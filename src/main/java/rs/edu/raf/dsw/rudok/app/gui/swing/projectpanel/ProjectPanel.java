package rs.edu.raf.dsw.rudok.app.gui.swing.projectpanel;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;

public class ProjectPanel extends JPanel implements IProjectPanel {

    private Project project;
    private JLabel nodeName;
    private JLabel authorName;
    private JButton add;
    private JButton edit;
    private JButton delete;

    public ProjectPanel(Project project) {
        this.project = project;

        nodeName = new JLabel(project.getNodeName());
        authorName = new JLabel(project.getAuthorName());

        add = new JButton("Add Mind map");
        edit = new JButton("Edit Project");
        delete = new JButton("Delete Project");
    }

    @Override
    public void openMindMap(MindMap m) {

    }

    @Override
    public void closeMindMap(MindMap m) {

    }
}
