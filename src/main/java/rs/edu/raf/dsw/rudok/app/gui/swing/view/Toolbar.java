package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class Toolbar extends JToolBar {
    public Toolbar() {
        JButton btnNew = new JButton("New");
        JButton btnDelete = new JButton("Delete");

        this.add(btnNew);
        this.add(btnDelete);

        this.setFloatable(false);

        btnNew.addActionListener(MainFrame.getInstance().getActionManager().getNewAction());
        btnDelete.addActionListener(MainFrame.getInstance().getActionManager().getDeleteAction());
    }
}
