package rs.edu.raf.dsw.rudok.app.addon.standard;

import org.junit.Assert;
import org.junit.Test;
import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.addon.IAddonManager;
import rs.edu.raf.dsw.rudok.app.addon.IAddonMeta;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;
import rs.edu.raf.dsw.rudok.app.gui.IGuiAddon;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

import java.util.Iterator;
import java.util.Map;

public class TestStandardAddonManager {

    public static class TestAddon extends IAddon {

        @Override
        public void initialize(ApplicationFramework applicationFramework) {

        }

        @Override
        public IGuiAddon getGui() {
            return null;
        }

        @Override
        public IAddonMeta getMeta() {
            return new IAddonMeta() {
                @Override
                public String getName() {
                    return "FooBarAddon";
                }

                @Override
                public String getVersion() {
                    return null;
                }

                @Override
                public String getAuthor() {
                    return null;
                }

                @Override
                public String getWebsite() {
                    return null;
                }
            };
        }
    }

    @Test
    public void testLoadAddons() {
        ApplicationFramework applicationFramework = new ApplicationFramework() {

            @Override
            public IConfigHandler getConfigHandler() {
                return new IConfigHandler() {
                    @Override
                    public boolean loadConfig(String name) {
                        return false;
                    }

                    @Override
                    public void saveConfig() {

                    }

                    @Override
                    public void resetConfig() {

                    }

                    @Override
                    public void set(String key, String val) {

                    }

                    @Override
                    public String get(String key) {
                        if (key.equals("addons")) {
                            return "sample";
                        }
                        return null;
                    }

                    @Override
                    public Object get(String key, Object defaultValue) {
                        return null;
                    }
                };
            }

            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {

                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return null;
                    }

                    @Override
                    public void saveProject(IMapNodeComposite project) {

                    }

                    @Override
                    public IMapNodeComposite loadProject(String name) {
                        return null;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        try {
                            return TestAddon.class.getConstructor().newInstance();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                };
            }
        };

        IAddonManager addonManager = new StandardAddonManager(applicationFramework);

        Assert.assertEquals(0, addonManager.getAddons().size());

        addonManager.loadAddons();

        Assert.assertEquals(1, addonManager.getAddons().size());

        Iterator<IAddon> iterator = addonManager.getAddons().iterator();

        Assert.assertEquals("FooBarAddon", iterator.next().getMeta().getName());
    }

    @Test
    public void testInitializeAddons() {
        ApplicationFramework applicationFramework = new ApplicationFramework() {

            @Override
            public IConfigHandler getConfigHandler() {
                return new IConfigHandler() {
                    @Override
                    public boolean loadConfig(String name) {
                        return false;
                    }

                    @Override
                    public void saveConfig() {

                    }

                    @Override
                    public void resetConfig() {

                    }

                    @Override
                    public void set(String key, String val) {

                    }

                    @Override
                    public String get(String key) {
                        if (key.equals("addons")) {
                            return "sample";
                        }
                        return null;
                    }

                    @Override
                    public Object get(String key, Object defaultValue) {
                        return null;
                    }
                };
            }

            @Override
            public IFileSystem getFileSystem() {
                return new IFileSystem() {

                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return null;
                    }

                    @Override
                    public void saveProject(IMapNodeComposite project) {

                    }

                    @Override
                    public IMapNodeComposite loadProject(String name) {
                        return null;
                    }

                    @Override
                    public IAddon loadAddon(String classname) {
                        try {
                            return TestAddon.class.getConstructor().newInstance();
                        } catch (Exception e) {
                            return null;
                        }
                    }
                };
            }
        };

        IAddonManager addonManager = new StandardAddonManager(applicationFramework);

        Assert.assertEquals(0, addonManager.getAddons().size());

        addonManager.loadAddons();

        Assert.assertEquals(1, addonManager.getAddons().size());

        Iterator<IAddon> iterator = addonManager.getAddons().iterator();

        Assert.assertEquals("FooBarAddon", iterator.next().getMeta().getName());

        addonManager.initializeAddons();
    }
}
