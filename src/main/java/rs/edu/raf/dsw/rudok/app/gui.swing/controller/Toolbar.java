package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import javax.swing.*;

public class Toolbar extends JToolBar {
    public Toolbar() {
        JButton btnNew = new JButton("New");
        JButton btwInfo = new JButton("Info");

        this.add(btnNew);
        this.add(btwInfo);
    }
}
