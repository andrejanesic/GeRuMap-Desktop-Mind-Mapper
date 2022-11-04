package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.StudentInfoDialog;

import java.awt.event.ActionEvent;

public class StudentInfoAction extends MyAbstractAction{
    @Override
    public void actionPerformed(ActionEvent e) {
        StudentInfoDialog studentInfoDialog = new StudentInfoDialog(MainFrame.getInstance(),"Information", false);
        studentInfoDialog.setVisible(true);
    }
}
