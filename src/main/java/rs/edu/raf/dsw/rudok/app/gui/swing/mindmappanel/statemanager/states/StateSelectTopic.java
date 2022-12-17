package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainter;
import rs.edu.raf.dsw.rudok.app.gui.swing.painter.ElementPainterFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class StateSelectTopic extends IState {

    private final Set<Topic> tempSelect = new HashSet<>();

    @Override
    public void migrate(MindMap parent, int x1, int y1, int x2, int y2, boolean complete) {
        int trX = (int) MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().getTranslationX();
        int trY = (int) MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().getTranslationY();
        double scaling = MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().getScaling();
        x1 -= trX;
        x2 -= trX;
        y1 -= trY;
        y2 -= trY;

        // Add every touched and contained element
        for (IMapNode e : parent.getChildren()) {
            if (!(e instanceof Topic)) continue;
            Topic t = (Topic) e;
            ElementPainter ep = ElementPainterFactory.getPainter((Element) e);
            Shape s = ep.getShape();

            // If touched
            if (ElementPainterFactory.getPainter(t).elementAt(new Point(x2, y2))) {
                tempSelect.add(t);
                t.setSelected(true);
                continue;
            }

            // If contained
            if (s != null) {
                Area rect = new Area(new Rectangle2D.Float(
                        Math.min(x1, x2),
                        Math.min(y1, y2),
                        Math.abs(x1 - x2),
                        Math.abs(y1 - y2)
                ));
                Area sh = new Area(s);
                rect.intersect(sh);
                if (!rect.isEmpty()) {
                    t.setSelected(true);
                    tempSelect.add(t);
                    continue;
                }
            }

            tempSelect.remove(t);
            t.setSelected(false);
        }

        if (complete) {
            Topic[] topics = new Topic[tempSelect.size()];
            int i = 0;
            for (Topic t : tempSelect) {
                topics[i] = t;
            }
            tempSelect.clear();

            super.commit(parent, topics);

            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().clearHelpers();
        } else {
            MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                    .getActiveMindMapPanel().getDiagramController().getView().paintRectangle(
                            x1, y1, x2, y2);
        }

        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().repaint();
        MainFrame.getInstance().getProjectExplorerPanel().getProjectPanel()
                .getActiveMindMapPanel().getDiagramController().getView().revalidate();
    }

    @Override
    public void migrate(MindMap parent, int x1, int y1) {
        int l = 0;
        for (IMapNode mapNode : parent.getChildren()) {
            if (mapNode == null) continue;
            if (!(mapNode instanceof Topic)) continue;
            if (((Topic) mapNode).isSelected()) l++;
        }

        Topic[] topics = new Topic[l];
        int i = 0;
        for (IMapNode mapNode : parent.getChildren()) {
            if (mapNode == null) continue;
            if (!(mapNode instanceof Topic)) continue;
            if (!((Topic) mapNode).isSelected()) continue;
            topics[i++] = (Topic) mapNode;
            ((Topic) mapNode).setSelected(false);
        }

        super.commit(parent, topics);
    }

    @Override
    public void migrate(MindMap parent, Topic... topics) {
        int l = 0;
        for (IMapNode mapNode : parent.getChildren()) {
            if (mapNode == null) continue;
            if (!(mapNode instanceof Topic)) continue;
            l++;
        }

        int i = 0;
        Topic[] flipped = new Topic[l];
        for (IMapNode e : parent.getChildren()) {
            if (!(e instanceof Topic)) continue;
            Topic t = (Topic) e;
            t.setSelected(false);
            for (Topic t1 : topics) {
                if (t == t1) {
                    t.setSelected(true);
                    break;
                }
            }
            flipped[i] = t;
        }

        super.commit(parent, flipped);
    }

    @Override
    public void rollback(Object... params) {
        try {
            for (Topic t : (Topic[]) params[1]) {
                if (t == null) continue;
                t.setSelected(!t.isSelected());
            }
        } catch (Exception e) {
            AppCore.getInstance().getMessageGenerator().error(e.getMessage());
        }
    }
}
