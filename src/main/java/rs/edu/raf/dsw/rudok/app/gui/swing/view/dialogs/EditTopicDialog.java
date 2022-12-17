package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Dialog for editing the passed {@link Topic}(s).
 */
public class EditTopicDialog extends JDialog {

    /**
     * Initial name value in case multiple nodes are passed for editing.
     */
    public static String MULTI_VALUES_NAME = "<< Multiple values >>";

    private Set<Topic> topics = new HashSet<>();
    private JTextField name;
    private JColorChooser color;
    private JSpinner border;
    private Result result;

    public EditTopicDialog(Set<Topic> topics) {
        super(MainFrame.getInstance(), "Edit topic", true);

        this.topics.addAll(topics);

        this.setSize(new Dimension(350, 500));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(MainFrame.getInstance());

        JLabel lbName = new JLabel("Text:");
        // JLabel lbColor = new JLabel("Color:");
        JLabel lbBorder = new JLabel("Border:");

        Optional<Topic> optionalTopic = topics.stream().findAny();
        if (!optionalTopic.isPresent()) {
            AppCore.getInstance().getMessageGenerator().error(
                    "No topics selected."
            );
            return;
        }

        Topic topic = optionalTopic.get();
        if (this.topics.size() == 1) {
            name = new JTextField(topic.getNodeName());
        } else {
            name = new JTextField(MULTI_VALUES_NAME);
        }

        color = new JColorChooser(Color.decode(topic.getColor()));
        border = new JSpinner(new SpinnerNumberModel(topic.getStroke(), 0, 5, 1));

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(EditTopicDialog.this,
                        "Cancel pending changes?",
                        "Confirm cancel",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) != 0)
                    return;
                result = Result.CANCEL;
                dispose();
            }
        });

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!name.getText().equals(MULTI_VALUES_NAME)) {
                    if (!(name.getText().matches("[a-zA-Z0-9 _-]+"))) {
                        AppCore.getInstance().getMessageGenerator().warning(
                                "Topic name can only contain chars a-z, A-Z, 0-9 and _-."
                        );
                        return;
                    }
                }

                result = Result.APPLY;
                dispose();
            }
        });

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel rowName = new JPanel(new BorderLayout(5, 0));
        rowName.add(lbName, BorderLayout.WEST);
        rowName.add(name, BorderLayout.CENTER);

        JPanel rowLang = new JPanel(new BorderLayout(5, 0));
        rowLang.add(lbBorder, BorderLayout.WEST);
        rowLang.add(border, BorderLayout.CENTER);

        top.add(Box.createVerticalStrut(5));
        top.add(rowName);
        top.add(Box.createVerticalStrut(5));
        top.add(rowLang);
        top.add(Box.createVerticalStrut(5));

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(btnCancel, BorderLayout.WEST);
        buttons.add(btnSave, BorderLayout.EAST);
        buttons.add(Box.createVerticalStrut(5), BorderLayout.NORTH);

        JPanel container = new JPanel(new BorderLayout());
        container.add(top, BorderLayout.NORTH);
        container.add(color, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(container);
    }

    public String getName() {
        return name.getText();
    }

    public String getColor() {
        return String.format("#%02X%02X%02X",
                color.getColor().getRed(),
                color.getColor().getGreen(),
                color.getColor().getBlue()
        );
    }

    public int getStroke() {
        return (int) border.getValue();
    }

    public Result getResult() {
        return result;
    }

    /**
     * Possible outcomes of the dialog.
     */
    public enum Result {
        CANCEL,
        APPLY,
    }
}
