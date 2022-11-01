package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;
import rs.edu.raf.dsw.rudok.app.repository.*;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite.Message.ChildChangeMessageData;

import javax.swing.text.Element;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// TODO local file saving will have to be expanded once more metadata is added for projects, etc.

/**
 * Saves data into the local file system.
 * <br><br>
 * Continuously listens for changes to the project structure and appends them to a database log file. Every
 * FILESYSTEM_PROJECT_DATABASE_CYCLE cycles the data is compacted into a new file, with the old one being overwritten.
 * The data is saved as bytes, in the format: <code>&lt;operation code&gt; &lt;data byte size&gt; (...arguments)</code>.
 * Arguments are the following (spaces for clarity, but a byte-separator is used):
 * <br><br>
 * <ul>
 *     <li>Node is edited: <code>&lt;nodeId&gt; &lt;attribute&gt; &lt;value&gt;</code></li>
 *     <li>Child is added to a parent node: <code>&lt;childId&gt; &lt;parentId&gt;</code></li>
 *     <li>Child is removed from a parent node: <code>&lt;childId&gt; &lt;parentId&gt;</code></li>
 * </ul>
 */
public class LocalFileSystem extends IPublisher implements IFileSystem {

    private final ApplicationFramework applicationFramework;

    /**
     * Stores the IDs of all map nodes.
     */
    private Map<Integer, IMapNode> ids = new HashMap<>();

    /**
     * Int-code for each operation on the tree.
     */
    private enum OPCODES {
        END,
        SEPARATOR,
        NODE_EDIT,
        NODE_CHILD_ADD,
        NODE_CHILD_REMOVE,
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

        // TODO this method needs to be updated to support multiple projects
        // TODO this should be the name of the project

        // TODO complete this method once we have IProject available

        return null;
    }

    @Override
    public void receive(Object message) {
        // TODO execute on a separate thread
        if (message instanceof IMapNodeComposite.Message) {
            switch (((IMapNodeComposite.Message) message).getStatus()) {

                case CHILD_ADDED: {
                    ChildChangeMessageData data = (ChildChangeMessageData) ((IMapNodeComposite.Message) message).getData();
                    Set<Project> projects = getProjects(data.getParent());
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
                    ChildChangeMessageData data = (ChildChangeMessageData) ((IMapNodeComposite.Message) message).getData();
                    Set<Project> projects = getProjects(data.getParent());
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
     * Retrieves the Project parent for the given IMapNodeComposite of arbitrary implementation. Assumes that parents
     * are correctly set (e.g. MindMap's parent cannot be Element, Project's parent must be ProjectExplorer, etc.)
     *
     * @param composite Composite, assumed to be part of a Project.
     * @return Project root node.
     */
    private Set<Project> getProjects(IMapNodeComposite composite) {
        if (composite instanceof ProjectExplorer) return null;
        Set<Project> s = new HashSet<>();
        if (composite instanceof Project) {
            s.add((Project) composite);
            return s;
        }
        if (composite instanceof MindMap) {
            Iterator<IMapNodeComposite> iterator = composite.getParents().iterator();
            while (iterator.hasNext()) {
                s.add((Project) iterator.next());
            }
            return s;
        }
        if (composite instanceof Element) {
            Iterator<IMapNodeComposite> iterator = composite.getParents().iterator();
            while (iterator.hasNext()) {
                MindMap m = (MindMap) iterator.next();
                Iterator<IMapNodeComposite> nestedIterator = m.getParents().iterator();
                while (nestedIterator.hasNext()) {
                    s.add((Project) nestedIterator.next());
                }
            }
        }
        // Should never be reached
        return null;
    }

    private Object[] op_encode_Edit(IMapNode.Message.EditedMessageData data) {
        return new Object[]{
                (byte) OPCODES.NODE_EDIT.ordinal(),
                data.getKey(),
                data.getValue()
        };
    }

    private Object[] op_encode_ChildAdd(IMapNodeComposite.Message.ChildChangeMessageData data) {
        IMapNode child = data.getChild();
        IMapNodeComposite parent = data.getParent();

        return new Object[]{
                (byte) OPCODES.NODE_CHILD_ADD.ordinal(),
                child.hashCode(),
                parent.hashCode()
        };
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
