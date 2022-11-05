package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.MapTreeNodeNew;

public class ActionManager {

    private StudentInfoAction studentInfoAction;
    private MapTreeNodeNew mapTreeNodeNew;

    public ActionManager() {
        initActions();
    }

    public void initActions(){
        studentInfoAction = new StudentInfoAction();
        mapTreeNodeNew = new MapTreeNodeNew();
    }

    public StudentInfoAction getStudentInfoAction() {
        return studentInfoAction;
    }

    public MapTreeNodeNew getMapTreeNodeNew() {
        return mapTreeNodeNew;
    }
}
