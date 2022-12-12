package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

public class StateAddTopic extends IState {

    @Override
    public void migrate(Object... params) {
        super.migrate(params);

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
            AppCore.getInstance().getMessageGenerator().error(e.getMessage());
        }
    }

    @Override
    public void rollback(Object... params) {

    }
}
