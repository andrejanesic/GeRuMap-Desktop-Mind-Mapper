package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TopicPainter extends ElementPainter {

    private Shape shape;

    public TopicPainter(Element element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Topic topic = (Topic) getElement();
        float textW = g.getFontMetrics().stringWidth(topic.getNodeName());
        float totalW = textW + topic.getWidth() * 2.0f;
        float textH = g.getFont().getSize2D();
        float totalH = textH + topic.getHeight() * 2.0f;
        shape = new Ellipse2D.Float(
                (float) topic.getX() - totalW / 2,
                (float) topic.getY() - totalH / 2,
                totalW,
                totalH
        );

        if (this.isSelected()) {
            Font fontPrev = g.getFont();
            Stroke strokePrev = g.getStroke();

            g.setFont(new Font(fontPrev.getFontName(), Font.BOLD, fontPrev.getSize()));
            g.drawString(topic.getNodeName(), (int) (topic.getX() - textW / 2.0f), (int) (topic.getY() + textH / 2.0f));

            g.setStroke(new BasicStroke(4));
            g.draw(shape);

            // revert settings
            g.setFont(fontPrev);
            g.setStroke(strokePrev);
            return;
        }

        g.drawString(topic.getNodeName(), (int) (topic.getX() - textW / 2.0f), (int) (topic.getY() + textH / 2.0f));
        g.draw(shape);
    }

    @Override
    public boolean elementAt(Point p) {
        if (p == null) return false;
        return getShape().contains(p.getX(), p.getY());
    }

    public Shape getShape() {
        return shape;
    }
}
