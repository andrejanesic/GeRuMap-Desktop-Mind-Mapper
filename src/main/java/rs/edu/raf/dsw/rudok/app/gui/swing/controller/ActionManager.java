package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

public class ActionManager {

    private StudentInfoAction studentInfoAction;
    private NewAction newAction;
    private DeleteAction mapTreeCellDelete;
    private ProjectAuthorAction projectAuthorAction;

    public ActionManager() {
        initActions();
    }

    public void initActions() {
        studentInfoAction = new StudentInfoAction();
        newAction = new NewAction();
        mapTreeCellDelete = new DeleteAction();
        projectAuthorAction = new ProjectAuthorAction();
    }

    public StudentInfoAction getStudentInfoAction() {
        return studentInfoAction;
    }

    public NewAction getNewAction() {
        return newAction;
    }

    public DeleteAction getDeleteAction() {
        return mapTreeCellDelete;
    }

    public ProjectAuthorAction getProjectAuthorAction() {
        return projectAuthorAction;
    }
}
