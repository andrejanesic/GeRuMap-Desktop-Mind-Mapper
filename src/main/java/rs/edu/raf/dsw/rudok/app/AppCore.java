package rs.edu.raf.dsw.rudok.app;

import rs.edu.raf.dsw.rudok.app.confighandler.standard.StandardConfigHandler;
import rs.edu.raf.dsw.rudok.app.constants.standard.StandardConstants;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.filesystem.local.LocalFileSystem;
import rs.edu.raf.dsw.rudok.app.gui.swing.GuiSwing;
import rs.edu.raf.dsw.rudok.app.logger.console.file.FileLogger;
import rs.edu.raf.dsw.rudok.app.messagegenerator.standard.StandardMessageGenerator;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

/**
 * Main application class.
 */
public class AppCore extends ApplicationFramework {

    private AppCore() {
        this.initialize(
                new StandardConstants(),
                new StandardMessageGenerator(),
                new StandardConfigHandler(this),
                new GuiSwing(),
                (ProjectExplorer) MapNodeFactoryUtils.getFactory(null).createNode(),
                new LocalFileSystem(this),
                new FileLogger()
        );
    }

    public static void main(String[] args) {
        AppCoreSingleton.INSTANCE.run();
    }

    public static ApplicationFramework getInstance() {
        return AppCoreSingleton.INSTANCE;
    }

    /**
     * Singleton wrapper.
     */
    private static class AppCoreSingleton {
        private static final AppCore INSTANCE = new AppCore();
    }
}
