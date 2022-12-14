package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;

public class ActionManager implements IActionManager {

    private final IMindMapPanel mindMapPanel;
    private IAction editTopicAction;

    public ActionManager(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        initActions();
    }

    private void initActions() {
        editTopicAction = new EditTopicAction(mindMapPanel);
    }

    @Override
    public IAction getEditTopicAction() {
        return editTopicAction;
    }
}
