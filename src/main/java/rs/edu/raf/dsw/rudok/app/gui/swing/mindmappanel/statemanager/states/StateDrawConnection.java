package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.MapNodeFactoryUtils;

public class StateDrawConnection extends IState {

    @Override
    public void migrate(Object... params) {

        try {
            if (params.length < 3) return;

            // If no targets available, draw a markup line
            if (params[1] == null || params[2] == null) {
                if (params.length < 7 ||
                        !(params[3] instanceof Integer) ||
                        !(params[4] instanceof Integer) ||
                        !(params[5] instanceof Integer) ||
                        !(params[6] instanceof Integer)
                ) return;
                MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                        .getActiveMindMapPanel().getDiagramController().getView().paintLine(
                                (int) params[3], (int) params[4], (int) params[5], (int) params[6]
                        );
                MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                        .getActiveMindMapPanel().getDiagramController().getView().repaint();
                MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                        .getActiveMindMapPanel().getDiagramController().getView().revalidate();
                return;
            }

            // Remove markup line
            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().clearLine();

            if (!(params[1] instanceof Topic && params[2] instanceof Topic)) return;

            // Create new connection
            MindMap parent = (MindMap) params[0];
            Topic from = (Topic) params[1];
            Topic to = (Topic) params[2];
            Connection conn = (Connection) MapNodeFactoryUtils.getFactory(parent)
                    .createNode(ElementFactory.Type.Connection,
                            from,
                            to
                    );

            if (conn != null)
                super.migrate(parent, conn);

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
