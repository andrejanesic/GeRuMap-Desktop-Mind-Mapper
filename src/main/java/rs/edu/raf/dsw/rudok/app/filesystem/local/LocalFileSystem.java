package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.repository.*;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite.Message.ChildChangeMessageData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// TODO local file saving will have to be expanded once more metadata is added for projects, etc.

/**
 * <h1>Local file system</h1>
 * Saves data into the local file system.<br>
 * <br>
 * Continuously listens for changes to the project structure and appends them to a database log file. Changes are saved
 * in the form of "state" statements for each node. Each statement logs a change to the state of a node, overwriting the
 * previous one. States are formatted in the database like so:<br>
 * <br>
 * <code>&lt;nodeId (int)&gt; &lt;number of changes (byte)&gt; [...attribute [...value]</code><br>
 * <br>
 * There may be an arbitrary amount of attributes and values, depending on the node type. Node type is inferred from the
 * schema. The schema is written for forwards and backwards compatibility, but could be omitted to preserve space.<br>
 * <br>
 * A backup of the project is kept in the <code>&lt;filename&gt;.bak</code> file located in the same directory as the
 * project file.<br>
 * <br>
 * <h2>Database structure</h2>
 * <h3>Save file</h3>
 * The save file of the project (the "source"/"original" project file) is saved in a log-alike structure, in binary
 * format without compression. Only final states of each node are recorded&mdash;the project history is omitted.<br>
 * <br>
 * <h3>Backup file</h3>
 * A backup file is created for each project. It works in a similar way to the source project file&mdash;however, full
 * project history is recorded in this file and the current state of the project can be re-created anywhere by
 * "replaying" the backup file. The backup file is appended to, and there may be duplicate data when new children are
 * added. Because the program does not know when a new node is created in memory, it can only detect new nodes when they
 * are added to a parent. Thus, each time a node is added to a parent and it has no more than 1 parent at the time of
 * reading, the node type and current values are written.
 */
public class LocalFileSystem extends IFileSystem {

    private final ApplicationFramework applicationFramework;

    public LocalFileSystem(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }

    @Override
    public void saveConfig(Map<String, String> config) {
        try {
            if (config.getOrDefault("config", null) == null) {
                AppCore.getInstance().getMessageGenerator().error("Config name cannot be empty");
                return;
            }
            String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER() +
                    config.get("config") +
                    ".ser";
            Files.createDirectories(Paths.get(applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER()));

            // open output streams
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // write object
            oos.writeObject(config);

            // close output streams
            oos.close();
            fos.close();
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error("Failed to save configuration");
        }
    }

    @Override
    public Map<String, String> loadConfig(String name) {

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER() +
                name + (name.endsWith(".ser") ? "" : ".ser");

        try {
            // open output streams
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // write object
            Map<String, String> obj = (Map<String, String>) ois.readObject();

            // close output streams
            ois.close();
            fis.close();

            return obj;
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error("Failed to load configuration " + name);
            return null;
        }
    }

    /**
     * Saves the project file as a logfile of operations on the project tree. The root tree node is saved inside the
     * file too.
     *
     * @param project Project to save.
     */
    @Override
    public void saveProject(Project project) {
        if (!eraseDb(project, false) || !setupDb(project, false)) {
            AppCore.getInstance().getMessageGenerator().error("Failed to save project " + project.getNodeName());
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(
                    this.parseProjectFilepath(project, false),
                    true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            // encode values
            recreateOperations(dos, project);

            dos.close();
            bos.close();
            fos.close();

            // backup no longer needed, clean
            eraseDb(project, true);

        } catch (IOException e) {
            AppCore.getInstance().getMessageGenerator().error("Failed to save project " + project.getNodeName());
        }

        AppCore.getInstance().getMessageGenerator().log("Project " + project.getNodeName() + " saved.");
    }

    @Override
    public Project loadProject(String filepath) {
        Project project = null;

        try {
            File f = new File(filepath);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);

            // Store a temporary map of node IDs and their objects for easier referencing
            Map<Integer, IMapNode> nodes = new HashMap<>();

            // Parse states.
            while (dis.available() > 0) {
                if (!decodeNodeState(dis, nodes)) {
                    // TODO invalid operation or programmatic error in parsing
                    // AppCore.getInstance().getMessageGenerator().error("Failed to decode project file " + filepath);
                    return null;
                }
            }

            // Find root project. TODO there should be a better solution than traversing the whole structure again
            Iterator<Integer> iterator = nodes.keySet().iterator();
            while (iterator.hasNext()) {
                int key = iterator.next();
                if (nodes.get(key) instanceof Project) {
                    if (project != null) {
                        // TODO programmatic error, there may only be one project per project file
                        return null;
                    }
                    project = (Project) nodes.get(key);
                }
            }

            // Close the input stream and return project
            dis.close();
            fis.close();
            return project;

        } catch (IOException e) {
            AppCore.getInstance().getMessageGenerator().error("Failed to load project from " + filepath);
            // e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteProject(Project p) {
        try {
            return new File(p.getFilepath()).delete();
        } catch (Exception e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to delete project " + p.getNodeName());
            return false;
        }
    }

    @Override
    public void log(String content, IMessageGenerator.Type type, String timestamp) {
        try{
            FileWriter fileWriter = new FileWriter("/logfile.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content + type + timestamp);
            bufferedWriter.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Listens for changes to subscriber objects.<br><br>
     * Messages regarding
     *
     * @param message Data sent by publisher.
     */
    @Override
    public void receive(Object message) {

        // TODO execute on a separate thread
        // TODO this should be ignored on all messages from projects and/or its subelements of a project currently being loaded!

        // If message from IMapNodeComposite
        if (message instanceof IMapNodeComposite.Message) {

            ChildChangeMessageData data = (ChildChangeMessageData)
                    ((IMapNodeComposite.Message) message).getData();

            // Child because we want to avoid getting the "root-est" ProjectExplorer; if null, getChild() is a Project
            Set<Project> projects = getProjects(data.getChild());

            Iterator<Project> iterator = projects.iterator();
            while (iterator.hasNext()) {
                Project p = iterator.next();

                switch (((IMapNodeComposite.Message) message).getStatus()) {

                    case CHILD_ADDED: {
                        if (data.getChild() instanceof Project) {
                            // New project added
                            appendOp(p, true, data.getChild());

                            if (Boolean.parseBoolean(
                                    String.valueOf(AppCore.getInstance().getConfigHandler()
                                            .get("autosave", false)))) {
                                saveProject((Project) data.getChild());
                            }

                        } else if (data.getChild() instanceof MindMap) {
                            appendOp(p, true, data.getChild());
                            appendOp(p, true, data.getParent(),
                                    new ATTR_SCHEMA[]{ATTR_SCHEMA.PROJECT_CHILDREN});

                            if (Boolean.parseBoolean(
                                    String.valueOf(AppCore.getInstance().getConfigHandler()
                                            .get("autosave", false)))) {
                                saveProject(p);
                            }

                        } else if (data.getChild() instanceof Element) {
                            appendOp(p, true, data.getChild());
                            appendOp(p, true, data.getParent(),
                                    new ATTR_SCHEMA[]{ATTR_SCHEMA.MINDMAP_CHILDREN});

                            if (Boolean.parseBoolean(
                                    String.valueOf(AppCore.getInstance().getConfigHandler()
                                            .get("autosave", false)))) {
                                saveProject(p);
                            }
                        }
                        break;
                    }

                    case CHILD_REMOVED: {
                        if (data.getChild() instanceof Project) {
                            // TODO Check if nothing should be done here (project was removed from workspace, potentially deleted)
                            if (Boolean.parseBoolean(
                                    String.valueOf(AppCore.getInstance().getConfigHandler()
                                            .get("autosave", false)))) {
                                saveProject(p);
                            }

                        } else if (data.getChild() instanceof MindMap) {
                            appendOp(p, true, data.getChild());
                            appendOp(p, true, data.getParent(),
                                    new ATTR_SCHEMA[]{ATTR_SCHEMA.PROJECT_CHILDREN});

                            if (Boolean.parseBoolean(
                                    String.valueOf(AppCore.getInstance().getConfigHandler()
                                            .get("autosave", false)))) {
                                saveProject(p);
                            }

                        } else if (data.getChild() instanceof Element) {
                            appendOp(p, true, data.getChild());
                            appendOp(p, true, data.getParent(),
                                    new ATTR_SCHEMA[]{ATTR_SCHEMA.MINDMAP_CHILDREN});

                            if (Boolean.parseBoolean(
                                    String.valueOf(AppCore.getInstance().getConfigHandler()
                                            .get("autosave", false)))) {
                                saveProject(p);
                            }
                        }
                        break;
                    }
                }
            }

        } else if (message instanceof IMapNode.Message) {
            switch (((IMapNode.Message) message).getStatus()) {

                case EDITED: {
                    IMapNode sender = (IMapNode) ((IMapNode.Message) message).getData().getSender();
                    String key = ((IMapNode.Message.EditedMessageData) ((IMapNode.Message) message).getData()).getKey();

                    // Child because we want to avoid getting the "root-est" ProjectExplorer; if null, getChild() is a Project
                    Set<Project> projects = getProjects(sender);

                    // For each project the sender is child of, encode op for the specific change
                    Iterator<Project> iterator = projects.iterator();
                    while (iterator.hasNext()) {
                        Project p = iterator.next();

                        if (sender instanceof Project) {

                            if (key.equals("nodeName")) {
                                appendOp(p, true, sender, new ATTR_SCHEMA[]{ATTR_SCHEMA.PROJECT_NAME});
                            } else if (key.equals("authorName")) {
                                appendOp(p, true, sender, new ATTR_SCHEMA[]{ATTR_SCHEMA.PROJECT_AUTHORNAME});
                            } else if (key.equals("filepath")) {
                                appendOp(p, true, sender, new ATTR_SCHEMA[]{ATTR_SCHEMA.PROJECT_FILEPATH});
                            } else {
                                throw new RuntimeException("Should never be reached");
                            }

                        } else if (sender instanceof MindMap) {

                            if (key.equals("nodeName")) {
                                appendOp(p, true, sender, new ATTR_SCHEMA[]{ATTR_SCHEMA.MINDMAP_NAME});
                            } else if (key.equals("template")) {
                                appendOp(p, true, sender, new ATTR_SCHEMA[]{ATTR_SCHEMA.MINDMAP_TEMPLATE});
                            } else {
                                throw new RuntimeException("Should never be reached");
                            }

                        } else if (sender instanceof Element) {
                            appendOp(p, true, sender, new ATTR_SCHEMA[]{ATTR_SCHEMA.ELEMENT_NAME});
                        }

                        if (Boolean.parseBoolean(
                                String.valueOf(AppCore.getInstance().getConfigHandler()
                                        .get("autosave", false)))) {
                            saveProject(p);
                        }
                    }
                    break;
                }

                case PARENT_ADDED:
                case PARENT_REMOVED:
                default: {
                    // Ignore because we listen for changes in the parent.
                    break;
                }
            }
        }
    }

    /**
     * Encodes the project tree by reverse-encoding it from the leaves to the root.
     *
     * @param dos  {@link DataOutputStream} Stream to write to.
     * @param node Root IMapNode.
     * @return True if no errors, false otherwise.
     */
    private boolean recreateOperations(DataOutputStream dos, IMapNode node) {
        if (node == null) return true;

        // If a composite, write leaves first so we can read the children first
        if (node instanceof IMapNodeComposite) {
            IMapNodeComposite root = (IMapNodeComposite) node;

            Iterator<IMapNode> iterator = root.getChildren().iterator();
            while (iterator.hasNext()) {
                IMapNode child = iterator.next();
                if (!recreateOperations(dos, child)) {
                    // AppCore.getInstance().getMessageGenerator().error("Failed to recreate project tree");
                    return false;
                }
            }
        }

        return encodeNodeState(dos, node);
    }

    /**
     * Erases an existing database file. TODO this should use project name as filename
     *
     * @param backup Whether to erase the actual DB or its backup.
     * @return Returns true if no error, false otherwise.
     */
    private boolean eraseDb(Project project, boolean backup) {
        String fileName = this.parseProjectFilepath(project, backup);
        try {
            Files.deleteIfExists(Paths.get(fileName));
            return true;
        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to erase project database " + fileName);
            return false;
        }
    }

    /**
     * Sets up the database for the project. Does not reset an existing file. Creates new file if not found.
     *
     * @param project Project to set up the DB for.
     * @param backup  Whether to make this into a backup DB or not.
     */
    private boolean setupDb(Project project, boolean backup) {
        String fileName = this.parseProjectFilepath(project, backup);
        try {
            Files.createDirectories(Paths.get(applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER()));

            new File(fileName).createNewFile();
            return true;
        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to setup project database at " + fileName);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Appends partial attribute (changes) of a node to a project's database.
     *
     * @param project Which project to append ot.
     * @param backup  Which database to append to for the given project.
     * @param node    Node the delta occurred on.
     * @param attrs   Which attributes to append to regarding the changed node.
     * @return True if no errors, false otherwise.
     */
    private boolean appendOp(Project project, boolean backup, IMapNode node, ATTR_SCHEMA[] attrs) {

        // Ensure deltas DB is available
        if (!setupDb(project, backup)) {
            // AppCore.getInstance().getMessageGenerator().error("Project database unavailable");
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(
                    this.parseProjectFilepath(project, backup),
                    true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            // encode values
            encodeNodeState(dos, node, attrs);

            dos.close();
            bos.close();
            fos.close();
            return true;
        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to write to project database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Appends all attributes of a node to a project's database.
     *
     * @param project Which project to append ot.
     * @param backup  Which database to append to for the given project.
     * @param node    Node the delta occurred on.
     * @return True if no errors, false otherwise.
     */
    private boolean appendOp(Project project, boolean backup, IMapNode node) {
        return appendOp(project, backup, node, new ATTR_SCHEMA[]{});
    }

    /**
     * Returns the fully-qualified path to the project, taking all parameters into account.
     *
     * @param project The project.
     * @param backup  Whether to create a filepath to the backup of the project or the original.
     * @return The fully qualified filepath.
     */
    private String parseProjectFilepath(Project project, boolean backup) {
        String t = applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER();
        t = t + project.getFilepath();
        return t + (backup ? ".bak" : "");
    }

    /**
     * Retrieves the Project parent for the given IMapNode of arbitrary implementation. Assumes that parents are
     * correctly set (e.g. MindMap's parent cannot be Element, Project's parent must be ProjectExplorer, etc.)
     *
     * @param node Node, potentially {@link IMapNodeComposite}, assumed to be part of a {@link Project}.
     * @return Set of root parent {@link Project}s for the given node..
     */
    private Set<Project> getProjects(IMapNode node) {
        Set<Project> s = new HashSet<>();
        if (node instanceof ProjectExplorer)
            return s;

        if (node instanceof Project) {
            s.add((Project) node);
            return s;
        }

        if (node instanceof MindMap) {
            Iterator<IMapNodeComposite> iterator = node.getParents().iterator();
            while (iterator.hasNext()) {
                s.add((Project) iterator.next());
            }
            return s;
        }

        if (node instanceof Element) {
            Iterator<IMapNodeComposite> iterator = node.getParents().iterator();
            while (iterator.hasNext()) {
                MindMap m = (MindMap) iterator.next();
                Iterator<IMapNodeComposite> nestedIterator = m.getParents().iterator();
                while (nestedIterator.hasNext()) {
                    s.add((Project) nestedIterator.next());
                }
            }
            return s;
        }

        // Should never be reached
        AppCore.getInstance().getMessageGenerator().warning("App encountered unreachable state");
        return null;
    }

    /**
     * Encode the full state of a node into the DataOutputStream.
     *
     * @param dos  {@link DataOutputStream} Stream to write to.
     * @param node {@link IMapNode} node.
     * @return True if no errors, false otherwise.
     */
    private boolean encodeNodeState(DataOutputStream dos, IMapNode node) {
        return encodeNodeState(dos, node, new ATTR_SCHEMA[]{});
    }

    /**
     * Partially encode the state of a node (only select values).
     *
     * @param dos   {@link DataOutputStream} Stream to write to.
     * @param node  {@link IMapNode} node.
     * @param attrs {@link ATTR_SCHEMA}[] array of schema attributes to encode.
     * @return True if no errors, false otherwise.
     */
    private boolean encodeNodeState(DataOutputStream dos, IMapNode node, ATTR_SCHEMA[] attrs) {

        // TODO add other attributes here

        try {
            if (node instanceof Project) {
                Project p = (Project) node;

                if (attrs.length == 0) {
                    attrs = new ATTR_SCHEMA[]{
                            ATTR_SCHEMA.PROJECT_NAME,
                            ATTR_SCHEMA.PROJECT_AUTHORNAME,
                            ATTR_SCHEMA.PROJECT_FILEPATH,
                            ATTR_SCHEMA.PROJECT_CHILDREN,
                    };
                }

                dos.writeInt(node.hashCode());
                // TODO this could snap if more than 2^8 attributes on node
                dos.writeByte(attrs.length);

                for (int i = 0; i < attrs.length; i++) {
                    dos.write((byte) attrs[i].ordinal());

                    switch (attrs[i]) {
                        case PROJECT_NAME:
                            dos.writeUTF(p.getNodeName());
                            break;
                        case PROJECT_AUTHORNAME:
                            dos.writeUTF(p.getAuthorName());
                            break;
                        case PROJECT_FILEPATH:
                            dos.writeUTF(p.getFilepath());
                            break;
                        case PROJECT_CHILDREN:
                            dos.writeInt(p.getChildren().size());

                            Iterator<IMapNode> iterator = p.getChildren().iterator();
                            while (iterator.hasNext()) {
                                dos.writeInt(iterator.next().hashCode());
                            }
                            break;

                        default:
                            break;
                    }
                }

                return true;
            }

            if (node instanceof MindMap) {

                MindMap m = (MindMap) node;

                if (attrs.length == 0) {
                    attrs = new ATTR_SCHEMA[]{
                            ATTR_SCHEMA.MINDMAP_TEMPLATE,
                            ATTR_SCHEMA.MINDMAP_NAME,
                            ATTR_SCHEMA.MINDMAP_CHILDREN,
                    };
                }

                dos.writeInt(node.hashCode());
                // TODO this could snap if more than 2^8 attributes on node
                dos.writeByte(attrs.length);

                for (int i = 0; i < attrs.length; i++) {
                    dos.write((byte) attrs[i].ordinal());

                    switch (attrs[i]) {
                        case MINDMAP_TEMPLATE:
                            dos.writeBoolean(m.isTemplate());
                            break;
                        case MINDMAP_NAME:
                            dos.writeUTF(m.getNodeName());
                            break;
                        case MINDMAP_CHILDREN:
                            dos.writeInt(m.getChildren().size());

                            Iterator<IMapNode> iterator = m.getChildren().iterator();
                            while (iterator.hasNext()) {
                                dos.writeInt(iterator.next().hashCode());
                            }
                            break;

                        default:
                            break;
                    }
                }

                return true;

            }

            if (node instanceof Element) {

                Element e = (Element) node;

                if (attrs.length == 0) {
                    attrs = new ATTR_SCHEMA[]{
                            ATTR_SCHEMA.ELEMENT_NAME,
                    };
                }

                dos.writeInt(node.hashCode());
                // TODO this could snap if more than 2^8 attributes on node
                dos.writeByte(attrs.length);

                for (int i = 0; i < attrs.length; i++) {
                    dos.write((byte) attrs[i].ordinal());

                    switch (attrs[i]) {
                        case ELEMENT_NAME:
                            dos.writeUTF(e.getNodeName());
                            break;

                        default:
                            break;
                    }
                }

                return true;

            }

            // TODO add a method on IAddon for exporting repository objects of arbitrary type as bytes
            // Arbitrary implementation of IMapNode
            return false;

        } catch (Exception e) {
            // AppCore.getInstance().getMessageGenerator().error("Error writing to project database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Decode the state of a node from a byte array into a tree of nodes.
     *
     * @param dis   DataInputStream to read from.
     * @param nodes Map of nodes where the node is (potentially) located.
     * @return True if no error, false otherwise.
     */
    private boolean decodeNodeState(DataInputStream dis, Map<Integer, IMapNode> nodes) {
        try {
            // Read node ID
            int nodeId = dis.readInt();

            // Read number of changes
            int changes = dis.readByte();

            // Possible values to be read from schema for each delta
            String
                    project_name = null,
                    project_authorName = null,
                    project_filePath = null,
                    mindmap_name = null,
                    element_name = null;
            Boolean
                    mindmap_template = null;
            List<Integer>
                    project_children = null,
                    mindmap_children = null;

            int type = -1;
            int i = changes;

            // Iterate on deltas while checking schema
            while (i > 0) {
                ATTR_SCHEMA schema = ATTR_SCHEMA.values()[dis.readUnsignedByte()];

                switch (schema) {
                    case PROJECT_NAME: {
                        if (type > -1 && type != 0) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 0;
                        project_name = dis.readUTF();
                        break;
                    }

                    case PROJECT_AUTHORNAME: {
                        if (type > -1 && type != 0) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 0;
                        project_authorName = dis.readUTF();
                        break;
                    }

                    case PROJECT_FILEPATH: {
                        if (type > -1 && type != 0) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 0;
                        project_filePath = dis.readUTF();
                        break;
                    }

                    case PROJECT_CHILDREN: {
                        // TODO this will fail if children are not loaded yet!

                        if (type > -1 && type != 0) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 0;
                        project_children = new ArrayList<>();
                        int numChildren = dis.readInt();
                        for (int j = 0; j < numChildren; j++) {
                            project_children.add(dis.readInt());
                        }
                        break;
                    }

                    case MINDMAP_TEMPLATE: {
                        if (type > -1 && type != 1) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 1;
                        mindmap_template = dis.readBoolean();
                        break;
                    }

                    case MINDMAP_NAME: {
                        if (type > -1 && type != 1) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 1;
                        mindmap_name = dis.readUTF();
                        break;
                    }

                    case MINDMAP_CHILDREN: {
                        // TODO this will fail if children are not loaded yet!

                        if (type > -1 && type != 1) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 1;
                        mindmap_children = new ArrayList<>();
                        int numChildren = dis.readInt();
                        for (int j = 0; j < numChildren; j++) {
                            mindmap_children.add(dis.readInt());
                        }
                        break;
                    }

                    case ELEMENT_NAME: {
                        if (type > -1 && type != 2) {
                            // Type already inferred from another attribute, so invalid schema!
                            return false;
                        }

                        type = 1;
                        element_name = dis.readUTF();
                        break;
                    }
                }

                i--;
            }

            switch (type) {

                case 0: {

                    // If Project schema
                    Project p = (Project) nodes.getOrDefault(nodeId, null);
                    if (p == null) {
                        p = new Project(
                                project_name,
                                project_authorName,
                                project_filePath
                        );
                        nodes.put(nodeId, p);
                    }
                    if (project_name != null) {
                        p.setNodeName(project_name);
                    }
                    if (project_authorName != null) {
                        p.setAuthorName(project_authorName);
                    }
                    if (project_filePath != null) {
                        p.setFilepath(project_filePath);
                    }
                    if (project_children != null) {
                        // Remove all children
                        Iterator<IMapNode> iterator = p.getChildren().iterator();
                        while (iterator.hasNext()) {
                            IMapNode c = iterator.next();
                            iterator.remove();
                            p.removeChild(c);
                        }

                        int s = project_children.size();
                        for (int k = 0; k < s; k++) {
                            IMapNode child = nodes.getOrDefault(project_children.get(k), null);
                            if (child == null) {
                                // TODO this is a programmatic error as children are not yet initialized
                                continue;
                            }
                            if (!(child instanceof MindMap)) {
                                // TODO log error, could be programmatic or corruption of data, invalid subelement
                                continue;
                            }
                            p.addChild(child);
                        }
                    }

                    break;
                }

                case 1: {

                    // If MindMap schema
                    MindMap m = (MindMap) nodes.getOrDefault(nodeId, null);
                    if (m == null) {
                        m = new MindMap(
                                Boolean.TRUE.equals(mindmap_template),
                                mindmap_name
                        );
                        nodes.put(nodeId, m);
                    }
                    if (mindmap_template != null) {
                        m.setTemplate(Boolean.TRUE.equals(mindmap_template));
                    }
                    if (mindmap_name != null) {
                        m.setNodeName(mindmap_name);
                    }
                    if (mindmap_children != null) {
                        // Remove all children
                        Iterator<IMapNode> iterator = m.getChildren().iterator();
                        while (iterator.hasNext()) {
                            IMapNode c = iterator.next();
                            iterator.remove();
                            m.removeChild(c);
                        }

                        int s = mindmap_children.size();
                        for (int k = 0; k < s; k++) {
                            IMapNode child = nodes.getOrDefault(mindmap_children.get(k), null);
                            if (child == null) {
                                // TODO this is a programmatic error as children are not yet initialized
                                continue;
                            }
                            if (!(child instanceof Element)) {
                                // TODO log error, could be programmatic or corruption of data, invalid subelement
                                continue;
                            }
                            m.addChild(child);
                        }
                    }

                    break;
                }

                case 2: {

                    // If Element schema
                    Element e = (Element) nodes.getOrDefault(nodeId, null);
                    if (e == null) {
                        e = new Element(element_name);
                        nodes.put(nodeId, e);
                    }
                    if (element_name != null) {
                        e.setNodeName(element_name);
                    }

                    break;
                }

                default:
                    // TODO log error or pass to IAddon because no registered  type inferred
                    return false;
            }

            return true;
        } catch (Exception e) {
            // AppCore.getInstance().getMessageGenerator().warning("Corrupted project file");
            return false;
        }
    }

    /**
     * Int-codes representing the schema for each node.
     */
    private enum ATTR_SCHEMA {
        PROJECT_NAME,
        PROJECT_AUTHORNAME,
        PROJECT_FILEPATH,
        PROJECT_CHILDREN,
        MINDMAP_TEMPLATE,
        MINDMAP_NAME,
        MINDMAP_CHILDREN,
        ELEMENT_NAME,
        // TODO add other attributes here
    }
}
