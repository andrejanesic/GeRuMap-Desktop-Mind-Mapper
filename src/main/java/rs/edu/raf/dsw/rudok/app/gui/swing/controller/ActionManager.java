package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.MapTreeCellDelete;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.MapTreeNodeNew;

public class ActionManager {

    private StudentInfoAction studentInfoAction;
    private NewAction newAction;
    private MapTreeNodeNew mapTreeNodeNew;
    private MapTreeCellDelete mapTreeCellDelete;
    private ProjectAuthorAction projectAuthorAction;

    public ActionManager() {
        initActions();
    }

    public void initActions() {
        studentInfoAction = new StudentInfoAction();
        newAction = new NewAction();
        mapTreeNodeNew = new MapTreeNodeNew();
        mapTreeCellDelete = new MapTreeCellDelete();
        projectAuthorAction = new ProjectAuthorAction();
    }

    public StudentInfoAction getStudentInfoAction() {
        return studentInfoAction;
    }

    public NewAction getNewAction() {
        return newAction;
    }

    public MapTreeNodeNew getMapTreeNodeNew() {
        return mapTreeNodeNew;
    }

    public MapTreeCellDelete getMapTreeCellDelete() {
        return mapTreeCellDelete;
    }

    public ProjectAuthorAction getProjectAuthorAction() {
        return projectAuthorAction;
    }
}
