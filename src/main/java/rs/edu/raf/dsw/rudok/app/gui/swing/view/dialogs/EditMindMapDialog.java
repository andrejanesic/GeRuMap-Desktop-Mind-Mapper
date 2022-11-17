package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.function.Predicate;

public class EditMindMapDialog extends JDialog {
    private final JTextField tfName;
    private final JComboBox<String> cbTemplate;
    private final JCheckBox cbIsTemplate;
    private final MindMap[] templateRefs;
    private Result result;

    public EditMindMapDialog(Project project, MindMap mindMap) {
        super(MainFrame.getInstance(), mindMap != null ? "Edit " + mindMap.getNodeName() : "Create Mind map", true);

        this.setSize(new Dimension(260, 160));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(MainFrame.getInstance());

        JLabel lbName = new JLabel("Name:");
        this.tfName = new JTextField(mindMap != null ? mindMap.getNodeName() : "");

        JLabel lbTemplate = new JLabel("Select template:");
        Iterator<IMapNode> iteratorI = project.getChildren().iterator();
        int i = 0;
        while (iteratorI.hasNext()) {
            MindMap m = (MindMap) iteratorI.next();
            if (m.isTemplate()) i++;
        }
        templateRefs = new MindMap[i + 1];
        String[] templateNames = new String[i + 1];
        templateRefs[0] = null;
        templateNames[0] = "No template";
        Iterator<IMapNode> iteratorJ = project.getChildren().iterator();
        int j = 1;
        while (iteratorJ.hasNext()) {
            MindMap m = (MindMap) iteratorJ.next();
            if (!m.isTemplate()) continue;
            templateRefs[j] = m;
            templateNames[j] = m.getNodeName();
            j++;
        }
        cbTemplate = new JComboBox<>(templateNames);
        cbTemplate.setSelectedIndex(0);

        JLabel lbIsTemplate = new JLabel("Make this a template");
        cbIsTemplate = new JCheckBox("Template", mindMap != null && mindMap.isTemplate());

        JButton buttonCancel = new JButton("Cancel");
        JButton buttonConfirm = new JButton("Confirm");

        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (
                        (!tfName.getText().equals(mindMap != null ? mindMap.getNodeName() : "")) ||
                                (mindMap == null && cbTemplate.getSelectedIndex() > 0) ||
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
        rowSelectTemplate.add(cbTemplate, BorderLayout.CENTER);

        JPanel rowIsTemplate = new JPanel(new BorderLayout(5, 0));
        rowIsTemplate.add(lbIsTemplate, BorderLayout.WEST);
        rowIsTemplate.add(cbIsTemplate, BorderLayout.CENTER);

        central.add(Box.createVerticalStrut(5));
        central.add(rowName);
        central.add(Box.createVerticalStrut(5));
        if (mindMap == null) {
            central.add(rowSelectTemplate);
            central.add(Box.createVerticalStrut(5));
        }
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

    public MindMap getTemplate() {
        return templateRefs[cbTemplate.getSelectedIndex()];
    }

    public boolean getIsTemplate() {
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

