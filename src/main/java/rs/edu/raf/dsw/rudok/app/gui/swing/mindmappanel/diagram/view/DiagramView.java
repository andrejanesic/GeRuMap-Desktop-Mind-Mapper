package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import java.awt.*;

public class DiagramView extends IDiagramView {

    /**
     * The painted {@link MindMap}.
     */
    private final MindMap mindMap;

    /**
     * Where the {@link MindMap} is painted.
     */
    private final DiagramFramework framework;

    public DiagramView(MindMap mindMap) {
        this.mindMap = mindMap;
        setLayout(new BorderLayout());

        this.framework = new DiagramFramework(mindMap);
        framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(framework, BorderLayout.CENTER);
    }

    @Override
    public int getCenterX() {
        return framework.getWidth() / 2;
    }

    @Override
    public int getCenterY() {
        return framework.getHeight() / 2;
    }

    @Override
    public void paintRectangle(int x1, int y1, int x2, int y2) {
        framework.paintRectangle(x1, y1, x2, y2);
    }

    @Override
    public void paintLine(int x1, int y1, int x2, int y2) {
        framework.paintLine(x1, y1, x2, y2);
    }

    @Override
    public void clearHelpers() {
        framework.clearHelpers();
    }

    @Override
    public void zoomIn(double coefficient) {
        getFramework().zoomIn(coefficient);
    }

    @Override
    public void zoomOut(double coefficient) {
        getFramework().zoomOut(coefficient);
    }

    @Override
    public void translateView(int dx, int dy) {
        framework.translateView(dx, dy);
    }

    @Override
    public double getScaling() {
        return framework.getScaling();
    }

    @Override
    public double getTranslationX() {
        return framework.getTranslateX();
    }

    @Override
    public double getTranslationY() {
        return framework.getTranslateY();
    }

    public DiagramFramework getFramework() {
        return framework;
    }
}
