package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;

public class StateActionManager implements IStateActionManager {

    private final IMindMapPanel mindMapPanel;
    private IStateAction startAddTopicStateAction;
    private IStateAction startDeleteElementStateAction;
    private IStateAction startDrawConnectionStateAction;
    private IStateAction startMoveTopicStateAction;
    private IStateAction startSelectTopicStateAction;

    public StateActionManager(IMindMapPanel mindMapPanel) {
        this.mindMapPanel = mindMapPanel;
        initActions();
    }

    public void initActions() {
        startAddTopicStateAction = new StartAddTopicStateAction(mindMapPanel.getStateManager());
        startDeleteElementStateAction = new StartDeleteElementStateAction(mindMapPanel.getStateManager());
        startDrawConnectionStateAction = new StartDrawConnectionStateAction(mindMapPanel.getStateManager());
        startMoveTopicStateAction = new StartMoveTopicStateAction(mindMapPanel.getStateManager());
        startSelectTopicStateAction = new StartSelectTopicStateAction(mindMapPanel.getStateManager());

    }

    @Override
    public IStateAction getStartAddTopicStateAction() {
        return startAddTopicStateAction;
    }

    @Override
    public IStateAction getStartDeleteElementStateAction() {
        return startDeleteElementStateAction;
    }

    @Override
    public IStateAction getStartDrawConnectionStateAction() {
        return startDrawConnectionStateAction;
    }

    @Override
    public IStateAction getStartMoveTopicStateAction() {
        return startMoveTopicStateAction;
    }

    @Override
    public IStateAction getStartSelectTopicStateAction() {
        return startSelectTopicStateAction;
    }


}
