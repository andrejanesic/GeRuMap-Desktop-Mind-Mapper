package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.util.Arrays;

public class StateAddTopic implements IState {

    @Override
    public void migrate(Object... params) {
        try {
            MindMap parent = (MindMap) params[0];
            MapNodeFactoryUtils.getFactory(parent)
                    .createNode(ElementFactory.Type.Topic,
                            params[2],
                            params[3],
                            ElementFactory.TOPIC_DEFAULT_WIDTH, // TODO customization
                            ElementFactory.TOPIC_DEFAULT_HEIGHT  // TODO customization
                    );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void rollback() {

    }
}
