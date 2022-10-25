package rs.edu.raf.dsw.rudok.app.filesystem.local;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConstants;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestLocalFileSystem {

    public static class TestSerializable implements Serializable {

        private int foo;

        private int bar;

        public TestSerializable(int foo, int bar) {
            this.foo = foo;
            this.bar = bar;
        }

        public int getFoo() {
            return foo;
        }

        public void setFoo(int foo) {
            this.foo = foo;
        }

        public int getBar() {
            return bar;
        }

        public void setBar(int bar) {
            this.bar = bar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestSerializable that = (TestSerializable) o;
            return getFoo() == that.getFoo() && getBar() == that.getBar();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getFoo(), getBar());
        }
    }

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSaveConfig() throws IOException, ClassNotFoundException {
        String dirPath = "test/config/";
        String fileName = "default.ser";
        String relPath = dirPath + fileName;
        Map<String, String> testSerializable = new HashMap<>();

        ApplicationFramework applicationFramework = new ApplicationFramework() {
            @Override
            public IConstants getConstants() {
                return new IConstants() {
                    @Override
                    public String FILESYSTEM_LOCAL_CONFIG_FOLDER() {
                        return temporaryFolder.getRoot().getAbsolutePath() + "/" + dirPath;
                    }

                    @Override
                    public String FILESYSTEM_LOCAL_PROJECTS_FOLDER() {
                        return null;
                    }
                };
            }
        };

        IFileSystem fs = new LocalFileSystem(applicationFramework);

        fs.saveConfig(testSerializable);

        FileInputStream fis = new FileInputStream(
                temporaryFolder.getRoot().getAbsolutePath() + "/" + relPath
        );
        ObjectInputStream ois = new ObjectInputStream(fis);

        Assert.assertEquals(testSerializable, ois.readObject());
    }

    @Test
    public void testLoadConfig() throws IOException {
        String dirPath = "test/config/";
        String fileName = "default.ser";
        String relPath = dirPath + fileName;
        Map<String, String> testSerializable = new HashMap<>();

        ApplicationFramework applicationFramework = new ApplicationFramework() {
            @Override
            public IConstants getConstants() {
                return new IConstants() {
                    @Override
                    public String FILESYSTEM_LOCAL_CONFIG_FOLDER() {
                        return temporaryFolder.getRoot().getAbsolutePath() + "/" + dirPath;
                    }

                    @Override
                    public String FILESYSTEM_LOCAL_PROJECTS_FOLDER() {
                        return null;
                    }
                };
            }
        };

        temporaryFolder.newFolder(dirPath);

        FileOutputStream fos = new FileOutputStream(
                temporaryFolder.getRoot().getAbsolutePath() + "/" + relPath
        );
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(testSerializable);

        IFileSystem fs = new LocalFileSystem(applicationFramework);

        Object obj = fs.loadConfig("default");

        Assert.assertEquals(testSerializable, obj);
    }
}
