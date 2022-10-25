package rs.edu.raf.dsw.rudok.app.filesystem.remote;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.filesystem.local.LocalFileSystem;

/**
 * Remote file system. This functionality enables the app to read/write data from a remote server, e.g. company's
 * app data cluster.
 */
public class RemoteFileSystem extends LocalFileSystem {

    private final ApplicationFramework applicationFramework;

    public RemoteFileSystem(ApplicationFramework applicationFramework) {
        this.applicationFramework = applicationFramework;
    }
}
