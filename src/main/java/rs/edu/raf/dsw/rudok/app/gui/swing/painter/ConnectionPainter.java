package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ConnectionPainter extends ElementPainter{
    @Override
    public void draw(Graphics g) {
        ((Graphics2D)g).draw(new Ellipse2D.Float());
    }

    @Override
    public boolean elementAt() {
        return false;
    }
}
