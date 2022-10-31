package rs.edu.raf.dsw.rudok.app.core.view.controller;

import rs.edu.raf.dsw.rudok.app.core.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.core.view.dialogs.StudentInfoDialog;

import java.awt.event.ActionEvent;

public class StudentInfoAction extends MyAbstractAction{
    @Override
    public void actionPerformed(ActionEvent e) {
        StudentInfoDialog studentInfoDialog = new StudentInfoDialog(MainFrame.getInstance(),"Information", false);
        studentInfoDialog.setVisible(true);
    }
}
