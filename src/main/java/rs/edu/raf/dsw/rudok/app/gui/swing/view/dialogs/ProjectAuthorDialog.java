package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import rs.edu.raf.dsw.rudok.app.gui.swing.tree.model.MapTreeItem;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProjectAuthorDialog extends JDialog {
    private JLabel lbAuthor;
    private JTextField tfAuthor;
    private JButton button;

    public ProjectAuthorDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.setSize(new Dimension(350,100));
        this.setLayout(new BorderLayout());

        this.setLocationRelativeTo(owner);

        this.lbAuthor = new JLabel("Enter the name of the author of the project:");
        this.tfAuthor = new JTextField();
        this.button = new JButton("Confirm");

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapTreeView mapTreeView = MainFrame.getInstance().getMapTreeView();
                MapTreeItem item = (MapTreeItem)mapTreeView.getLastSelectedPathComponent();
                Project node = (Project)item.getMapNode();
                node.setAuthorName(tfAuthor.getText());
            }
        });

        JPanel panel = new JPanel();
        BoxLayout box = new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(box);

        panel.add(lbAuthor);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tfAuthor);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);
        tfAuthor.setMaximumSize(new Dimension(450,20));
        this.add(panel,BorderLayout.CENTER);
    }
}
