package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TemplateDialog extends JDialog {
    private final JFileChooser jFileChooser;
    private final JButton btConfirm;
    public TemplateDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.setSize(new Dimension(500, 450));
        this.setMinimumSize(new Dimension(500, 450));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(owner);

        this.jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setControlButtonsAreShown(false);
        this.btConfirm = new JButton("Confirm");
        btConfirm.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo
                dispose();
            }
        });
        this.add(jFileChooser,BorderLayout.CENTER);
        this.add(btConfirm,BorderLayout.SOUTH);

    }
}
