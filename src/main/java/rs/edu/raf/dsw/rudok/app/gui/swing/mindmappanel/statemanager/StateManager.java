package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.StateAddTopic;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.StateDrawConnection;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.StateDeleteElement;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.StateSelectTopic;

public class StateManager implements IStateManager {

    private IState addTopic;
    private IState selectTopic;
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
