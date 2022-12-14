package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.EditTopicDialog;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * This action opens the menu for editing the selected {@link rs.edu.raf.dsw.rudok.app.repository.Topic}(s).
 */
public class EditTopicAction extends IAction {

    private final MindMap mindMap;

    public EditTopicAction(IMindMapPanel mindMapPanel) {
        super("/images/palette.png", "Edit topic");
        mindMap = mindMapPanel.getMindMap();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Set<Topic> selected = new HashSet<>();
        for (IMapNode c : mindMap.getChildren()) {
            if (!(c instanceof Topic) || !((Topic) c).isSelected()) continue;
            selected.add((Topic) c);
        }

        if (selected.isEmpty()) {
            AppCore.getInstance().getMessageGenerator().error(
                    "No topics selected."
            );
            return;
        }

        EditTopicDialog dialog = new EditTopicDialog(selected);
        dialog.setVisible(true);
        EditTopicDialog.Result result = dialog.getResult();
        if (result == null || result.equals(EditTopicDialog.Result.CANCEL)) return;

        String name = dialog.getName();
        String color = dialog.getColor();
        int stroke = dialog.getStroke();

        if (name.equals(EditTopicDialog.MULTI_VALUES_NAME)) {
            for (Topic t : selected) {
                t.setColor(color);
                t.setStroke(stroke);
            }
        } else {
            for (Topic t : selected) {
                t.setNodeName(name);
                t.setColor(color);
                t.setStroke(stroke);
            }
        }
    }
}
