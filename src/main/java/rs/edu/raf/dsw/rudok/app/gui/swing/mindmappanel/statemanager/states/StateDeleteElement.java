package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.DiagramFramework;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;
import java.util.Arrays;

public class StateDeleteElement implements IState {

    @Override
    public void migrate(Object... params) {
        // TODO check if correct params passed by caller
        // TODO conduct actual action on element/topic/connection

        int x = (int)params[2];
        int y = (int)params[3];

        for(Shape s: DiagramFramework.getShapes()){
            if(s.contains(x,y)){
                DiagramFramework.getShapes().remove(s);
                //for (Element e: )
                //TODO izbaciti child element kad se shape obrise
            }
        }


        System.out.println(this.getClass().getSimpleName());
        System.out.println(Arrays.toString(params));
    }

    @Override
    public void rollback() {

    }
}
