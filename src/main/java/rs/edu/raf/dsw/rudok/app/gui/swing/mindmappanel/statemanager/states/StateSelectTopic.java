package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

public class StateSelectTopic extends IState {

    @Override
    public void migrate(MindMap parent, int x1, int y1) {
        int l = 0;
        for (IMapNode mapNode : parent.getChildren()) {
            if (mapNode == null) continue;
            if (!(mapNode instanceof Topic)) continue;
            if (((Topic) mapNode).isSelected()) l++;
        }

        Topic[] topics = new Topic[l];
        int i = 0;
        for (IMapNode mapNode : parent.getChildren()) {
            if (mapNode == null) continue;
            if (!(mapNode instanceof Topic)) continue;
            if (!((Topic) mapNode).isSelected()) continue;
            topics[i++] = (Topic) mapNode;
            ((Topic) mapNode).setSelected(false);
        }

        super.commit(parent, topics);
    }

    @Override
    public void migrate(MindMap parent, Topic... topics) {
        for (Topic t : topics) {
            if (t == null) continue;
            t.setSelected(!t.isSelected());
        }

        super.commit(parent, topics);
    }

    @Override
    public void rollback(Object... params) {
        try {
            for (Topic t : (Topic[]) params[1]) {
                if (t == null) continue;
                t.setSelected(!t.isSelected());
            }
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error(e.getMessage());
        }
    }
}
