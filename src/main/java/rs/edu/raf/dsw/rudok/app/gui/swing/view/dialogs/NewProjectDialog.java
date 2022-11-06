package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewProjectDialog extends JDialog {

    private JLabel lbName;
    private JTextField tfName;
    private JLabel lbAuthor;
    private JTextField tfAuthor;
    private JFileChooser fileChooser;
    private JButton button;

    public NewProjectDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.setSize(new Dimension(550,550));
        this.setLayout(new BorderLayout());

        this.setLocationRelativeTo(owner);

        this.lbName = new JLabel("Name:");
        this.tfName = new JTextField();
        this.lbAuthor = new JLabel("Author:");
        this.tfAuthor = new JTextField();
        this.fileChooser = new JFileChooser();
        this.button = new JButton("Confirm");

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMaximumSize(new Dimension(300,100));
        tfName.setMaximumSize(new Dimension(225,20));
        tfAuthor.setMaximumSize(new Dimension(225,20));

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
                IMapNode parent = selected.getMapNode();
                Project child = new Project(tfName.getText(), tfAuthor.getText(), fileChooser.getCurrentDirectory().toString());
                ((ProjectExplorer) parent).addChild(child);
                child.addParent((ProjectExplorer)parent);
                dispose();
            }
        });

        JPanel panel = new JPanel();
        BoxLayout box = new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(box);

        panel.add(lbName);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tfName);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lbAuthor);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tfAuthor);
        panel.add(Box.createVerticalStrut(10));
        JPanel chooserPanel = new JPanel();
        chooserPanel.add(fileChooser);

        panel.add(chooserPanel);
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        this.add(panel,BorderLayout.CENTER);
    }
}

