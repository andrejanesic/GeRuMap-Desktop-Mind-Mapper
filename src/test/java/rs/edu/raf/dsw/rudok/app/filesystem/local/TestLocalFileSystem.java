package rs.edu.raf.dsw.rudok.app.filesystem.local;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rs.edu.raf.dsw.rudok.app.Helper;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.constants.IConstants;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.repository.*;

import java.io.*;
import java.util.*;

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

        public TestIMapNodeComposite(String nodeName) {
            super(nodeName);
        }
    }

    /**
     * Generate a tree of IMapNodeComposites. The root is a ProjectExplorer, with sub-elements being Project, MindMap
     * and Element. Start with depth 4 for all, 3 for Project, 2 for MindMap and 1 for Element.
     *
     * @param depth Depth of the tree. Cannot be greater than 4.
     * @return Root of the tree.
     */
    private IMapNode tree(int depth) {
        if (depth > 4) throw new RuntimeException("Depth may not be > 4");
        IMapNodeComposite root = null;

        // if initial node
        switch (depth) {
            case 4:
                root = new ProjectExplorer(Helper.randString());
                break;
            case 3:
                String projectName = null, authorName = null, filepath = null;
                switch (new Random().nextInt(3)) {
                    case 2:
                        projectName = "Baz";
                        break;
                    case 1:
                        projectName = "Bar";
                        break;
                    case 0:
                        projectName = "Foo";
                }
                switch (new Random().nextInt(3)) {
                    case 2:
                        authorName = "Baz";
                        break;
                    case 1:
                        authorName = "Bar";
                        break;
                    case 0:
                        authorName = "Foo";
                }
                filepath = projectName.toLowerCase(Locale.ROOT) + ".grm";
                root = new Project(projectName, authorName, filepath);
                break;
            case 2:
                root = new MindMap(new Random().nextBoolean(), Helper.randString());
                break;
            case 1:
                return new Element(Helper.randString());
        }

        int children = new Random().nextInt(3) + 1;
        for (int i = 0; i < children; i++) {
            IMapNode child = tree(depth - 1);
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
    public void testSaveLoadProject() throws IOException {
        Project root = (Project) tree(3);
        String fPath = root.getFilepath();

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
                        return temporaryFolder.getRoot().getAbsolutePath();
                    }
                };
            }
        };

        IFileSystem fs = new LocalFileSystem(applicationFramework);

        fs.saveProject(root);

        Assert.assertTrue(new File(temporaryFolder.getRoot().getAbsolutePath() + '/' + fPath).exists());
        Assert.assertTrue(new File(temporaryFolder.getRoot().getAbsolutePath() + '/' + fPath).length() > 0);

        Project cmp = fs.loadProject(root.getFilepath());

        Assert.assertEquals(root.getNodeName(), cmp.getNodeName());
        Assert.assertEquals(root.getFilepath(), cmp.getFilepath());
        Assert.assertEquals(root.getAuthorName(), cmp.getAuthorName());

        // TODO add method for testing subtrees individually - when debugged by hand, it works!
    }
}
