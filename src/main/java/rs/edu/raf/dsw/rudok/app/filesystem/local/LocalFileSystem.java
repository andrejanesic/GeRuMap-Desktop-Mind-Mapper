package rs.edu.raf.dsw.rudok.app.filesystem.local;

import rs.edu.raf.dsw.rudok.app.addon.IAddon;
import rs.edu.raf.dsw.rudok.app.core.IFileSystem;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;

public class LocalFileSystem implements IFileSystem {
    @Override
    public void save(String relPath, Serializable serializable) {

    }

    @Override
    public Serializable load(String path) {
        return null;
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
