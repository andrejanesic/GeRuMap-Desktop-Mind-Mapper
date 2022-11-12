package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller;

public class ProjectActionManager implements IProjectActionManager {

    private AddProjectAction addProjectAction;
    private EditProjectAction editProjectAction;
    private DeleteProjectAction deleteProjectAction;

    public ProjectActionManager() {
        initActions();
    }

    public void initActions() {
        addProjectAction = new AddProjectAction();
        editProjectAction = new EditProjectAction();
        deleteProjectAction = new DeleteProjectAction();
    }

    @Override
    public AddProjectAction getAddProjectAction() {
        return addProjectAction;
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
