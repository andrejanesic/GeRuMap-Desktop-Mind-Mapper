package rs.edu.raf.dsw.rudok.app.gui.swing.controller.listeners;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class WindowListener implements java.awt.event.WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        JFrame frame= (JFrame) e.getComponent();
        int code = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this app?","Close the app?",JOptionPane.YES_NO_OPTION);
        if (code != JOptionPane.YES_OPTION){
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
        else{
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }


    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
