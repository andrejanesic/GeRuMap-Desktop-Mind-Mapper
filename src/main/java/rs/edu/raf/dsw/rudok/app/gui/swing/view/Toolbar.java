package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class Toolbar extends JToolBar {
    public Toolbar() {
        JButton btnNew = new JButton("New");
        JButton btwInfo = new JButton("Info");
        JButton btnDelete = new JButton("Delete");
        JButton btnAuthor = new JButton("Author");

        this.add(btnNew);
        this.add(btwInfo);
        this.add(btnDelete);
        this.add(btnAuthor);

        this.setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getNewAction());
        btwInfo.addActionListener(MainFrame.getInstance().getActionManager().getStudentInfoAction());
        btnNew.addActionListener(MainFrame.getInstance().getActionManager().getNewAction());
        btnDelete.addActionListener(MainFrame.getInstance().getActionManager().getDeleteAction());
        btnAuthor.addActionListener(MainFrame.getInstance().getActionManager().getProjectAuthorAction());

    }
}
