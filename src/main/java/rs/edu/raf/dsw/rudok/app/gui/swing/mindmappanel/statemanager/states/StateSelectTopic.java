package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import jdk.jfr.internal.Utils;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.DiagramFramework;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

public class StateSelectTopic implements IState {

    @Override
    public void migrate(Object... params) {
        // TODO check if correct params passed by caller
        // TODO conduct actual action on element/topic/connection

        int x = (int)params[2];
        int y = (int)params[3];
        boolean flag = false;
        for(Shape s: DiagramFramework.getShapes()) {
            if (s.contains(x, y)) {
                DiagramFramework.getSelectedShapes().add(s);
                flag = true;
            }
        }
        if(!flag){
            DiagramFramework.getSelectedShapes().clear();
        }
        //wont work need a better solution
        System.out.println(this.getClass().getSimpleName());
        System.out.println(Arrays.toString(params));
    }

    @Override
    public void rollback() {

    }
}
