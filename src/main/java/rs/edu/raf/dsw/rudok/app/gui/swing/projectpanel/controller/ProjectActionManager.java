package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

public class ProjectActionManager {

    private EditProjectAction editProjectAction;
    private DeleteProjectAction deleteProjectAction;

    public ProjectActionManager() {
        initActions();
    }

    public void initActions() {
        editProjectAction = new EditProjectAction();
        deleteProjectAction = new DeleteProjectAction();
    }

    public EditProjectAction getEditProjectAction() {
        return editProjectAction;
    }

    public DeleteProjectAction getDeleteProjectAction() {
        return deleteProjectAction;
    }
}
