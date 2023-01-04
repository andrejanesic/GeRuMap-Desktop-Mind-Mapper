package rs.edu.raf.dsw.rudok.app.filesystem.local;

import org.json.JSONArray;
import org.json.JSONObject;
import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.repository.*;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite.Message.ChildChangeMessageData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// TODO local file saving will have to be expanded once more metadata is added for projects, etc.

/**
 * <h1>Local file system</h1>
 * Saves data into the local file system. Continuously listens for changes to the project structure and (optionally)
 * autosaves each changed {@link Project}. All repository items are saved as JSON objects via {@link org.json}.
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
            // TODO exclude on app start/create new config so no error pops up here
            // AppCore.getInstance().getMessageGenerator().error("Failed to save configuration");
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
            // TODO exclude on app start/create new config so no error pops up here
            // AppCore.getInstance().getMessageGenerator().error("Failed to load configuration " + name);
            return null;
        }
    }

    @Override
    public boolean saveProject(Project project) {
        if (!deleteProjectFile(project, false) || !createProjectFile(project, false)) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to save project " + project.getNodeName());
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(this.parseProjectFilepath(project, false), true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            // encode whole json as one string
            JSONObject jsonObject = createJsonProject(project);
            String json = jsonObject.toString();
            dos.writeUTF(json);

            // backup no longer needed, clean
            deleteProjectFile(project, true);

            // close output stream and return
            dos.close();
            bos.close();
            fos.close();

            // save templates too
            for (IMapNode child : project.getChildren()) {
                MindMap m = (MindMap) child;
                if (!m.isTemplate()) continue;
                saveMindMapTemplate(m);
            }

            return true;

        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to save project " + project.getNodeName());
            return false;
        }
    }

    @Override
    public Project loadProject(String filepath) {
        Project project = null;

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        try {
            File f = new File(filepath);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);

            // attempt to decode whole json from one string
            if (dis.available() == 0) {
                AppCore.getInstance().getMessageGenerator().error("Could not read file " + filepath);
                return null;
            }

            String json = dis.readUTF();
            JSONObject jsonObject = new JSONObject(json);
            project = parseJsonProject(jsonObject);

            // close input stream and return project
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
    public boolean saveMindMapTemplate(MindMap mindMap) {
        if (!deleteMindMapFile(mindMap) || !createMindMapFile(mindMap)) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to save mind map " + mindMap.getNodeName());
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(this.parseMindMapFilepath(mindMap), true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            // encode whole json as one string
            JSONObject jsonObject = createJsonMindMap(mindMap);
            String json = jsonObject.toString();
            dos.writeUTF(json);

            // close output stream and return
            dos.close();
            bos.close();
            fos.close();

            return true;

        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to save project " + project.getNodeName());
            return false;
        }
    }

    @Override
    public MindMap loadMindMapTemplate(String path) {
        MindMap template = null;

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        try {
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);

            // attempt to decode whole json from one string
            if (dis.available() == 0) {
                AppCore.getInstance().getMessageGenerator().error("Could not read file " + path);
                return null;
            }

            String json = dis.readUTF();
            JSONObject jsonObject = new JSONObject(json);
            template = parseJsonMindMap(jsonObject);

            // close input stream and return project
            dis.close();
            fis.close();
            return template;

        } catch (IOException e) {
            AppCore.getInstance().getMessageGenerator().error("Failed to load mind map from " + path);
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * Translates {@link Project} into JSON format.
     *
     * @param project {@link Project}
     * @return {@link JSONObject} representation of the {@link Project}
     */
    public JSONObject createJsonProject(Project project) {
        JSONObject json = new JSONObject();
        String nodeName = "nodeName",
                authorName = "authorName",
                filepath = "filepath",
                children = "children";
        json.put(nodeName, project.getNodeName());
        json.put(authorName, project.getAuthorName());
        json.put(filepath, project.getFilepath());

        JSONArray mindMaps = new JSONArray();
        for (IMapNode c : project.getChildren()) {
            JSONObject child = null;
            if (c instanceof MindMap) {
                child = createJsonMindMap((MindMap) c);
            } else {
                // TODO unsupported
            }

            if (child == null) {
                // TODO should not be encountered!
                continue;
            }
            mindMaps.put(child);
        }
        json.put(children, mindMaps);

        return json;
    }

    /**
     * Translates {@link MindMap} into JSON format.
     *
     * @param mindMap {@link MindMap}
     * @return {@link JSONObject} representation of the {@link MindMap}
     */
    public JSONObject createJsonMindMap(MindMap mindMap) {
        JSONObject json = new JSONObject();
        String nodeName = "nodeName",
                template = "template",
                children = "children";
        json.put(nodeName, mindMap.getNodeName());
        json.put(template, mindMap.isTemplate());

        JSONArray elements = new JSONArray();
        for (IMapNode c : mindMap.getChildren()) {
            JSONObject child = null;
            if (c instanceof Topic) {
                child = createJsonTopic((Topic) c);
            } else if (c instanceof Connection) {
                child = createJsonConnection((Connection) c);
            } else {
                // TODO unsupported
            }

            if (child == null) {
                // TODO should not be encountered!
                continue;
            }
            elements.put(child);
        }
        json.put(children, elements);

        return json;
    }

    /**
     * Translates {@link Topic} into JSON format.
     *
     * @param topic {@link Topic}
     * @return {@link JSONObject} representation of the {@link Topic}
     */
    public JSONObject createJsonTopic(Topic topic) {
        JSONObject json = new JSONObject();
        String id = "id",
                typeKey = "type",
                typeTopic = "topic",
                nodeName = "nodeName",
                stroke = "stroke",
                color = "color",
                width = "width",
                height = "height",
                x = "x",
                y = "y";
        json.put(id, String.valueOf(topic.hashCode()));
        json.put(typeKey, typeTopic);
        json.put(nodeName, topic.getNodeName());
        json.put(stroke, topic.getStroke());
        json.put(color, topic.getColor());
        json.put(width, topic.getWidth());
        json.put(height, topic.getHeight());
        json.put(x, topic.getX());
        json.put(y, topic.getY());
        return json;
    }

    /**
     * Translates {@link Connection} into JSON format.
     *
     * @param connection {@link Connection}
     * @return {@link JSONObject} representation of the {@link Connection}
     */
    public JSONObject createJsonConnection(Connection connection) {
        JSONObject json = new JSONObject();
        String id = "id",
                typeKey = "type",
                typeConnection = "connection",
                nodeName = "nodeName",
                stroke = "stroke",
                color = "color",
                from = "from",
                to = "to";
        json.put(id, String.valueOf(connection.hashCode()));
        json.put(typeKey, typeConnection);
        json.put(nodeName, connection.getNodeName());
        json.put(stroke, connection.getStroke());
        json.put(color, connection.getColor());
        json.put(from, String.valueOf(connection.getFrom().hashCode()));
        json.put(to, String.valueOf(connection.getTo().hashCode()));
        return json;
    }

    /**
     * Parses given {@link JSONObject} into a {@link Project}.
     *
     * @param json {@link JSONObject} source object.
     * @return {@link Project} if successful, null otherwise.
     */
    private Project parseJsonProject(JSONObject json) {
        try {
            String nodeName = "nodeName",
                    authorName = "authorName",
                    filepath = "filepath",
                    children = "children";
            if (!json.has(nodeName)) {
                AppCore.getInstance().getMessageGenerator().error("Project name not specified");
                return null;
            }
            if (!json.has(authorName)) {
                AppCore.getInstance().getMessageGenerator().error("Project author not specified");
                return null;
            }
            if (!json.has(filepath)) {
                AppCore.getInstance().getMessageGenerator().error("Project filepath not specified");
                return null;
            }

            Project project = new Project(
                    json.getString(nodeName),
                    json.getString(authorName),
                    json.getString(filepath)
            );

            if (!json.has(children)) {
                return project;
            }

            JSONArray mindMaps = json.getJSONArray(children);
            for (int i = 0; i < mindMaps.length(); i++) {
                JSONObject child = mindMaps.getJSONObject(i);
                MindMap mindMap = parseJsonMindMap(child);
                if (mindMap == null) {
                    // error parsing
                    continue;
                }

                project.addChild(mindMap);
            }

            return project;

        } catch (Exception e) {
            // TODO runtime errors expected when parsing
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses given {@link JSONObject} into a {@link MindMap}.
     *
     * @param json {@link JSONObject} source object.
     * @return {@link MindMap} if successful, null otherwise.
     */
    private MindMap parseJsonMindMap(JSONObject json) {
        try {
            String nodeName = "nodeName",
                    template = "template",
                    children = "children";

            if (!json.has(nodeName)) {
                AppCore.getInstance().getMessageGenerator().error("Mind map name not specified");
                return null;
            }
            if (!json.has(template)) {
                AppCore.getInstance().getMessageGenerator().error("Mind map template not specified");
                return null;
            }

            MindMap mindMap = new MindMap(
                    json.getBoolean(template),
                    json.getString(nodeName)
            );

            if (!json.has(children)) {
                return mindMap;
            }

            JSONArray elements = json.getJSONArray(children);

            // go through topics first, then connections
            String typeKey = "type", typeTopic = "topic", typeConnection = "connection";
            Map<String, Topic> pool = new HashMap<>();

            for (int i = 0; i < elements.length(); i++) {
                JSONObject child = elements.getJSONObject(i);
                if (!child.has(typeKey)) continue;
                if (!child.getString(typeKey).equals(typeTopic)) continue;
                Element e = parseJsonTopic(child, pool);
                if (e != null) {
                    mindMap.addChild(e);
                }
            }

            for (int i = 0; i < elements.length(); i++) {
                JSONObject child = elements.getJSONObject(i);
                if (!child.has(typeKey)) continue;
                if (!child.getString(typeKey).equals(typeConnection)) continue;
                Element e = parseJsonConnection(child, pool);
                if (e != null) {
                    mindMap.addChild(e);
                }
            }

            return mindMap;
        } catch (Exception e) {
            // TODO runtime errors expected when parsing
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses given {@link JSONObject} into a {@link Topic}.
     *
     * @param json {@link JSONObject} source object.
     * @param pool Map of old Topic IDs and current instances.
     * @return {@link Topic} if successful, null otherwise.
     */
    private Element parseJsonTopic(JSONObject json, Map<String, Topic> pool) {
        try {
            String id = "id",
                    nodeName = "nodeName",
                    stroke = "stroke",
                    color = "color",
                    width = "width",
                    height = "height",
                    x = "x",
                    y = "y";

            if (!json.has(id)) {
                AppCore.getInstance().getMessageGenerator().error("Topic id not specified");
                return null;
            }
            if (!json.has(nodeName)) {
                AppCore.getInstance().getMessageGenerator().error("Topic name not specified");
                return null;
            }
            if (!json.has(stroke)) {
                AppCore.getInstance().getMessageGenerator().error("Topic stroke not specified");
                return null;
            }
            if (!json.has(color)) {
                AppCore.getInstance().getMessageGenerator().error("Topic color not specified");
                return null;
            }
            if (!json.has(width)) {
                AppCore.getInstance().getMessageGenerator().error("Topic width not specified");
                return null;
            }
            if (!json.has(height)) {
                AppCore.getInstance().getMessageGenerator().error("Topic height not specified");
                return null;
            }
            if (!json.has(x)) {
                AppCore.getInstance().getMessageGenerator().error("Topic x not specified");
                return null;
            }
            if (!json.has(y)) {
                AppCore.getInstance().getMessageGenerator().error("Topic y not specified");
                return null;
            }

            Topic t = new Topic(
                    json.getString(nodeName),
                    json.getInt(stroke),
                    json.getString(color),
                    json.getInt(x),
                    json.getInt(y),
                    json.getInt(width),
                    json.getInt(height)
            );

            pool.put(json.getString(id), t);

            return t;

        } catch (Exception e) {
            // TODO runtime errors expected when parsing
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses given {@link JSONObject} into a {@link Connection}.
     *
     * @param json {@link JSONObject} source object.
     * @param pool Map of old Topic IDs and current instances.
     * @return {@link Connection} if successful, null otherwise.
     */
    private Element parseJsonConnection(JSONObject json, Map<String, Topic> pool) {
        try {
            String id = "id",
                    nodeName = "nodeName",
                    stroke = "stroke",
                    color = "color",
                    from = "from",
                    to = "to";

            if (!json.has(id)) {
                AppCore.getInstance().getMessageGenerator().error("Connection id not specified");
                return null;
            }
            if (!json.has(nodeName)) {
                AppCore.getInstance().getMessageGenerator().error("Connection name not specified");
                return null;
            }
            if (!json.has(stroke)) {
                AppCore.getInstance().getMessageGenerator().error("Connection stroke not specified");
                return null;
            }
            if (!json.has(color)) {
                AppCore.getInstance().getMessageGenerator().error("Connection color not specified");
                return null;
            }
            if (!json.has(from)) {
                AppCore.getInstance().getMessageGenerator().error("Connection from not specified");
                return null;
            }
            if (!json.has(to)) {
                AppCore.getInstance().getMessageGenerator().error("Connection to not specified");
                return null;
            }

            from = json.getString(from);
            to = json.getString(to);
            if (pool.getOrDefault(from, null) == null) {
                AppCore.getInstance().getMessageGenerator().error("Connection to not loaded");
                return null;
            }
            if (pool.getOrDefault(to, null) == null) {
                AppCore.getInstance().getMessageGenerator().error("Connection to not loaded");
                return null;
            }

            return new Connection(
                    json.getString(nodeName),
                    json.getInt(stroke),
                    json.getString(color),
                    pool.get(from),
                    pool.get(to)
            );

        } catch (Exception e) {
            // TODO runtime errors expected when parsing
            e.printStackTrace();
            return null;
        }
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
    public boolean exportMindMap(MindMap mindMap, RenderedImage renderedImage) {
        // TODO this currently saves to each parent
        for (IMapNodeComposite parent : mindMap.parents) {
            Project p = (Project) parent;
            File f = new File(p.getFilepath());
            String path = f.getParent() + "\\" + mindMap.getNodeName() + ".png";
            try {
                File target = new File(path);
                ImageIO.write(renderedImage, "png", target);
                AppCore.getInstance().getMessageGenerator().log("Mind map saved to " + path);
                if (!Desktop.isDesktopSupported()) return true;
                Desktop.getDesktop().open(target);
            } catch (IOException e) {
                AppCore.getInstance().getMessageGenerator().error(
                        "Failed to export mind map " + p.getNodeName() +
                                "\n:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        return true;
    }

    @Override
    public void log(String line) {
        try {
            String logPath = AppCore.getInstance().getConstants().FILESYSTEM_LOCAL_LOGS_FOLDER() +
                    "log-" +
                    DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()) +
                    ".txt";
            Files.createDirectories(Paths.get(applicationFramework.getConstants().FILESYSTEM_LOCAL_LOGS_FOLDER()));
            FileOutputStream fos = new FileOutputStream(logPath, true);
            fos.write(line.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (Exception e) {
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

                if (Boolean.parseBoolean(
                        String.valueOf(AppCore.getInstance().getConfigHandler()
                                .get("autosave", false)))) {
                    saveProject(p);
                }
                break;
            }

        } else if (message instanceof IMapNode.Message) {
            switch (((IMapNode.Message) message).getStatus()) {

                case EDITED: {
                    if (Boolean.parseBoolean(
                            String.valueOf(AppCore.getInstance().getConfigHandler()
                                    .get("autosave", false)))) {
                        break;
                    }
                    IMapNode sender = (IMapNode) ((IMapNode.Message) message).getData().getSender();

                    // Child because we want to avoid getting the "root-est" ProjectExplorer; if null, getChild() is a Project
                    Set<Project> projects = getProjects(sender);

                    // For each project the sender is child of, encode op for the specific change
                    Iterator<Project> iterator = projects.iterator();
                    while (iterator.hasNext()) {
                        Project p = iterator.next();
                        saveProject(p);
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
     * Erases an existing project file. TODO this should use project name as filename
     *
     * @param project Project file to erase
     * @param backup  Whether to erase the actual file or its backup.
     * @return Returns true if no error, false otherwise.
     */
    private boolean deleteProjectFile(Project project, boolean backup) {
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
     * Creates file for the project.
     *
     * @param project Project to set up the file for.
     * @param backup  Whether to make this into a backup DB or not.
     */
    private boolean createProjectFile(Project project, boolean backup) {
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
     * Erases an existing mind map file.
     *
     * @param mindMap Mind map file to erase
     * @return Returns true if no error, false otherwise.
     */
    private boolean deleteMindMapFile(MindMap mindMap) {
        String fileName = this.parseMindMapFilepath(mindMap);
        try {
            Files.deleteIfExists(Paths.get(fileName));
            return true;
        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to erase project database " + fileName);
            return false;
        }
    }

    /**
     * Creates file for the mind map.
     *
     * @param mindMap Mind map to set up the file for.
     */
    private boolean createMindMapFile(MindMap mindMap) {
        String fileName = this.parseMindMapFilepath(mindMap);
        try {
            Files.createDirectories(Paths.get(applicationFramework.getConstants().MINDMAP_TEMPLATES_DIR()));

            new File(fileName).createNewFile();
            return true;
        } catch (IOException e) {
            // AppCore.getInstance().getMessageGenerator().error("Failed to setup project database at " + fileName);
            e.printStackTrace();
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
        String t = applicationFramework.getConstants().FILESYSTEM_LOCAL_PROJECTS_FOLDER();
        t = t + project.getFilepath();
        if (!t.endsWith(".gerumap.json")) {
            t = t + ".gerumap.json";
        }
        return t + (backup ? ".bak" : "");
    }

    /**
     * Returns the fully-qualified path to the mind map (template), taking all parameters into account.
     *
     * @param mindMap {@link MindMap}
     * @return The fully qualified filepath.
     */
    private String parseMindMapFilepath(MindMap mindMap) {
        String t = applicationFramework.getConstants().MINDMAP_TEMPLATES_DIR();
        t = t + mindMap.getNodeName();
        if (!t.endsWith(".gerumap.json")) {
            t = t + ".gerumap.json";
        }
        return t;
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
}
