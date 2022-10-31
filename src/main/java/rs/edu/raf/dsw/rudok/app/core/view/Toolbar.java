package rs.edu.raf.dsw.rudok.app.core.view;

import rs.edu.raf.dsw.rudok.app.core.view.dialogs.StudentInfoDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar{
    public Toolbar() {
        JButton btnNew = new JButton("New");
        JButton btwInfo = new JButton("Info");

        this.add(btnNew);
        this.add(btwInfo);

        this.setFloatable(false);

        btwInfo.addActionListener(MainFrame.getInstance().getActionManager().getStudentInfoAction());

    }
}
