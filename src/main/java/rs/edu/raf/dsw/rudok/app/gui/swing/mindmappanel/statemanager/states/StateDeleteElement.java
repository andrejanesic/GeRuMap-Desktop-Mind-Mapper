package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

public class StateDeleteElement extends IState {

    @Override
    public void migrate(MindMap parent, Topic... topics) {
        super.migrate(parent, topics);
        for (Topic t : topics) {
            if (parent.getChildren().contains(t)) {
                parent.removeChild(t);
            }
        }
    }

    @Override
    public void rollback(Object... params) {
        MindMap parent = (MindMap) params[0];
        for (Topic t : (Topic[]) params[1]) {
            if (parent.getChildren().contains(t)) continue;
            Topic tNew = (Topic) MapNodeFactoryUtils.getFactory(parent).createNode(
                    ElementFactory.Type.Topic,
                    t.getX(),
                    t.getY()
            );
            tNew.setNodeName(t.getNodeName());
            parent.addChild(tNew);
        }
    }
}
