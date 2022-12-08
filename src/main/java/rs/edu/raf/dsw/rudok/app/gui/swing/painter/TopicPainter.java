package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.diagram.view.DiagramFramework;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class TopicPainter extends ElementPainter {

    private Shape shape;

    public TopicPainter(Element element) {
        super(element);
    }

    @Override
    public void draw(Graphics g) {
        Topic topic = (Topic) getElement();
        shape = new Ellipse2D.Float(
                (float) topic.getX(),
                (float) topic.getY(),
                topic.getNodeName().length()*7,
                g.getFont().getSize2D() + 20
        );
        g.drawString(topic.getNodeName(), topic.getX() + 10, topic.getY()+20);
        ((Graphics2D) g).draw(shape);
        DiagramFramework.getShapes().add(shape);
        System.out.println(topic.getNodeName().length());
    }

    @Override
    public boolean elementAt(List<Shape> list) {
        for (Shape s : list) {
            if (s.contains(
                    ((Topic) getElement()).getX(),
                    ((Topic) getElement()).getY()
            )) return true;
        }
        return false;
    }

    public Shape getShape() {
        return shape;
    }
}