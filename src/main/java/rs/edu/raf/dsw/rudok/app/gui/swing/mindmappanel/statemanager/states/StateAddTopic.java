package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

public class StateAddTopic extends IState {

    @Override
    public void migrate(MindMap parent, int x1, int y1) {
        super.migrate(parent, x1, y1);

        Element e = (Element) MapNodeFactoryUtils.getFactory(parent)
                .createNode(ElementFactory.Type.Topic,
                        x1,
                        y1,
                        ElementFactory.TOPIC_DEFAULT_WIDTH, // TODO customization
                        ElementFactory.TOPIC_DEFAULT_HEIGHT  // TODO customization
                );

        super.commit(parent, e);
    }

    @Override
    public void rollback(Object... params) {
        MindMap parent = (MindMap) params[0];
        parent.removeChild((IMapNode) params[1]);
    }
}
