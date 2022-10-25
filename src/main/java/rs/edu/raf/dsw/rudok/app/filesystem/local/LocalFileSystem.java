package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalFileSystem implements IFileSystem {

    @Override
    public void save(String path, Serializable serializable) {
        try {
            Files.createDirectories(Paths.get(path).getParent());

            // open output streams
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // write object
            oos.writeObject(serializable);

            // close output streams
            oos.close();
            fos.close();
        } catch (Exception e) {
            // TODO to error component
        }
    }

    @Override
    public Serializable load(String path) {

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        try {
            // open output streams
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // write object
            Serializable obj = (Serializable) ois.readObject();

            // close output streams
            ois.close();
            fis.close();

            return obj;
        } catch (Exception e) {
            // TODO to error component
            return null;
        }
    }
}
