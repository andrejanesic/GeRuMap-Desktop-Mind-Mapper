package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar() {

        // TODO add multi-lingual support

        JMenu file = new JMenu("File");
        JMenuItem fileNew = new JMenuItem("New project");
        JMenuItem fileOpen = new JMenuItem("Open project");
        JMenuItem fileClose = new JMenuItem("Close project");
        JMenuItem fileSave = new JMenuItem("Save project");
        JMenuItem fileSaveAll = new JMenuItem("Save all projects");
        JMenuItem fileExport = new JMenuItem("Export mind map");
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
        file.add(fileSave);
        file.add(fileSaveAll);
        file.addSeparator();
        file.add(fileOpen);
        file.add(fileClose);
        file.addSeparator();
        file.add(fileExport);
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

        fileNew.addActionListener(MainFrame.getInstance().getActionManager().getNewProjectAction());
        fileSave.addActionListener(MainFrame.getInstance().getActionManager().getSaveProjectAction());
        fileSaveAll.addActionListener(MainFrame.getInstance().getActionManager().getSaveAllProjectsAction());
        fileOpen.addActionListener(MainFrame.getInstance().getActionManager().getOpenProjectAction());
        fileClose.addActionListener(MainFrame.getInstance().getActionManager().getCloseProjectAction());
        fileExport.addActionListener(MainFrame.getInstance().getActionManager().getExportAction());
        fileQuit.addActionListener(MainFrame.getInstance().getActionManager().getQuitAction());

        editUndo.addActionListener(MainFrame.getInstance().getActionManager().getUndoAction());
        editRedo.addActionListener(MainFrame.getInstance().getActionManager().getRedoAction());
        editNew.addActionListener(MainFrame.getInstance().getMapTree().getTreeActionManager().getTreeNewAction());
        editDelete.addActionListener(MainFrame.getInstance().getMapTree().getTreeActionManager().getTreeDeleteAction());
        editPreferences.addActionListener(MainFrame.getInstance().getActionManager().getOpenPreferencesAction());

        helpAuthor.addActionListener(MainFrame.getInstance().getActionManager().getStudentInfoAction());
    }

}
