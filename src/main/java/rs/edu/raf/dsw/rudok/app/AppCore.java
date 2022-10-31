package rs.edu.raf.dsw.rudok.app;

import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.gui.swing.GuiSwing;

public class AppCore extends ApplicationFramework {

    public static void main(String[] args) {
        GuiSwing gui = new GuiSwing();
        gui.run();
    }
}
