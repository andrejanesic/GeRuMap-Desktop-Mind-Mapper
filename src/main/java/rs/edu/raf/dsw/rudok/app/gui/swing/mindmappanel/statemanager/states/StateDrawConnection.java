package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.util.Arrays;

public class StateDrawConnection implements IState {

    @Override
    public void migrate(Object... params) {
        try {
            MindMap parent = (MindMap) params[0];
            // params[1] = (Topic) from, params[2] = (Topic) to
            MapNodeFactoryUtils.getFactory(parent)
                    .createNode(ElementFactory.Type.Connection,
                            params[1],
                            params[2]
                    );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void rollback() {

    }
}
