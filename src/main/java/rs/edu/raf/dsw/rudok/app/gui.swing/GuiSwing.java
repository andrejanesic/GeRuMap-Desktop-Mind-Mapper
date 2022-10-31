package rs.edu.raf.dsw.rudok.app.gui.swing;

import rs.edu.raf.dsw.rudok.app.core.Gui;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;

public class GuiSwing implements Gui {

    @Override
    public void run() {
        MainFrame.getInstance().setVisible(true);
    }
}
