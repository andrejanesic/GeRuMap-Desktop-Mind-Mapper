package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class LocalFileSystem implements IFileSystem {

    @Override
    public void saveConfig(String path, Map<String, String> config) {
        try {
            Files.createDirectories(Paths.get(path).getParent());

            // open output streams
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // write object
            oos.writeObject(config);

            // close output streams
            oos.close();
            fos.close();
        } catch (Exception e) {
            // TODO to error component
        }
    }

    @Override
    public Map<String, String> loadConfig(String path) {

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        try {
            // open output streams
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // write object
            Map<String, String> obj = (Map<String, String>) ois.readObject();

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
