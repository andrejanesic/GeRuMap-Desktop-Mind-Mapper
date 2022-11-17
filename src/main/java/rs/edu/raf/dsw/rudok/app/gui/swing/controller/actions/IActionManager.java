package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.TreeDeleteAction;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions.TreeNewAction;

public interface IActionManager {
    StudentInfoAction getStudentInfoAction();

    TreeNewAction getNewAction();

    TreeDeleteAction getDeleteAction();

    ProjectAuthorAction getProjectAuthorAction();

    NewProjectAction getNewProjectAction();

    OpenProjectAction getOpenProjectAction();

    CloseProjectAction getCloseProjectAction();

    QuitAction getQuitAction();

    UndoAction getUndoAction();

    RedoAction getRedoAction();

    OpenPreferencesAction getOpenPreferencesAction();

    SaveProjectAction getSaveProjectAction();

    SaveProjectAction getSaveAllProjectsAction();
}
