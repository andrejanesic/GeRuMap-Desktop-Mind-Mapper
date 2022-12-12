package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

public class StateDeleteElement implements IState {

    @Override
    public void migrate(Object... params) {
        try {
            MindMap parent = (MindMap) params[0];
            Element target = (Element) params[1];
            if (parent.getChildren().contains(target)) {
                parent.removeChild(target);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void rollback() {

    }
}
