package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.TreeDeleteAction;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.TreeNewAction;

public class ActionManager implements IActionManager {

    private StudentInfoAction studentInfoAction;
    private TreeNewAction treeNewAction;
    private TreeDeleteAction mapTreeCellDelete;
    private ProjectAuthorAction projectAuthorAction;
    private NewProjectAction newProjectAction;
    private OpenProjectAction openProjectAction;
    private CloseProjectAction closeProjectAction;
    private QuitAction quitAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private OpenPreferencesAction openPreferencesAction;
    private SaveProjectAction saveProjectAction;
    private SaveProjectAsAction saveProjectAsAction;
    private SaveProjectAction saveAllProjectsAction;
    private ExportAction exportAction;

    public ActionManager() {
        initActions();
    }

    public void initActions() {
        studentInfoAction = new StudentInfoAction();
        treeNewAction = new TreeNewAction();
        mapTreeCellDelete = new TreeDeleteAction();
        projectAuthorAction = new ProjectAuthorAction();
        newProjectAction = new NewProjectAction();
        openProjectAction = new OpenProjectAction();
        closeProjectAction = new CloseProjectAction();
        quitAction = new QuitAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        openPreferencesAction = new OpenPreferencesAction();
        saveProjectAction = new SaveProjectAction(false);
        saveAllProjectsAction = new SaveProjectAction(true);
        exportAction = new ExportAction();
        saveProjectAsAction = new SaveProjectAsAction();
    }

    @Override
    public StudentInfoAction getStudentInfoAction() {
        return studentInfoAction;
    }

    @Override
    public TreeNewAction getNewAction() {
        return treeNewAction;
    }

    @Override
    public TreeDeleteAction getDeleteAction() {
        return mapTreeCellDelete;
    }

    @Override
    public ProjectAuthorAction getProjectAuthorAction() {
        return projectAuthorAction;
    }

    @Override
    public NewProjectAction getNewProjectAction() {
        return newProjectAction;
    }

    @Override
    public OpenProjectAction getOpenProjectAction() {
        return openProjectAction;
    }

    @Override
    public CloseProjectAction getCloseProjectAction() {
        return closeProjectAction;
    }

    @Override
    public QuitAction getQuitAction() {
        return quitAction;
    }

    @Override
    public UndoAction getUndoAction() {
        return undoAction;
    }

    @Override
    public RedoAction getRedoAction() {
        return redoAction;
    }

    @Override
    public OpenPreferencesAction getOpenPreferencesAction() {
        return openPreferencesAction;
    }

    @Override
    public SaveProjectAction getSaveProjectAction() {
        return saveProjectAction;
    }

    @Override
    public SaveProjectAsAction getSaveProjectAsAction() {
        return saveProjectAsAction;
    }

    @Override
    public SaveProjectAction getSaveAllProjectsAction() {
        return saveAllProjectsAction;
    }

    @Override
    public ExportAction getExportAction() {
        return exportAction;
    }
}
