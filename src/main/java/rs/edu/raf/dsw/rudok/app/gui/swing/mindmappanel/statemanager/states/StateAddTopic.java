package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

public class StateAddTopic extends IState {

    @Override
    public void migrate(Object... params) {

        try {
            if (!(params.length >= 4 &&
                    params[0] instanceof MindMap &&
                    params[2] instanceof Integer &&
                    params[3] instanceof Integer)
            ) {
                return;
            }

            MindMap parent = (MindMap) params[0];
            Element e = (Element) MapNodeFactoryUtils.getFactory(parent)
                    .createNode(ElementFactory.Type.Topic,
                            params[2],
                            params[3],
                            ElementFactory.TOPIC_DEFAULT_WIDTH, // TODO customization
                            ElementFactory.TOPIC_DEFAULT_HEIGHT  // TODO customization
                    );

            super.migrate(parent, e);

        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void rollback(Object... params) {
        MindMap parent = (MindMap) params[0];
        parent.removeChild((IMapNode) params[1]);
    }
}
