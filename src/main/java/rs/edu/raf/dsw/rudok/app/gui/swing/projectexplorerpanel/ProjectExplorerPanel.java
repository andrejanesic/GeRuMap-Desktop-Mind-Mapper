package rs.edu.raf.dsw.rudok.app.gui.swing.projectexplorerpanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.IProjectPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.ProjectPanel;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectExplorerPanel extends JPanel implements IProjectExplorerPanel {

    private final ProjectExplorer projectExplorer;
    private final JTabbedPane tabs;
    private List<Project> tabIndexes = new ArrayList<>();

    public ProjectExplorerPanel(ProjectExplorer projectExplorer) {
        super(new BorderLayout());
        this.projectExplorer = projectExplorer;
        this.projectExplorer.addObserver(
                new ProjectExplorerObserver(this, projectExplorer)
        );

        // Configure tabs
        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        add(tabs, BorderLayout.CENTER);
    }

    @Override
    public void openProject(Project p) {
        int i;

        if (!tabIndexes.contains(p)) {
            tabs.add(new ProjectPanel(p), p.getNodeName());
            tabIndexes.add(p);
            p.addObserver(new ProjectObserver(this, p));
            i = tabIndexes.size() - 1;
        } else {
            i = tabIndexes.indexOf(p);
        }

        tabs.setSelectedIndex(i);
    }

    @Override
    public void closeProject(Project p) {
        for (int i = 0; i < tabIndexes.size(); i++) {
            if (tabIndexes.get(i) == p) {
                tabIndexes.remove(i);
                tabs.remove(i);
                i--;
            }
        }
    }

    @Override
    public IProjectPanel getProjectPanel() {
        return (IProjectPanel) tabs.getSelectedComponent();
    }

    /**
     * Observes the passed {@link ProjectExplorer} for new or deleted projects.
     */
    private static class ProjectExplorerObserver extends IObserver {

        private final ProjectExplorerPanel host;
        private final ProjectExplorer target;

        public ProjectExplorerObserver(ProjectExplorerPanel host, ProjectExplorer target) {
            this.host = host;
            this.target = target;
        }

        @Override
        public void receive(Object message) {

            if (message instanceof IMapNodeComposite.Message) {

                switch (((IMapNodeComposite.Message) message).getStatus()) {

                    case CHILD_ADDED:
                    case CHILD_REMOVED: {
                        IMapNodeComposite.Message.ChildChangeMessageData data =
                                (IMapNodeComposite.Message.ChildChangeMessageData)
                                        ((IMapNodeComposite.Message) message).getData();

                        if (!(((IMapNodeComposite.Message) message).getData().getSender() instanceof ProjectExplorer)) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        ProjectExplorer sender = (ProjectExplorer) ((IMapNodeComposite.Message) message).getData().getSender();

                        if (sender != target) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        if (((IMapNodeComposite.Message) message).getStatus().equals(
                                IMapNodeComposite.Message.Type.CHILD_ADDED)) {
                            this.host.openProject((Project) data.getChild());
                        } else {
                            this.host.closeProject((Project) data.getChild());
                        }
                        break;
                    }

                    default:
                        // Do nothing
                        break;
                }
            }
        }
    }

    /**
     * Observes the passed {@link Project} for edits in name and filepath.
     */
    private static class ProjectObserver extends IObserver {

        private final ProjectExplorerPanel host;
        private final Project target;

        public ProjectObserver(ProjectExplorerPanel host, Project target) {
            this.host = host;
            this.target = target;
        }

        @Override
        public void receive(Object message) {

            if (message instanceof IMapNode.Message) {

                switch (((IMapNode.Message) message).getStatus()) {

                    case EDITED: {
                        IMapNode.Message.EditedMessageData data = (IMapNode.Message.EditedMessageData)
                                ((IMapNode.Message) message).getData();

                        if (!(((IMapNode.Message) message).getData().getSender() instanceof Project)) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        Project sender = (Project) ((IMapNode.Message) message).getData().getSender();

                        if (sender != target) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        if (data.getKey().equals("nodeName")) {
                            int i = 0;
                            for (i = 0; i < host.tabIndexes.size(); i++) {
                                if (host.tabIndexes.get(i).equals(target)) {
                                    break;
                                }
                            }

                            if (i == host.tabIndexes.size()) {
                                // TODO log error, this is programmatic, should never happen
                                return;
                            }

                            host.tabs.setTitleAt(i, data.getValue().toString());
                        }

                        if (data.getKey().equals("filepath")) {
                            // TODO react on filepath key change (what if two projects are named the same? use different fp?)
                        }

                        break;
                    }

                    case PARENT_ADDED:
                    case PARENT_REMOVED:
                    default:
                        // Do nothing
                        break;
                }
            }
        }
    }
}
