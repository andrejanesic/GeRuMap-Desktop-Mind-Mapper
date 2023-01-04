package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.toolbar;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.IMindMapPanel;

public class MindMapToolbar extends IMindMapToolbar {

    public MindMapToolbar(IMindMapPanel mindMapPanel) {
        setFloatable(true);

        // Add state switchers
        add(mindMapPanel.getStateActionManager().getStartSelectTopicStateAction());
        add(mindMapPanel.getStateActionManager().getStartAddTopicStateAction());
        add(mindMapPanel.getStateActionManager().getStartDrawConnectionStateAction());
        add(mindMapPanel.getStateActionManager().getStartMoveTopicStateAction());
        add(mindMapPanel.getStateActionManager().getStartDeleteElementStateAction());
        add(mindMapPanel.getActionManager().getEditTopicAction());
        add(mindMapPanel.getActionManager().getZoomInAction());
        add(mindMapPanel.getActionManager().getZoomOutAction());
        add(mindMapPanel.getActionManager().getEditMindMapAction());
        add(mindMapPanel.getActionManager().getCenterTopicAction());
    }
}
