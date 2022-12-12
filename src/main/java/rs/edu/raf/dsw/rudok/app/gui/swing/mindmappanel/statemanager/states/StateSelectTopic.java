package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.repository.Element;

public class StateSelectTopic extends IState {

    @Override
    public void migrate(Object... params) {
        // Undo last selection first
        rollback();

        super.migrate(params);

        try {
            for (Object p : params) {
                if (p instanceof Element) {
                    ElementPainterFactory.getPainter((Element) p).setSelected(true);
                }
            }
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error(e.getMessage());
        }
    }

    @Override
    public void rollback(Object... params) {
        try {
            for (Object p : params) {
                if (p instanceof Element) {
                    ElementPainterFactory.getPainter((Element) p).setSelected(false);
                }
            }
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error(e.getMessage());
        }
    }
}
