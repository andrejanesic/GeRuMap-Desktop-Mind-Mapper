package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;

public class ActionManager implements IActionManager {

    private final IMindMapPanel mindMapPanel;
    private IAction zoomInAction;
    private IAction zoomOutAction;
    private IAction editTopicAction;

    public ActionManager(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        initActions();
    }

    private void initActions() {
        editTopicAction = new EditTopicAction(mindMapPanel);
        zoomInAction = new ZoomInAction();
        zoomOutAction = new ZoomOutAction();
    }

    @Override
    public IAction getEditTopicAction() {
        return editTopicAction;
    }

    @Override
    public IAction getZoomInAction() {
        return zoomInAction;
    }

    @Override
    public IAction getZoomOutAction() {
        return zoomOutAction;
    }
}
