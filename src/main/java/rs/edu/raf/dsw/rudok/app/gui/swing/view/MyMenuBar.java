package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar() {
        JMenu file = new JMenu("File");
        JMenuItem menuNew = new JMenuItem("New");
        JMenuItem delete = new JMenuItem("Delete");
        JMenu help = new JMenu("Help");
        JMenu edit = new JMenu("Edit");
        JMenuItem author = new JMenuItem("Project Author");


        file.add(menuNew);
        file.addSeparator();
        file.add(delete);
        help.add(edit);
        edit.add(author);
        this.add(file);
        this.add(help);

        menuNew.addActionListener(MainFrame.getInstance().getActionManager().getNewAction());
        delete.addActionListener(MainFrame.getInstance().getActionManager().getDeleteAction());
        author.addActionListener(MainFrame.getInstance().getActionManager().getProjectAuthorAction());
    }
}
