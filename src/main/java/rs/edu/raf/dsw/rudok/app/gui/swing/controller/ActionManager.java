package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.MapTreeCellDelete;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.MapTreeNodeNew;

public class ActionManager {

    private StudentInfoAction studentInfoAction;
    private MapTreeNodeNew mapTreeNodeNew;
    private MapTreeCellDelete mapTreeCellDelete;
    private ProjectAuthorAction projectAuthorAction;

    public ActionManager() {
        initActions();
    }

    public void initActions(){
        studentInfoAction = new StudentInfoAction();
        mapTreeNodeNew = new MapTreeNodeNew();
        mapTreeCellDelete = new MapTreeCellDelete();
        projectAuthorAction = new ProjectAuthorAction();
    }

    public StudentInfoAction getStudentInfoAction() {
        return studentInfoAction;
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
