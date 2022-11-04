package rs.edu.raf.dsw.rudok.app;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.gui.swing.GuiSwing;

/**
 * Main application class.
 */
public class AppCore extends ApplicationFramework {

    private AppCore() {

    }

    public static void main(String[] args) {
        GuiSwing gui = new GuiSwing();
        gui.run();
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
