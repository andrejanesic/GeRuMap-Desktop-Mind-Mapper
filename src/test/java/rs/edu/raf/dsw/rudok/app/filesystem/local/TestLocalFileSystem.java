package rs.edu.raf.dsw.rudok.app.filesystem.local;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IConstants;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

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

    public static class TestIMapNodeComposite extends IMapNodeComposite {

    }

    /**
     * Generate a tree of IMapNodeComposites.
     *
     * @return Root of the tree.
     */
    private IMapNodeComposite tree(IMapNodeComposite root, int depth) {
        if (root == null) root = new TestIMapNodeComposite();
        if (depth == 0) return root;
        int children = new Random().nextInt(3) + 1;
        for (int i = 0; i < children; i++) {
            IMapNodeComposite child = tree(null, depth - 1);
            root.addChild(child);
        }
        return root;
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

    @Test
    public void testSaveProject() throws IOException {
        IMapNodeComposite root = tree(null, new Random().nextInt(5) + 1);

        String dirPath = "test/projects";
        String fileName = "default.gerumap";
        String relPath = dirPath + '/' + fileName;

        ApplicationFramework applicationFramework = new ApplicationFramework() {
            @Override
            public IConstants getConstants() {
                return new IConstants() {
                    @Override
                    public String FILESYSTEM_LOCAL_CONFIG_FOLDER() {
                        return null;
                    }

                    @Override
                    public String FILESYSTEM_LOCAL_PROJECTS_FOLDER() {
                        return temporaryFolder.getRoot().getAbsolutePath() + "/" + dirPath;
                    }
                };
            }
        };

        IFileSystem fs = new LocalFileSystem(applicationFramework);

        fs.saveProject(root);

        Assert.assertTrue(new File(temporaryFolder.getRoot().getAbsolutePath() + '/' + relPath).exists());
    }
}
