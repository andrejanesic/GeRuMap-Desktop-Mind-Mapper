package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

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
        float textW = topic.getNodeName().length() * g.getFont().getSize2D();
        float totalW = textW + topic.getWidth() * 2.0f;
        float textH = g.getFont().getSize2D();
        float totalH = textH + topic.getHeight() * 2.0f;
        shape = new Ellipse2D.Float(
                (float) topic.getX() - totalW / 2,
                (float) topic.getY() - totalH / 2,
                totalW,
                totalH
        );
        g.drawString(topic.getNodeName(), (int) (topic.getX() - textW / 2), (int) (topic.getY() - textH / 2));
        ((Graphics2D) g).draw(shape);
        // DiagramFramework.getShapes().add(shape);
        // System.out.println(topic.getNodeName().length());
    }

    @Override
    public boolean elementAt(Point p) {
        return getShape().contains(p.getX(), p.getY());
    }

    public Shape getShape() {
        return shape;
    }
}
