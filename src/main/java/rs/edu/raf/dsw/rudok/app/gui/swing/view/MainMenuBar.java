package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {
    public MainMenuBar() {

        // TODO add multi-lingual support

        JMenu file = new JMenu("File");
        JMenuItem fileNew = new JMenuItem("New project");
        JMenuItem fileOpen = new JMenuItem("Open project");
        JMenuItem fileClose = new JMenuItem("Close project");
        JMenuItem fileQuit = new JMenuItem("Exit");

        JMenu edit = new JMenu("Edit");
        JMenuItem editUndo = new JMenuItem("Undo");
        JMenuItem editRedo = new JMenuItem("Redo");
        JMenuItem editNew = new JMenuItem("Add");
        JMenuItem editDelete = new JMenuItem("Delete");
        JMenuItem editPreferences = new JMenuItem("Preferences");

        JMenu help = new JMenu("Help");
        JMenuItem helpAuthor = new JMenuItem("Project authors");

        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileClose);
        file.addSeparator();
        file.add(fileQuit);

        edit.add(editUndo);
        edit.add(editRedo);
        edit.addSeparator();
        edit.add(editNew);
        edit.add(editDelete);
        edit.addSeparator();
        edit.add(editPreferences);

        help.add(helpAuthor);

        this.add(file);
        this.add(edit);
        this.add(help);

        editNew.addActionListener(MainFrame.getInstance().getActionManager().getNewAction());
        editDelete.addActionListener(MainFrame.getInstance().getActionManager().getDeleteAction());
        helpAuthor.addActionListener(MainFrame.getInstance().getActionManager().getProjectAuthorAction());
    }
}
