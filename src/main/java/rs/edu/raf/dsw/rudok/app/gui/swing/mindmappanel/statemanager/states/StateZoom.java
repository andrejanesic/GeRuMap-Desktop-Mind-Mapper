package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;

import java.util.Arrays;

public class StateZoom extends IState {

    @Override
    public void migrate(Object... params) {
        super.migrate(params);

        // TODO check if correct params passed by caller
        // TODO conduct actual action on element/topic/connection
        System.out.println(this.getClass().getSimpleName());
        System.out.println(Arrays.toString(params));
    }

    @Override
    public void rollback(Object... params) {

    }
}
