package rs.edu.raf.dsw.rudok.app.addon.standard;

import org.junit.Assert;
import org.junit.Test;
import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.addon.IAddonManager;
import rs.edu.raf.dsw.rudok.app.addon.IAddonMeta;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.core.ISerializer;
import rs.edu.raf.dsw.rudok.app.gui.IGuiAddon;

import java.io.Serializable;
import java.util.Iterator;

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
                    public boolean loadConfig(String relPath) {
                        return false;
                    }

                    @Override
                    public void saveConfig(String relPath) {

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
            public ISerializer getSerializer() {
                return new ISerializer() {

                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
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
                    public boolean loadConfig(String relPath) {
                        return false;
                    }

                    @Override
                    public void saveConfig(String relPath) {

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
            public ISerializer getSerializer() {
                return new ISerializer() {

                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
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
