package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states.*;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.util.Stack;

public class StateManager implements IStateManager {

    /**
     * Stores history of all state changes.
     */
    private Stack<IState> history = new Stack<>();
    private IState addTopic;
    private IState selectTopic;
    private IState moveTopic;
    private IState deleteElement;
    private IState drawConnection;
    private IState current;
    private MindMap parent;

    public StateManager(MindMap parent) {
        this.parent = parent;
        init();
    }

    @Override
    public void init() {
        addTopic = new StateAddTopic();
        selectTopic = new StateSelectTopic();
        moveTopic = new StateMoveTopic();
        deleteElement = new StateDeleteElement();
        drawConnection = new StateDrawConnection();
        current = selectTopic;
    }

    @Override
    public void setAddTopic() {
        clearSelected();
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
    public void setDrawConnection() {
        clearSelected();
        current = drawConnection;
        history.push(current);
    }

    @Override
    public void setDeleteElement() {
        clearSelected();
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

    private void clearSelected() {
        for (IMapNode e : parent.getChildren()) {
            if (!(e instanceof Topic)) continue;
            ((Topic) e).setSelected(false);
        }
        selectTopic.clear();
    }

}
