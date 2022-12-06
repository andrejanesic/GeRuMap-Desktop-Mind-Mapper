package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import javax.swing.*;

public class MindMapToolbar extends JToolBar {

    public MindMapToolbar(MindMapPanel mindMapPanel) {
        setFloatable(true);

        // Add state switchers
        add(mindMapPanel.getStateActionManager().getStartSelectTopicStateAction());
        add(mindMapPanel.getStateActionManager().getStartZoomStateAction());
        add(mindMapPanel.getStateActionManager().getStartAddTopicStateAction());
        add(mindMapPanel.getStateActionManager().getStartDrawConnectionStateAction());
        add(mindMapPanel.getStateActionManager().getStartMoveTopicStateAction());
        add(mindMapPanel.getStateActionManager().getStartDeleteElementStateAction());
    }
}
