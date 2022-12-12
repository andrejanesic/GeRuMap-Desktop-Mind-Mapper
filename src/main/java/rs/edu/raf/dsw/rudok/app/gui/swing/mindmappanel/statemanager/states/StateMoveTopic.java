package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

import java.util.Arrays;

public class StateMoveTopic extends IState {

    @Override
    public void migrate(Object... params) {
        super.migrate(params);

        try {
            Topic target = (Topic) params[0];
            int x = (int) params[1];
            int y = (int) params[2];
            target.setX(x);
            target.setY(y);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void rollback(Object... params) {

    }
}
