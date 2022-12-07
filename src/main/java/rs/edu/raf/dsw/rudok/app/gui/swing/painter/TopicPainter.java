package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class TopicPainter extends ElementPainter {

    public TopicPainter(Element element) {
        super(element);
    }

    @Override
    public void draw(Graphics g) {
        Topic topic = (Topic) getElement();
        g.drawString(topic.getNodeName(), topic.getX(), topic.getY());
        ((Graphics2D) g).draw(new Ellipse2D.Float(
                topic.getX(),
                topic.getY(),
                topic.getNodeName().length() + topic.getWidth() / 2.0f,
                g.getFont().getSize2D() + 10 + topic.getHeight() / 2.0f
        ));
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
}
