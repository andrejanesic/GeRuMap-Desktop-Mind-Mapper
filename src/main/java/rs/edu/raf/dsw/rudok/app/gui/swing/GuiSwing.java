package rs.edu.raf.dsw.rudok.app.gui.swing;

import rs.edu.raf.dsw.rudok.app.gui.IGui;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;

import javax.swing.*;

public class GuiSwing extends IGui {

    @Override
    public void run() {
        MainFrame.getInstance().setVisible(true);
    }

    @Override
    public void showDialog(IMessageGenerator.Message message) {
        switch (message.getData().getType()) {
            case LOG:
                break;
            case ERROR:
                JOptionPane.showMessageDialog(
                        MainFrame.getInstance(),
                        message.getData().getContent(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                break;
            case WARNING:
                JOptionPane.showMessageDialog(
                        MainFrame.getInstance(),
                        message.getData().getContent(),
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                break;
        }
    }
}
