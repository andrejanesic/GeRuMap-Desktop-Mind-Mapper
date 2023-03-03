package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller.ActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.controller.IActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller.DiagramController;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.controller.IDiagramController;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IStateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.StateManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.IStateActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller.StateActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.toolbar.MindMapToolbar;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

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

    /**
     * Manager for other actions.
     */
    private final IActionManager actionManager;

    /**
     * Used for painting the mind map.
     */
    private final IDiagramController diagramController;

    public MindMapPanel(MindMap mindMap) {
        super(new BorderLayout());
        this.mindMap = mindMap;
        actionManager = new ActionManager(this);
        stateManager = new StateManager(mindMap);
        stateActionManager = new StateActionManager(this);

        // Add map visualizer
        diagramController = new DiagramController(this);
        JPanel diagram = diagramController.getView();
        add(diagram, BorderLayout.CENTER);

        // Add toolbar
        JToolBar toolbar = new MindMapToolbar(this);
        add(toolbar, BorderLayout.PAGE_START);
    }

    @Override
    public MindMap getMindMap() {
        return mindMap;
    }

    @Override
    public IActionManager getActionManager() {
        return actionManager;
    }

    @Override
    public IStateActionManager getStateActionManager() {
        return stateActionManager;
    }

    @Override
    public IDiagramController getDiagramController() {
        return diagramController;
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
    public void mouseDrawStateMigrate(int x1, int y1, int x2, int y2, boolean complete) {
        stateManager.getState().migrate(mindMap, x1, y1, x2, y2, complete);
    }

    @Override
    public void mouseDrawStateMigrate(Topic t1, Topic t2) {
        stateManager.getState().migrate(mindMap, t1, t2);
    }

    @Override
    public void mouseClickStateMigrate(int x1, int y1) {
        stateManager.getState().migrate(mindMap, x1, y1);
    }

    @Override
    public void mouseClickStateMigrate(Topic topic) {
        stateManager.getState().migrate(mindMap, topic);
    }

    @Override
    public void mouseDrawStateMigrate(Topic... topics) {
        stateManager.getState().migrate(mindMap, topics);
    }

    @Override
    public void mouseDrawStateMigrate(Topic t, int x1, int y1, boolean complete) {
        stateManager.getState().migrate(mindMap, t, x1, y1, complete);
    }
}
