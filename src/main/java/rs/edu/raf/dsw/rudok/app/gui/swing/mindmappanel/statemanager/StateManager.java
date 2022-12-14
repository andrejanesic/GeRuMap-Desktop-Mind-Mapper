package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.*;

import java.util.Stack;

public class StateManager implements IStateManager {

    /**
     * Stores history of all state changes.
     */
    private Stack<IState> history = new Stack<>();
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
        if (getState().equals(selectTopic)) {
            selectTopic.rollback();
            rollback();
        }
        current = addTopic;
        history.push(current);
    }

    @Override
    public void setSelectTopic() {
        current = selectTopic;
        history.push(current);
    }

    @Override
    public void setMoveTopic() {
        current = moveTopic;
        history.push(current);
    }

    @Override
    public void setZoom() {
        if (getState().equals(selectTopic)) {
            selectTopic.rollback();
            rollback();
        }
        current = zoom;
        history.push(current);
    }

    @Override
    public void setDrawConnection() {
        if (getState().equals(selectTopic)) {
            selectTopic.rollback();
            rollback();
        }
        current = drawConnection;
        history.push(current);
    }

    @Override
    public void setDeleteElement() {
        if (getState().equals(selectTopic)) {
            selectTopic.rollback();
            rollback();
        }
        current = deleteElement;
        history.push(current);
    }

    @Override
    public IState getState() {
        return current;
    }

    @Override
    public void rollback() {
        if (!history.empty()) history.pop();
    }

}
