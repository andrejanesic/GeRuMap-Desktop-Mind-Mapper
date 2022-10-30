package rs.edu.raf.dsw.rudok.app.core.view;

import rs.edu.raf.dsw.rudok.app.core.view.dialogs.StudentInfoDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar{
    public Toolbar() {
        JButton btnNew = new JButton("New");
        JButton btwInfo = new JButton("Info");

        this.add(btnNew);
        this.add(btwInfo);

        btwInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentInfoDialog studentInfoDialog = new StudentInfoDialog(MainFrame.getInstance(),"Information", false);
                studentInfoDialog.setVisible(true);
            }
        });

    }
}
