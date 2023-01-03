package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditMindMapDialog;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This action opens the edit menu for the currently active {@link MindMap}.
 */
public class EditMindMapAction extends IAction {

    private final MindMap mindMap;

    public EditMindMapAction(IMindMapPanel mindMapPanel) {
        super("/images/gear.png", "Edit Mind map");
        mindMap = mindMapPanel.getMindMap();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditMindMapDialog dialog = new EditMindMapDialog(mindMap);
        dialog.setVisible(true);
        if (dialog.getResult() == null || dialog.getResult().equals(EditMindMapDialog.Result.CANCELED)) return;

        boolean changed = false;
        if (!dialog.getNodeName().equals(mindMap.getNodeName())) {
            mindMap.setNodeName(dialog.getNodeName());
            changed = true;
        }

        if (dialog.isTemplate() != mindMap.isTemplate()) {
            mindMap.setTemplate(dialog.isTemplate());
            changed = true;
        }

        if (dialog.getTemplate() == null) {
            if (changed)
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Changes saved");
            return;
        }
        String path = dialog.getTemplate();
        MindMap template = AppCore.getInstance().getFileSystem().loadMindMapTemplate(path);
        if (template == null) {
            // errored, but other changes saved
            if (changed)
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Changes saved");
            return;
        }
        mindMap.copyTemplate(template);
        JOptionPane.showMessageDialog(MainFrame.getInstance(), "Template applied");
    }
}
