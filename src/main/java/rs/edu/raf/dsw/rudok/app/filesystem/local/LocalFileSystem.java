package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class LocalFileSystem implements IFileSystem {

    @Override
    public void save(String relPath, Serializable serializable) {
        try {
            // open output streams
            FileOutputStream fos = new FileOutputStream(relPath);
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
    public Serializable load(String relPath) {
        try {
            // open output streams
            FileInputStream fis = new FileInputStream(relPath);
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

    @Override
    public IAddon loadAddon(String classname) {
        File f = new File("./add-ons");
        try {
            URL url = f.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            Class<?> cls = cl.loadClass(classname);
            return (IAddon) cls.getConstructor().newInstance();
        } catch (Exception e) {
            // TODO call error handler here
        }
        return null;
    }
}
