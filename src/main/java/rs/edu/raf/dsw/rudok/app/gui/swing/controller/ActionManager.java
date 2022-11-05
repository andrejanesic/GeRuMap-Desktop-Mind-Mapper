package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

public class ActionManager {

    private StudentInfoAction studentInfoAction;
    private NewAction newAction;

    public ActionManager() {
        initActions();
    }

    public void initActions() {
        studentInfoAction = new StudentInfoAction();
        newAction = new NewAction();
    }

    public StudentInfoAction getStudentInfoAction() {
        return studentInfoAction;
    }

    public NewAction getNewAction() {
        return newAction;
    }
}
