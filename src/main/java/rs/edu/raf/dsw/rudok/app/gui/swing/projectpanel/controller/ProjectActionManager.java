package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

public class ProjectActionManager implements IProjectActionManager {

    private EditProjectAction editProjectAction;
    private DeleteProjectAction deleteProjectAction;

    public ProjectActionManager() {
        initActions();
    }

    public void initActions() {
        editProjectAction = new EditProjectAction();
        deleteProjectAction = new DeleteProjectAction();
    }

    @Override
    public EditProjectAction getEditProjectAction() {
        return editProjectAction;
    }

    @Override
    public DeleteProjectAction getDeleteProjectAction() {
        return deleteProjectAction;
    }
}
