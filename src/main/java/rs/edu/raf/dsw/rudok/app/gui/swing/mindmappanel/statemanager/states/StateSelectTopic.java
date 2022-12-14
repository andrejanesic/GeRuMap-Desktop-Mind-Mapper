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
        int l = 0;
        for (IMapNode mapNode : parent.getChildren()) {
            if (mapNode == null) continue;
            if (!(mapNode instanceof Topic)) continue;
            l++;
        }

        int i = 0;
        Topic[] flipped = new Topic[l];
        for (IMapNode e : parent.getChildren()) {
            if (!(e instanceof Topic)) continue;
            Topic t = (Topic) e;
            t.setSelected(false);
            for (Topic t1 : topics) {
                if (t == t1) {
                    t.setSelected(true);
                    break;
                }
            }
            flipped[i] = t;
        }

        super.commit(parent, flipped);
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
