package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

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
     * Path to the current open project.
     */
    private String projectPath = null;

    /**
     * Path to the backup of the current open project.
     */
    private String projectPathBackup = null;

    /**
     * Stores the IDs of all map nodes.
     */
    private Map<Integer, IMapNode> ids = new HashMap<>();

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
    public void saveProject(IMapNodeComposite project) {
        // TODO this method needs to be updated to support multiple projects
        // TODO this should be the name of the project

        if (projectPath == null) {
            setupDb(false);
        }

        if (projectPath == null) {
            // TODO log to error handler, cannot append
            return;
        }

        // reset the project db if already existing
        if (!eraseDb(false) || setupDb(false) == null) {
            // TODO log to error handler, something went wrong
            return;
        }

        // recreate operations from the root to the leaves of the tree
        List<Object[]> operations = recreateOperations(project);

        // append the operations to the new db file
        Iterator<Object[]> iterator = operations.listIterator();
        while (iterator.hasNext()) {
            if (!appendOp(false, iterator.next())) {
                // TODO log to error handler, there was an error
                return;
            }
        }

        // backup no longer needed, clean
        eraseDb(true);
    }

    @Override
    public IMapNodeComposite loadProject(String name) {

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

                case CHILD_ADDED:
                    appendOp(true, op_encode_ChildAdd((IMapNodeComposite.Message.ChildChangeMessageData)
                            ((IMapNodeComposite.Message) message).getData()));
                    break;

                case CHILD_REMOVED:
                    appendOp(true, op_encode_ChildRemove((IMapNodeComposite.Message.ChildChangeMessageData)
                            ((IMapNodeComposite.Message) message).getData()));
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
    private boolean eraseDb(boolean backup) {
        // TODO this method needs to be updated to support multiple projects
        // TODO this should be the name of the project

        String dbName = "default";
        String fileName = dbName + (backup ? ".bak" : "") + ".gerumap";
        String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER() + '/' + fileName;
        try {
            Files.deleteIfExists(Paths.get(filePath));
            if (backup) projectPath = null;
            else projectPathBackup = null;
            return true;
        } catch (IOException e) {
            // TODO send to error handler
            return false;
        }
    }

    /**
     * Sets up the database for the project. Does not reset an existing file. Creates new file if not found.
     * TODO this should use project name as filename.
     *
     * @param backup Whether to make this into a backup DB or not.
     * @return String path to the setup database.
     */
    private String setupDb(boolean backup) {
        // if already setup
        if (!backup && projectPath != null) return projectPath;
        if (backup && projectPathBackup != null) return projectPathBackup;

        try {
            // TODO this method needs to be updated to support multiple projects
            // TODO this should be the name of the project
            String dbName = "default";
            String fileName = dbName + (backup ? ".bak" : "") + ".gerumap";
            Files.createDirectories(Paths.get(applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER()));

            if (backup) {
                projectPathBackup = applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER() + '/' + fileName;
                projectPathBackup = new File(projectPathBackup).createNewFile() ? projectPathBackup : null;
            } else {
                projectPath = applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER() + '/' + fileName;
                projectPath = new File(projectPath).createNewFile() ? projectPath : null;
            }
        } catch (IOException e) {
            // TODO send to error handler
            if (backup) projectPathBackup = null;
            else projectPath = null;
            e.printStackTrace();
        }

        if (backup) return projectPathBackup;
        return projectPath;
    }

    /**
     * Appends to the currently opened project's database.
     *
     * @param backup Which database to append to for the given project.
     * @param tokens Operation tokens to append (operation code plus arguments, omit separators.) First token must be
     *               operation code.
     */
    private boolean appendOp(boolean backup, Object... tokens) {
        if (tokens == null || tokens.length == 0) {
            // TODO log to error handler here?
            return false;
        }

        // coding error, this shouldn't (ever) happen
        if (!(tokens[0] instanceof Byte)) {
            throw new RuntimeException("First operation in appendOp(tokens) must be the OPCODE.");
        }

        String pathToWrite = setupDb(backup);
        if (pathToWrite == null) {
            // TODO log to error handler, cannot append
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pathToWrite, true);
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
