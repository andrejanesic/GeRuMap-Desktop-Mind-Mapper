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
                            (int) params[2],
                            (int) params[3],
                            0, // TODO customization
                            0  // TODO customization
                    );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void rollback() {

    }
}
