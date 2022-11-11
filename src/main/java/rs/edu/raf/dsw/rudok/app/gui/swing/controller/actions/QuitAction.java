package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class QuitAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO save all projects
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );
        if (result == 0) {
            System.exit(0);
        }
    }
}
