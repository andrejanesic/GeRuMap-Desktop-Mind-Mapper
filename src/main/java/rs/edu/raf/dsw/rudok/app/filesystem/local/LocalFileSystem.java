package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;
import rs.edu.raf.dsw.rudok.app.repository.*;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite.Message.ChildChangeMessageData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// TODO local file saving will have to be expanded once more metadata is added for projects, etc.

/**
 * <h1>Local file system</h1>
 * Saves data into the local file system.
 * <br><br>
 * Continuously listens for changes to the project structure and appends them to a database log file. Every
 * FILESYSTEM_PROJECT_DATABASE_CYCLE cycles the data is compacted into a new file, with the old one being overwritten.
 * The data is saved as bytes, in the format: <code>&lt;operation code&gt; &lt;data byte size&gt; (...arguments)</code>.
 * Arguments are the following (spaces for clarity, but a byte-separator is used):
 * <ul>
 *     <li>Node values are changed: <code>&lt;nodeId&gt; &lt;number of changes&gt; [...attribute [value]]</code>.
 *     There may be an arbitrary amount of attributes and values, depending on the node type. Node type is inferred from
 *     the schema. The schema is written for forwards and backwards compatibility, but could be omitted to preserve
 *     space.</li>
 *     <li>Child is added to a parent node: <code>&lt;childId&gt; &lt;parentId&gt;</code></li>
 *     <li>Child is removed from a parent node: <code>&lt;childId&gt; &lt;parentId&gt;</code></li>
 * </ul>
 * A backup of the project is kept in the <code>&lt;filename&gt;.bak</code> file located in the same directory as the
 * project file.
 * <h2>Database structure</h2>
 * <h3>Save file</h3>
 * The save file of the project (the "source"/"original" project file) is saved in a log-alike structure, in binary
 * format without compression. Only final states of each node are recorded&mdash;the project history is omitted.
 * <h3>Backup file</h3>
 * A backup file is created for each project. It works in a similar way to the source project file&mdash;however, full
 * project history is recorded in this file and the current state of the project can be re-created anywhere by
 * "replaying" the backup file. The backup file is appended to, and there may be duplicate data when new children are
 * added. Because the program does not know when a new node is created in memory, it can only detect new nodes when they
 * are added to a parent. Thus, each time a node is added to a parent and it has no more than 1 parent at the time of
 * reading, the node type and current values are written.
 */
public class LocalFileSystem extends IPublisher implements IFileSystem {

    private final ApplicationFramework applicationFramework;

    /**
     * Int-code for each operation on the tree.
     */
    private enum OPCODES {
        END,
        SEPARATOR,
        NODE_VALUE,
        NODE_CHILD_ADD,
        NODE_CHILD_REMOVE,
    }

    /**
     * Int-codes representing the schema for each node.
     */
    private enum ATTR_SCHEMA {
        PROJECT_PROJECTNAME,
        PROJECT_AUTHORNAME,
        PROJECT_FILEPATH,
        MINDMAP_TEMPLATE,
        ELEMENT_EMPTY,
        // TODO add other attributes here
    }

    public LocalFileSystem(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }

    @Override
    public void saveConfig(Map<String, String> config) {
        try {
            String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER() +
                    config.getOrDefault("config", "default") +
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
            // TODO to error component
        }
    }

    @Override
    public Map<String, String> loadConfig(String name) {

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER() +
                name +
                ".ser";

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
            // TODO to error component
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
            // TODO log to error handler, cannot append
            return;
        }

        // recreate operations from the root to the leaves of the tree
        List<Object[]> operations = recreateOperations(project);

        // append the operations to the new db file
        Iterator<Object[]> iterator = operations.listIterator();
        while (iterator.hasNext()) {
            if (!appendOp(project, false, iterator.next())) {
                // TODO log to error handler, there was an error
                return;
            }
        }

        // backup no longer needed, clean
        eraseDb(project, true);
    }

    @Override
    public Project loadProject(String name) {
        String filepath = applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER() +
                "/" +
                name;
        Project project = null;

        try {
            File f = new File(filepath);
            InputStream is = new FileInputStream(f);

            // Get the size of the file
            long length = f.length();

            // Not possible to create a byte-array with more than Integer values, so check to ensure casting won't fail
            if (length > Integer.MAX_VALUE) {
                // File is too large
                // TODO log error
                return null;
            }

            // Create the byte array to hold the data
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < length) {
                // throw new IOException("Could not completely read file "+file.getName());
                // TODO log error
                return null;
            }

            // Ensure all the bytes have been read in
            if (numRead < length) {
                // throw new IOException("Could not completely read file "+file.getName());
                // TODO log error
                return null;
            }

            // Parse bytes
            int i = 0;
            while (i < numRead) {

            }

            // Close the input stream and return project
            is.close();
            return project;
        } catch (FileNotFoundException e) {
            // TODO log error
            // e.printStackTrace();
        } catch (IOException e) {
            // TODO log error
            // e.printStackTrace();
        }
        return null;
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

        if (message instanceof IMapNodeComposite.Message) {
            switch (((IMapNodeComposite.Message) message).getStatus()) {

                case CHILD_ADDED: {
                    ChildChangeMessageData data = (ChildChangeMessageData)
                            ((IMapNodeComposite.Message) message).getData();

                    // Child because we want to avoid getting the "root-est" ProjectExplorer
                    Set<Project> projects = getProjects(data.getChild());
                    if (projects == null) {
                        // TODO log error
                        return;
                    }

                    Iterator<Project> iterator = projects.iterator();
                    while (iterator.hasNext()) {
                        appendOp(iterator.next(), true, op_encode_ChildAdd(data));
                    }

                    break;
                }

                case CHILD_REMOVED: {
                    ChildChangeMessageData data = (ChildChangeMessageData)
                            ((IMapNodeComposite.Message) message).getData();

                    // Child because we want to avoid getting the "root-est" ProjectExplorer
                    Set<Project> projects = getProjects(data.getChild());
                    if (projects == null) {
                        // TODO log error
                        return;
                    }

                    Iterator<Project> iterator = projects.iterator();
                    while (iterator.hasNext()) {
                        appendOp(iterator.next(), true, op_encode_ChildRemove(data));
                    }

                    break;
                }
            }
        } else if (message instanceof IMapNode.Message) {
            switch (((IMapNode.Message) message).getStatus()) {

                case EDITED:
                    // TODO call op_encode_Value
                    // TODO right now changes to actual ndoe values are not being backed up, only the structure is. Node values have to be backed up
                    break;

                case PARENT_ADDED:
                    // Ignore because we listen for changes in the parent.
                    break;

                case PARENT_REMOVED:
                    // Ignore because we listen for changes in the parent.
                    break;
            }
        }
    }

    /**
     * Recreates the operations that build a tree of IMapNodeComposites from the given root element. If an IMapNode is
     * passed, returns only operations for that node.
     *
     * @param node Root IMapNode.
     * @return List of operations that recreate a tree of IMapNodeComposites.
     */
    private List<Object[]> recreateOperations(IMapNode node) {
        if (node == null) return new LinkedList<>();
        List<Object[]> operations = new LinkedList<>();

        // TODO here we will encode the actual properties of the node
        // operations.add(op_encode_Edit(new IMapNode.Message.EditedMessageData(key, val)))

        // stop here if not a composite
        if (!(node instanceof IMapNodeComposite)) return operations;
        IMapNodeComposite root = (IMapNodeComposite) node;

        Iterator<IMapNode> iterator = root.getChildren().iterator();
        while (iterator.hasNext()) {
            IMapNode child = iterator.next();
            operations.add(op_encode_ChildAdd(new IMapNodeComposite.Message.ChildChangeMessageData(
                    root, child
            )));
            if (child instanceof IMapNodeComposite)
                operations.addAll(recreateOperations(child));
        }

        return operations;
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
            // TODO send to error handler
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
        try {
            String fileName = this.parseProjectFilepath(project, backup);

            Files.createDirectories(Paths.get(applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER()));

            new File(fileName).createNewFile();
            return true;
        } catch (IOException e) {
            // TODO send to error handler
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Appends to the currently opened project's database.
     *
     * @param backup Which database to append to for the given project.
     * @param tokens Operation tokens to append (operation code plus arguments, omit separators.) First token must be
     *               operation code.
     */
    private boolean appendOp(Project project, boolean backup, Object... tokens) {
        if (tokens == null || tokens.length == 0) {
            // TODO log to error handler here?
            return false;
        }

        // coding error, this shouldn't (ever) happen
        if (!(tokens[0] instanceof Byte)) {
            throw new RuntimeException("First operation in appendOp(tokens) must be the OPCODE.");
        }

        // Ensure deltas DB is available
        if (!setupDb(project, backup)) {
            // TODO log to error handler
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(
                    this.parseProjectFilepath(project, backup),
                    true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            for (int j = 0; j < tokens.length; j++) {
                Object tok = tokens[j];

                if (tok instanceof String) {
                    dos.writeUTF((String) tok);
                    continue;
                }

                if (tok instanceof Long) {
                    dos.writeLong((Long) tok);
                }

                if (tok instanceof Integer) {
                    dos.writeInt((int) tok);
                    continue;
                }

                if (tok instanceof Short) {
                    dos.writeShort((short) tok);
                    continue;
                }

                if (tok instanceof Character) {
                    dos.writeChar((char) tok);
                    continue;
                }

                if (tok instanceof Double) {
                    dos.writeDouble((double) tok);
                    continue;
                }

                if (tok instanceof Float) {
                    dos.writeFloat((float) tok);
                    continue;
                }

                if (tok instanceof Byte) {
                    dos.write((byte) tok);
                }

                if (j < tokens.length - 1)
                    dos.write((byte) OPCODES.SEPARATOR.ordinal());
            }

            dos.write((byte) OPCODES.END.ordinal());

            dos.close();
            bos.close();
            fos.close();
            return true;
        } catch (IOException e) {
            // TODO log to error handler
            return false;
        }
    }

    /**
     * Returns the fully-qualified path to the project, taking all parameters into account.
     *
     * @param project The project.
     * @param backup  Whether to create a filepath to the backup of the project or the original.
     * @return The fully qualified filepath.
     */
    private String parseProjectFilepath(Project project, boolean backup) {
        return applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER() +
                "/" +
                project.getFilepath() +
                (backup ? ".bak" : "");
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
        }

        // Should never be reached
        // TODO log error
        return null;
    }

    /**
     * Encode the state of the node.
     *
     * @param node {@link IMapNode} node.
     * @return Byte-representation of the node state.
     */
    private Object[] op_encode_Value(IMapNode node) {

        // TODO add other attributes here

        if (node instanceof Project) {
            return new Object[]{
                    (byte) node.hashCode(),
                    (byte) OPCODES.NODE_VALUE.ordinal(),
                    (byte) ATTR_SCHEMA.PROJECT_PROJECTNAME.ordinal(),
                    ((Project) node).getProjectName(),
                    (byte) ATTR_SCHEMA.PROJECT_AUTHORNAME.ordinal(),
                    ((Project) node).getAuthorName(),
                    (byte) ATTR_SCHEMA.PROJECT_PROJECTNAME.ordinal(),
                    ((Project) node).getFilepath(),
            };

        } else if (node instanceof MindMap) {
            return new Object[]{
                    (byte) node.hashCode(),
                    (byte) OPCODES.NODE_VALUE.ordinal(),
                    (byte) ATTR_SCHEMA.MINDMAP_TEMPLATE.ordinal(),
                    (((MindMap) node).isTemplate() ? 1 : 0),
            };

        } else if (node instanceof Element) {
            // TODO add element attributes here
            return new Object[]{
                    (byte) node.hashCode(),
                    (byte) OPCODES.NODE_VALUE.ordinal(),
                    (byte) ATTR_SCHEMA.ELEMENT_EMPTY.ordinal()
            };

        } else {
            // TODO add a method on IAddon for exporting repository objects of arbitrary type as bytes
            // Arbitrary implementation of IMapNode
            return new Object[]{};
        }
    }

    private Object[] op_encode_ChildAdd(IMapNodeComposite.Message.ChildChangeMessageData data) {
        IMapNode child = data.getChild();
        IMapNodeComposite parent = data.getParent();

        if (child instanceof Project) {
            // New project created. Here we can't add a child anywhere because ProjectExplorer is not saved
            return op_encode_Value(child);
        }

        // New arbitrary object (mind map, element, or else) created OR added to a valid parent

        Object[] bytes = new Object[]{
                (byte) OPCODES.NODE_CHILD_ADD.ordinal(),
                child.hashCode(),
                parent.hashCode(),
        };

        // TODO this won't work in async code if a parent gets added after a parent is added but before this line
        if (data.getChild().getParents().size() == 1) {
            // If just added, save the values too
            List<Object> arr = Arrays.asList(bytes);
            arr.addAll(Arrays.asList(op_encode_Value(child)));
            bytes = arr.toArray();
        }

        return bytes;
    }

    private Object[] op_encode_ChildRemove(IMapNodeComposite.Message.ChildChangeMessageData data) {
        IMapNode child = data.getChild();
        IMapNodeComposite parent = data.getParent();

        return new Object[]{
                (byte) OPCODES.NODE_CHILD_REMOVE.ordinal(),
                child.hashCode(),
                parent.hashCode()
        };
    }

    private Object[] op_decode_ChildAdd() {
        return null;
    }

    private Object[] op_encode_ChildRemove(IMapNodeComposite.Message message) {
        return null;
    }

    private Object[] op_decode_ChildRemove() {
        return null;
    }
}
