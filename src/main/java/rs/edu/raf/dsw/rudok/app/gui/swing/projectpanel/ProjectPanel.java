package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.MindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller.IProjectActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel.controller.ProjectActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectPanel extends JPanel implements IProjectPanel {

    private final JTabbedPane tabs;
    private Project project;
    private List<MindMap> tabIndexes = new ArrayList<>();

    private JLabel nodeName;
    private JLabel authorName;
    private JButton add;
    private JButton edit;
    private JButton delete;

    private static final IProjectActionManager projectActionManager = new ProjectActionManager();

    public ProjectPanel(Project project) {
        super(new BorderLayout());
        this.project = project;
        this.project.addObserver(
                new ProjectObserver(this, project)
        );

        // Configure tabs
        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        add(tabs, BorderLayout.CENTER);

        // Configure "top strip"
        JPanel metaInfo = new JPanel();
        metaInfo.setLayout(new BoxLayout(metaInfo, BoxLayout.PAGE_AXIS));
        nodeName = new JLabel(project.getNodeName());
        authorName = new JLabel(project.getAuthorName());
        metaInfo.add(nodeName);
        metaInfo.add(authorName);

        // Create actions section
        JPanel actions = new JPanel(new FlowLayout());

        add = new JButton("Add Mind map");
        add.addActionListener(MainFrame.getInstance().getMapTree().getTreeActionManager().getTreeNewAction());

        edit = new JButton("Edit Project");
        edit.addActionListener(projectActionManager.getEditProjectAction());

        delete = new JButton("Delete Project");
        delete.addActionListener(projectActionManager.getDeleteProjectAction());

        actions.add(add);
        actions.add(edit);
        actions.add(delete);

        // Compose it all
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(metaInfo, BorderLayout.WEST);
        topPanel.add(actions, BorderLayout.EAST);
        add(topPanel, BorderLayout.PAGE_START);
    }

    @Override
    public void openMindMap(MindMap m) {
        int i;

        if (!tabIndexes.contains(m)) {
            tabs.add(new MindMapPanel(m), m.getNodeName());
            tabIndexes.add(m);
            m.addObserver(new MindMapObserver(this, m));
            i = tabIndexes.size() - 1;
        } else {
            i = tabIndexes.indexOf(m);
        }

        tabs.setSelectedIndex(i);
    }

    @Override
    public void closeMindMap(MindMap m) {
        for (int i = 0; i < tabIndexes.size(); i++) {
            if (tabIndexes.get(i) == m) {
                tabIndexes.remove(i);
                tabs.remove(i);
                i--;
            }
        }
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public IProjectActionManager getProjectActionManager() {
        return projectActionManager;
    }

    /**
     * Observes the passed {@link Project} for new or deleted mind maps.
     */
    private static class ProjectObserver extends IObserver {

        private final ProjectPanel host;
        private final Project target;

        public ProjectObserver(ProjectPanel host, Project target) {
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
                            host.nodeName.setText(data.getValue().toString());
                        }

                        if (data.getKey().equals("authorName")) {
                            host.authorName.setText(data.getValue().toString());
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

            if (message instanceof IMapNodeComposite.Message) {

                switch (((IMapNodeComposite.Message) message).getStatus()) {

                    case CHILD_ADDED:
                    case CHILD_REMOVED: {
                        IMapNodeComposite.Message.ChildChangeMessageData data =
                                (IMapNodeComposite.Message.ChildChangeMessageData)
                                        ((IMapNodeComposite.Message) message).getData();

                        if (!(((IMapNodeComposite.Message) message).getData().getSender() instanceof Project)) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        Project sender = (Project) ((IMapNodeComposite.Message) message).getData().getSender();

                        if (sender != target) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        if (((IMapNodeComposite.Message) message).getStatus().equals(
                                IMapNodeComposite.Message.Type.CHILD_ADDED)) {
                            this.host.openMindMap((MindMap) data.getChild());
                        } else {
                            this.host.closeMindMap((MindMap) data.getChild());
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
     * Observes the passed {@link MindMap} for edits in name and template.
     */
    private static class MindMapObserver extends IObserver {

        private final ProjectPanel host;
        private final MindMap target;

        public MindMapObserver(ProjectPanel host, MindMap target) {
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

                        if (!(((IMapNode.Message) message).getData().getSender() instanceof MindMap)) {
                            // TODO log error, this is programmatic, should never happen
                            return;
                        }

                        MindMap sender = (MindMap) ((IMapNode.Message) message).getData().getSender();

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

                        if (data.getKey().equals("template")) {
                            // TODO react on template key change
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
