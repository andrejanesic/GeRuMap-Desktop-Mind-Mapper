package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

// TODO Fix this
public class EditMindMapDialog extends JDialog {
    private final JButton btSelectTemplate;
    private final JTextField tfName;
    private final JCheckBox cbIsTemplate;

    /**
     * Result of the dialog.
     */
    private Result result;

    /**
     * {@link MindMap} template path.
     */
    private String template = null;

    public EditMindMapDialog(MindMap mindMap) {
        super(MainFrame.getInstance(), mindMap != null ? "Edit " + mindMap.getNodeName() : "Create Mind map", true);

        this.setSize(new Dimension(260, 160));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(MainFrame.getInstance());

        JLabel lbName = new JLabel("Name:");
        this.tfName = new JTextField(mindMap != null ? mindMap.getNodeName() : "");

        // if mind map not empty, cannot apply template!
        String lbTemplateText;
        if (mindMap == null || mindMap.getChildren().isEmpty()) {
            lbTemplateText = "Use template:";
        } else {
            lbTemplateText = "Can't apply template, mind map not empty";
        }
        JLabel lbTemplate = new JLabel(lbTemplateText);
        btSelectTemplate = new JButton("Select");
        btSelectTemplate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TemplateDialog td = new TemplateDialog();
                td.setVisible(true);
                template = td.getPath();
            }
        });

        JLabel lbIsTemplate = new JLabel("Make this a template");
        cbIsTemplate = new JCheckBox("Template", mindMap != null && mindMap.isTemplate());

        JButton buttonCancel = new JButton("Cancel");
        JButton buttonConfirm = new JButton("Confirm");

        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (
                        (!tfName.getText().equals(mindMap != null ? mindMap.getNodeName() : "")) ||
                                (cbIsTemplate.isSelected() != (mindMap != null && mindMap.isTemplate()))
                ) {
                    int confirmResult = JOptionPane.showConfirmDialog(
                            null,
                            "Cancel new Mind map? All changes will be lost",
                            "Cancel mind map",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    if (confirmResult != 0) {
                        return;
                    }
                }

                result = Result.CANCELED;
                dispose();
            }
        });

        buttonConfirm.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfName.getText().length() == 0) {
                    AppCore.getInstance().getMessageGenerator().error("Mind map name cannot be empty");
                    return;
                }
                if ((!tfName.getText().matches("[a-zA-Z0-9-_ ]+"))) {
                    AppCore.getInstance().getMessageGenerator().error("Mind map name may only use chars a-z, A-Z, 0-9, space and -_");
                    return;
                }

                result = Result.CONFIRMED;
                dispose();
            }
        });

        JPanel central = new JPanel();
        central.setLayout(new BoxLayout(central, BoxLayout.Y_AXIS));

        JPanel rowName = new JPanel(new BorderLayout(5, 0));
        rowName.add(lbName, BorderLayout.WEST);
        rowName.add(tfName, BorderLayout.CENTER);

        JPanel rowSelectTemplate = new JPanel(new BorderLayout(5, 0));
        rowSelectTemplate.add(lbTemplate, BorderLayout.WEST);
        if (mindMap == null || mindMap.getChildren().isEmpty()) {
            rowSelectTemplate.add(btSelectTemplate, BorderLayout.CENTER);
        }

        JPanel rowIsTemplate = new JPanel(new BorderLayout(5, 0));
        rowIsTemplate.add(lbIsTemplate, BorderLayout.WEST);
        rowIsTemplate.add(cbIsTemplate, BorderLayout.CENTER);

        central.add(Box.createVerticalStrut(5));
        central.add(rowName);
        central.add(Box.createVerticalStrut(5));
        central.add(rowSelectTemplate);
        central.add(Box.createVerticalStrut(5));
        central.add(rowIsTemplate);
        central.add(Box.createVerticalStrut(5));

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(buttonCancel, BorderLayout.WEST);
        buttons.add(buttonConfirm, BorderLayout.EAST);
        buttons.add(Box.createVerticalStrut(5), BorderLayout.NORTH);

        JPanel container = new JPanel(new BorderLayout());
        container.add(central, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(container);
    }

    public String getNodeName() {
        return tfName.getText();
    }

    public String getTemplate() {
        return template;
    }

    public boolean isTemplate() {
        return cbIsTemplate.isSelected();
    }

    public Result getResult() {
        return result;
    }

    /**
     * Dialog result states upon disposal.
     */
    public enum Result {
        CONFIRMED,
        CANCELED,
    }
}

