package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import javax.swing.*;

public class Menu extends JMenu {
    public Menu() {
        JMenu File = new JMenu("File");
        JMenuItem New = new JMenuItem("New");
        JMenu Help = new JMenu("Help");
        JMenuItem Edit = new JMenuItem("Edit");

        File.add(New);
        Edit.add(Help);

        this.add(File);
        this.add(Help);
    }

}
