package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;

public class ConnectionPainter extends ElementPainter {

    public ConnectionPainter(Element element) {
        super(element);
    }

    @Override
    public void draw(Graphics g) {
        Connection c = (Connection) getElement();
        g.drawLine(c.getFrom().getX(), c.getFrom().getY(), c.getTo().getX(), c.getTo().getY());
    }

    @Override
    public boolean elementAt(Point p) {
        // TODO connections can't be clicked by default
        return false;
    }
}
