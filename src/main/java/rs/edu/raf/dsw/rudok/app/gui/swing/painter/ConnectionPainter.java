package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;

public class ConnectionPainter extends ElementPainter {

    public ConnectionPainter(Element element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Connection c = (Connection) getElement();
        int x1 = c.getFrom().getX();
        int y1 = c.getFrom().getY();
        int x2 = c.getTo().getX();
        int y2 = c.getTo().getY();
        Color c1 = Color.decode(c.getFrom().getColor()).darker();
        Color c2 = Color.decode(c.getTo().getColor()).darker();
        g.setStroke(new BasicStroke(2));
        g.setPaint(new GradientPaint((float) x1, (float) y1, c1, (float) x2, (float) y2, c2));
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public boolean elementAt(Point p) {
        // TODO connections can't be clicked by default
        return false;
    }

    @Override
    public Shape getShape() {
        return null;
    }
}
