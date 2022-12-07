package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.*;

public class StateManager implements IStateManager {

    private IState addTopic;
    private IState selectTopic;
    private IState moveTopic;
    private IState zoom;
    private IState deleteElement;
    private IState drawConnection;
    private IState current;

    public StateManager() {
        init();
    }

    @Override
    public void init() {
        addTopic = new StateAddTopic();
        selectTopic = new StateSelectTopic();
        moveTopic = new StateMoveTopic();
        zoom = new StateZoom();
        deleteElement = new StateDeleteElement();
        drawConnection = new StateDrawConnection();
        current = selectTopic;
    }

    @Override
    public void setAddTopic() {
        current = addTopic;
    }

    @Override
    public void setSelectTopic() {
        current = selectTopic;
    }

    @Override
    public void setMoveTopic() {
        current = moveTopic;
    }

    @Override
    public void setZoom() {
        current = zoom;
    }

    @Override
    public void setDrawConnection() {
        current = drawConnection;
    }

    @Override
    public void setDeleteElement() {
        current = deleteElement;
    }

    @Override
    public IState getState() {
        return current;
    }

}
