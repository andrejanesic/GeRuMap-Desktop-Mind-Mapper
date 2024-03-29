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

        if (topic.isSelected()) {
            Font fontPrev = g.getFont();
            Stroke strokePrev = g.getStroke();

            g.setColor(Color.decode(topic.getColor()));
            g.fill(shape);

            g.setColor(Color.BLACK);
            g.setFont(new Font(fontPrev.getFontName(), Font.BOLD, fontPrev.getSize()));
            g.drawString(topic.getNodeName(), (int) (topic.getX() - textW / 2.0f), (int) (topic.getY() + textH / 2.0f));

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(5));
            g.draw(shape);

            // revert settings
            g.setFont(fontPrev);
            g.setStroke(strokePrev);
            return;
        }

        g.setColor(Color.decode(topic.getColor()));
        g.fill(shape);

        g.setColor(Color.BLACK);
        g.drawString(topic.getNodeName(), (int) (topic.getX() - textW / 2.0f), (int) (topic.getY() + textH / 2.0f));

        Color border = Color.decode(topic.getColor()).darker();
        g.setColor(border);
        g.setStroke(new BasicStroke(getElement().getStroke()));
        g.draw(shape);
    }

    @Override
    public boolean elementAt(Point p) {
        if (p == null) return false;
        //double scaling = 1.0;
        //return getShape().contains(p.getX()
        return getShape().contains(p.getX(), p.getY());
        // return getShape().contains(p.getX(), p.getY());
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}
