package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.confighandler.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

public class ConfigDialog extends JDialog {

    private final JTextField tfName;
    private final JComboBox<String> cbLang;
    private final JCheckBox cbAutosave;
    private Result result = null;
    private String loadedConfig = null;

    public ConfigDialog() {
        super(MainFrame.getInstance(), "Edit preferences", true);

        this.setSize(new Dimension(260, 160));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(MainFrame.getInstance());

        IConfigHandler ref = AppCore.getInstance().getConfigHandler();
        JLabel lbName = new JLabel("Profile name");
        JLabel lbLang = new JLabel("Language");
        JLabel lbAutosave = new JLabel("Autosave projects");

        tfName = new JTextField();
        tfName.setText(ref.get("config", "Unknown").toString());

        String[] langs = new String[]{"English"};
        cbLang = new JComboBox<>(langs);
        cbLang.setSelectedIndex(Arrays.asList(langs).indexOf((String) ref.get("language", "English")));

        cbAutosave = new JCheckBox("Autosave", Boolean.parseBoolean((String) ref.get("autosave", "false")));

        JButton btNew = new JButton("Reset");
        btNew.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(ConfigDialog.this,
                        "Are you sure you want to reset preferences and overwrite default preferences profile?",
                        "Confirm reset",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) != 0)
                    return;
                result = Result.CONFIG_NEW;
                dispose();
            }
        });

        JButton btLoad = new JButton("Load");
        btLoad.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(AppCore.getInstance().getConstants().FILESYSTEM_LOCAL_CONFIG_FOLDER()));
                int returnVal = fc.showOpenDialog(ConfigDialog.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    if (JOptionPane.showConfirmDialog(ConfigDialog.this,
                            "Are you sure you want to load this preferences profile?",
                            "Confirm load",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) != 0)
                        return;
                    File file = fc.getSelectedFile();
                    result = Result.CONFIG_LOADED;
                    loadedConfig = file.getName();
                    dispose();
                }
            }
        });

        JButton btSave = new JButton("Save");
        btSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(tfName.getText().matches("[a-zA-Z0-9 _-]+"))) {
                    AppCore.getInstance().getMessageGenerator().warning(
                            "Preferences profile name can only contain chars a-z, A-Z, 0-9 and _-."
                    );
                    return;
                }
                result = Result.CONFIG_SAVED;
                dispose();
            }
        });

        JPanel central = new JPanel();
        central.setLayout(new BoxLayout(central, BoxLayout.Y_AXIS));

        JPanel rowName = new JPanel(new BorderLayout(5, 0));
        rowName.add(lbName, BorderLayout.WEST);
        rowName.add(tfName, BorderLayout.CENTER);

        JPanel rowLang = new JPanel(new BorderLayout(5, 0));
        rowLang.add(lbLang, BorderLayout.WEST);
        rowLang.add(cbLang, BorderLayout.CENTER);

        JPanel rowAutosave = new JPanel(new BorderLayout(5, 0));
        rowLang.add(lbAutosave, BorderLayout.WEST);
        rowLang.add(cbAutosave, BorderLayout.CENTER);

        central.add(Box.createVerticalStrut(5));
        central.add(rowName);
        central.add(Box.createVerticalStrut(5));
        central.add(rowLang);
        central.add(Box.createVerticalStrut(5));
        central.add(rowAutosave);
        central.add(Box.createVerticalStrut(5));

        JPanel buttons = new JPanel(new BorderLayout());
        JPanel leftGr = new JPanel();
        leftGr.setLayout(new BoxLayout(leftGr, BoxLayout.X_AXIS));
        leftGr.add(btNew);
        leftGr.add(Box.createHorizontalStrut(5));
        leftGr.add(btLoad);
        buttons.add(leftGr, BorderLayout.WEST);
        buttons.add(btSave, BorderLayout.EAST);
        buttons.add(Box.createVerticalStrut(5), BorderLayout.NORTH);

        JPanel container = new JPanel(new BorderLayout());
        container.add(central, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(container);
    }

    public String getName() {
        return tfName.getText();
    }

    public String getLang() {
        if (cbLang.getSelectedItem() == null) return "English";
        return cbLang.getSelectedItem().toString();
    }

    public boolean getAutosave() {
        return cbAutosave.isSelected();
    }

    public Result getResult() {
        return result;
    }

    public String getLoadedConfig() {
        return loadedConfig;
    }

    /**
     * Dialog result states upon disposal.
     */
    public enum Result {
        CONFIG_LOADED,
        CONFIG_SAVED,
        CONFIG_NEW,
    }
}
