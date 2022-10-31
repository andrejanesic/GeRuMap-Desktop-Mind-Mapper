package rs.edu.raf.dsw.rudok.app.gui.swing;

import rs.edu.raf.dsw.rudok.app.gui.IGui;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;

public class GuiSwing implements IGui {

    @Override
    public void run() {
        MainFrame.getInstance().setVisible(true);
    }
}
