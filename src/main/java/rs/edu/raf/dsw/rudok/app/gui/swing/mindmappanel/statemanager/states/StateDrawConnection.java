package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.standard.AddElementCommand;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

public class StateDrawConnection extends IState {

    @Override
    public void migrate(MindMap parent, Topic t, int x1, int y1, boolean complete) {
        this.migrate(parent, t.getX(), t.getY(), x1, y1, false);
    }

    @Override
    public void migrate(MindMap parent, int x1, int y1, int x2, int y2, boolean complete) {
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().paintLine(
                        x1, y1, x2, y2
                );
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().repaint();
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().revalidate();
    }

    @Override
    public void migrate(MindMap parent, Topic t1, Topic t2) {
        // Remove markup line
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().clearHelpers();

        // Create new connection
        CommandManagerFactory.getCommandManager(parent)
                .addCommand(new AddElementCommand(
                        parent,
                        t1,
                        t2
                ));
    }
}
