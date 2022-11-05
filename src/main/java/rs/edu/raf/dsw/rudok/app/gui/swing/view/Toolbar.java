package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class Toolbar extends JToolBar {
    public Toolbar() {
        JButton btnNew = new JButton("New");
        JButton btwInfo = new JButton("Info");

        this.add(btnNew);
        this.add(btwInfo);

        this.setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getNewAction());
        btwInfo.addActionListener(MainFrame.getInstance().getActionManager().getStudentInfoAction());

    }
}
