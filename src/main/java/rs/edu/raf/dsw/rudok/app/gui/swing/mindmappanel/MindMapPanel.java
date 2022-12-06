package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.mapview.view.MapView;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.StateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.IStateActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.StateActionManager;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import java.awt.*;

public class MindMapPanel extends JPanel implements IMindMapPanel {

    /**
     * MindMap of the panel.
     */
    private final MindMap mindMap;

    /**
     * State manager.
     */
    private final IStateManager stateManager;

    /**
     * Manager for state-changing actions.
     */
    private final IStateActionManager stateActionManager;

    public MindMapPanel(MindMap mindMap) {
        super(new BorderLayout());
        this.mindMap = mindMap;
        stateManager = new StateManager();
        stateActionManager = new StateActionManager(this);

        // Add map visualizer
        JPanel mapView = new MapView(mindMap);
        add(mapView, BorderLayout.CENTER);

        // Add toolbar
        JToolBar toolbar = new MindMapToolbar(this);
        add(toolbar, BorderLayout.PAGE_START);
    }

    @Override
    public MindMap getMindMap() {
        return mindMap;
    }

    @Override
    public IStateActionManager getStateActionManager() {
        return stateActionManager;
    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }

    @Override
    public void startAddTopicState() {
        stateManager.setAddTopic();
    }

    @Override
    public void startDeleteElementState() {
        stateManager.setDeleteElement();
    }

    @Override
    public void startDrawConnectionState() {
        stateManager.setDrawConnection();
    }

    @Override
    public void startSelectTopicState() {
        stateManager.setSelectTopic();
    }

    @Override
    public void mouseClickStateMigrate(int x, int y) {
        // TODO should be replaced with actual required params
        // TODO check which state requires which params, but there shouldn't be any if-elses
        stateManager.getState().migrate(x, y);
    }

    @Override
    public void mouseDrawStateMigrate(int x1, int y1, int x2, int y2) {
        // TODO should be replaced with actual required params
        // TODO check which state requires which params, but there shouldn't be any if-elses
        stateManager.getState().migrate(x1, y1, x2, y2);
    }
}
