package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class LocalFileSystem implements IFileSystem {

    private final ApplicationFramework applicationFramework;

    public LocalFileSystem(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }

    @Override
    public void saveConfig(Map<String, String> config) {
        try {
            String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER() +
                    config.getOrDefault("config", "default") +
                    ".ser";
            Files.createDirectories(Paths.get(applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER()));

            // open output streams
            FileOutputStream fos = new FileOutputStream(filePath);
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
    public Map<String, String> loadConfig(String name) {

        /**
         * TODO DANGER! Deserialized data should be checked!
         */

        String filePath = applicationFramework.getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER() +
                name +
                ".ser";

        try {
            // open output streams
            FileInputStream fis = new FileInputStream(filePath);
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
