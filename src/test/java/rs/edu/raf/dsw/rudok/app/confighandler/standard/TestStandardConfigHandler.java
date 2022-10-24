package rs.edu.raf.dsw.rudok.app.confighandler.standard;

import org.junit.Assert;
import org.junit.Test;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.core.ISerializer;

import java.io.Serializable;
import java.util.HashMap;

public class TestStandardConfigHandler {

    @Test
    public void testLoadConfig() {

        IConfigHandler iConfigHandler = new StandardConfigHandler(new ApplicationFramework() {
            @Override
            public ISerializer getSerializer() {
                return new ISerializer() {
                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
                        return new HashMap<String, String>() {{
                            put("test", "abc");
                        }};
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
            public ISerializer getSerializer() {
                return new ISerializer() {
                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
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
            public ISerializer getSerializer() {
                return new ISerializer() {
                    @Override
                    public void save(Serializable serializable) {
                        Assert.assertEquals(((HashMap<String, String>) serializable).get("abc"), "def");
                    }

                    @Override
                    public Serializable load(String path) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
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
            public ISerializer getSerializer() {
                return new ISerializer() {
                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
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
            public ISerializer getSerializer() {
                return new ISerializer() {
                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
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
            public ISerializer getSerializer() {
                return new ISerializer() {
                    @Override
                    public void save(Serializable serializable) {

                    }

                    @Override
                    public Serializable load(String path) {
                        return new HashMap<String, String>() {{
                            put("language", "French");
                        }};
                    }
                };
            }
        });

        Assert.assertNull(iConfigHandler.get("123"));

        iConfigHandler.set("123", "adadad");

        Assert.assertEquals(iConfigHandler.getOrDefault("abcdef123", "foobar"), "foobar");
    }
}
