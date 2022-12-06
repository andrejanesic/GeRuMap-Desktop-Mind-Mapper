package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

public class StateActionManager implements IStateActionManager {

    private IStateAction startAddTopicStateAction;
    private IStateAction startDeleteElementStateAction;
    private IStateAction startDrawConnectionStateAction;
    private IStateAction startMoveTopicStateAction;
    private IStateAction startSelectTopicStateAction;
    private IStateAction startZoomStateAction;

    public StateActionManager() {
        initActions();
    }

    public void initActions() {
        startAddTopicStateAction = new StartAddTopicStateAction();
        startDeleteElementStateAction = new StartDeleteElementStateAction();
        startDrawConnectionStateAction = new StartDrawConnectionStateAction();
        startMoveTopicStateAction = new StartMoveTopicStateAction();
        startSelectTopicStateAction = new StartSelectTopicStateAction();
        startZoomStateAction = new StartZoomStateAction();
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

    @Override
    public IStateAction getStartZoomStateAction() {
        return startZoomStateAction;
    }
}
