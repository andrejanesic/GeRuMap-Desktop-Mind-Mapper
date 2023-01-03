package rs.edu.raf.dsw.rudok.app.confighandler.standard;

import org.junit.Assert;
import org.junit.Test;
import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.confighandler.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import java.util.HashMap;
import java.util.Map;

public class TestStandardConfigHandler {

    @Test
    public void testLoadConfig() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {
                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return new HashMap<String, String>() {{
                            put("test", "abc");
                        }};
                    }

                    @Override
                    public boolean saveProject(Project project) {
                        return false;
                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public MindMap loadMindMapTemplate(String path) {
                        return null;
                    }

                    @Override
                    public boolean saveMindMapTemplate(MindMap mindMap) {
                        return false;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        return null;
                    }

                    @Override
                    public void log(String line) {

                    }
                };
            }
        });

        Assert.assertNull(iConfigHandler.get("test"));

        iConfigHandler.loadConfig("abc");

        Assert.assertEquals(iConfigHandler.get("test"), "abc");
    }

    @Test
    public void testResetConfig() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {
                    @Override
                    public void log(String line) {

                    }

                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
                    }

                    @Override
                    public boolean saveProject(Project project) {
                        return false;
                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public MindMap loadMindMapTemplate(String path) {
                        return null;
                    }

                    @Override
                    public boolean saveMindMapTemplate(MindMap mindMap) {
                        return false;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        return null;
                    }
                };
            }
        });

        iConfigHandler.loadConfig("abc");

        Assert.assertEquals(iConfigHandler.get("language"), "French");

        iConfigHandler.resetConfig();

        Assert.assertEquals(iConfigHandler.get("language"), "English");
    }

    @Test
    public void testSaveConfig() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {
                    @Override
                    public void saveConfig(Map<String, String> config) {
                        Assert.assertEquals(((HashMap<String, String>) config).get("abc"), "def");
                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
                    }

                    @Override
                    public boolean saveProject(Project project) {
                        return false;
                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public MindMap loadMindMapTemplate(String path) {
                        return null;
                    }

                    @Override
                    public boolean saveMindMapTemplate(MindMap mindMap) {
                        return false;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        return null;
                    }

                    @Override
                    public void log(String line) {

                    }
                };
            }
        });

        iConfigHandler.set("abc", "def");
        iConfigHandler.saveConfig();
    }

    @Test
    public void testSet() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {
                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
                    }

                    @Override
                    public boolean saveProject(Project project) {
                        return false;
                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public MindMap loadMindMapTemplate(String path) {
                        return null;
                    }

                    @Override
                    public boolean saveMindMapTemplate(MindMap mindMap) {
                        return false;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        return null;
                    }

                    @Override
                    public void log(String line) {

                    }
                };
            }
        });

        Assert.assertNull(iConfigHandler.get("xoxo"));

        iConfigHandler.set("xoxo", "testy");

        Assert.assertEquals(iConfigHandler.get("xoxo"), "testy");
    }

    @Test
    public void testGet() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {
                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
                    }

                    @Override
                    public boolean saveProject(Project project) {
                        return false;
                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public MindMap loadMindMapTemplate(String path) {
                        return null;
                    }

                    @Override
                    public boolean saveMindMapTemplate(MindMap mindMap) {
                        return false;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        return null;
                    }

                    @Override
                    public void log(String line) {

                    }
                };
            }
        });

        Assert.assertNull(iConfigHandler.get("123"));

        iConfigHandler.set("123", "adadad");

        Assert.assertEquals(iConfigHandler.get("123"), "adadad");
    }

    @Test
    public void testGetOrDefault() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {
                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
                    }

                    @Override
                    public boolean saveProject(Project project) {
                        return false;
                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public MindMap loadMindMapTemplate(String path) {
                        return null;
                    }

                    @Override
                    public boolean saveMindMapTemplate(MindMap mindMap) {
                        return false;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        return null;
                    }

                    @Override
                    public void log(String line) {

                    }
                };
            }
        });

        Assert.assertNull(iConfigHandler.get("123"));

        iConfigHandler.set("123", "adadad");

        Assert.assertEquals(iConfigHandler.getOrDefault("abcdef123", "foobar"), "foobar");
    }
}
