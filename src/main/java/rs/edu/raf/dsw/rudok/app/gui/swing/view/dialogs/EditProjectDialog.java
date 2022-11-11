package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Paths;

public class EditProjectDialog extends JDialog {

    private Project project;
    private JLabel lbName;
    private JTextField tfName;
    private JLabel lbAuthor;
    private JTextField tfAuthor;
    private JFileChooser fileChooser;
    private JButton button;

    public EditProjectDialog(Project project, Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.project = project;
        this.setSize(new Dimension(550, 550));
        this.setLayout(new BorderLayout());

        this.setLocationRelativeTo(owner);

        this.lbName = new JLabel("Name:");
        this.tfName = new JTextField();
        this.lbAuthor = new JLabel("Author:");
        this.tfAuthor = new JTextField();
        this.fileChooser = new JFileChooser();
        this.button = new JButton("Confirm");

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMaximumSize(new Dimension(300, 100));
        tfName.setMaximumSize(new Dimension(225, 20));
        tfAuthor.setMaximumSize(new Dimension(225, 20));

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel();
        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(box);

        panel.add(lbName);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tfName);
        panel.add(Box.createVerticalStrut(10));
        if (project != null) {
            tfName.setText(project.getNodeName());
        }
        panel.add(lbAuthor);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tfAuthor);
        if (project != null) {
            tfAuthor.setText(project.getAuthorName());
        }
        panel.add(Box.createVerticalStrut(10));
        JPanel chooserPanel = new JPanel();
        chooserPanel.add(fileChooser);
        if (project != null) {
            fileChooser.setCurrentDirectory(Paths.get(project.getFilepath()).getParent().toFile());
        }

        panel.add(chooserPanel);
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        this.add(panel, BorderLayout.CENTER);
    }

    public String getNodeName() {
        return tfName.getText();
    }

    public String getAuthorName() {
        return tfAuthor.getText();
    }

    public String getFilepath() {
        return fileChooser.getCurrentDirectory().getAbsolutePath() + "/" + getNodeName() + ".grm";
    }
}

