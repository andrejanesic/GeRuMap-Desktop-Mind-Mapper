package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Dialog for choosing the {@link rs.edu.raf.dsw.rudok.app.repository.MindMap} template file.
 */
public class TemplateDialog extends JDialog {
    private final JFileChooser jFileChooser;
    private final JButton btConfirm;

    public TemplateDialog() {
        super(MainFrame.getInstance(), "Choose a template", true);

        this.setSize(new Dimension(500, 450));
        this.setMinimumSize(new Dimension(500, 450));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(MainFrame.getInstance());

        this.jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setControlButtonsAreShown(false);
        jFileChooser.setCurrentDirectory(new File(AppCore.getInstance().getConstants().MINDMAP_TEMPLATES_DIR()));
        this.btConfirm = new JButton("Confirm");
        btConfirm.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(jFileChooser, BorderLayout.CENTER);
        this.add(btConfirm, BorderLayout.SOUTH);
    }

    public String getPath() {
        if (jFileChooser.getSelectedFile() == null) return null;
        return jFileChooser.getSelectedFile().getAbsolutePath();
    }
}
