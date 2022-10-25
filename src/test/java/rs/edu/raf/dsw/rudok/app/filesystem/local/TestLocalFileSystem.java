package rs.edu.raf.dsw.rudok.app.filesystem.local;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.*;
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
    public void testSave() throws IOException, ClassNotFoundException {
        IFileSystem fs = new LocalFileSystem();
        int foo = 25, bar = 10;
        String dirPath = "test/";
        String fileName = "TestSerializable.gerumap";
        String relPath = dirPath + fileName;
        Serializable testSerializable = new TestSerializable(foo, bar);

        fs.save(temporaryFolder.getRoot().getAbsolutePath() + "/" + relPath, testSerializable);

        FileInputStream fis = new FileInputStream(
                temporaryFolder.getRoot().getAbsolutePath() + "/" + relPath
        );
        ObjectInputStream ois = new ObjectInputStream(fis);

        Assert.assertEquals(testSerializable, ois.readObject());
    }

    @Test
    public void testLoad() throws IOException, ClassNotFoundException {
        IFileSystem fs = new LocalFileSystem();
        int foo = 25, bar = 10;
        String dirPath = "test/";
        String fileName = "TestSerializable.gerumap";
        String relPath = dirPath + fileName;
        Serializable testSerializable = new TestSerializable(foo, bar);

        temporaryFolder.newFolder(dirPath);

        FileOutputStream fos = new FileOutputStream(
                temporaryFolder.getRoot().getAbsolutePath() + "/" + relPath
        );
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(testSerializable);

        Object obj = fs.load(temporaryFolder.getRoot().getAbsolutePath() + "/" + relPath);

        Assert.assertEquals(testSerializable, obj);
    }
}
