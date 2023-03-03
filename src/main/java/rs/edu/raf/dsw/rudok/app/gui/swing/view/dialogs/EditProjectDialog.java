package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Paths;

public class EditProjectDialog extends JDialog {

    private final JTextField tfName;
    private final JTextField tfAuthor;
    private final JFileChooser fileChooser;
    private boolean confirmed = false;

    public EditProjectDialog(Project project, Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.setSize(new Dimension(500, 450));
        this.setMinimumSize(new Dimension(500, 450));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(owner);

        JLabel lbName = new JLabel("Name:");
        this.tfName = new JTextField();
        JLabel lbAuthor = new JLabel("Author:");
        this.tfAuthor = new JTextField();
        this.fileChooser = new JFileChooser();
        JButton button = new JButton("Confirm");

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setControlButtonsAreShown(false);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfName.getText().length() == 0) {
                    AppCore.getInstance().getMessageGenerator().error("Project name cannot be empty");
                    return;
                }
                if (tfAuthor.getText().length() == 0) {
                    AppCore.getInstance().getMessageGenerator().error("Project author cannot be empty");
                    return;
                }
                if (fileChooser.getCurrentDirectory().length() == 0) {
                    AppCore.getInstance().getMessageGenerator().error("Invalid project directory specified");
                    return;
                }
                if ((!tfName.getText().matches("[a-zA-Z0-9-_ ]+"))) {
                    AppCore.getInstance().getMessageGenerator().error("Project name may only use chars a-z, A-Z, 0-9, space and -_");
                    return;
                }

                confirmed = true;
                dispose();
            }
        });

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));

        JPanel rowName = new JPanel(new BorderLayout(5, 0));
        rowName.add(lbName, BorderLayout.WEST);
        rowName.add(tfName, BorderLayout.CENTER);
        if (project != null) {
            tfName.setText(project.getNodeName());
        }

        JPanel rowAuthor = new JPanel(new BorderLayout(5, 0));
        rowAuthor.add(lbAuthor, BorderLayout.WEST);
        rowAuthor.add(tfAuthor, BorderLayout.CENTER);
        if (project != null) {
            tfAuthor.setText(project.getAuthorName());
        }

        panelTop.add(Box.createVerticalStrut(5));
        panelTop.add(rowName);
        panelTop.add(Box.createVerticalStrut(5));
        panelTop.add(rowAuthor);
        panelTop.add(Box.createVerticalStrut(5));

        JPanel panelTopContainer = new JPanel(new BorderLayout());
        panelTopContainer.add(Box.createHorizontalStrut(5), BorderLayout.EAST);
        panelTopContainer.add(panelTop, BorderLayout.CENTER);
        panelTopContainer.add(Box.createHorizontalStrut(5), BorderLayout.WEST);
        this.add(panelTopContainer, BorderLayout.NORTH);

        JPanel chooserPanel = new JPanel();
        chooserPanel.add(fileChooser);
        if (project != null) {
            fileChooser.setCurrentDirectory(Paths.get(project.getFilepath()).getParent().toFile());
        }

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
        buttonPanel.add(Box.createHorizontalStrut(5), BorderLayout.WEST);
        buttonPanel.add(button);
        buttonPanel.add(Box.createVerticalStrut(5), BorderLayout.SOUTH);
        buttonPanel.add(Box.createHorizontalStrut(5), BorderLayout.EAST);
        this.add(chooserPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getNodeName() {
        return tfName.getText();
    }

    public String getAuthorName() {
        return tfAuthor.getText();
    }

    public String getFilepath() {
        return fileChooser.getCurrentDirectory().getAbsolutePath() + "/" + getNodeName() + ".gerumap.json";
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

