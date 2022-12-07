package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class ConnectionPainter extends ElementPainter{
    @Override
    public void draw(Graphics g, Element e) {
        Connection c = (Connection)e;
        g.drawLine(c.getFrom().getX(),c.getFrom().getY(),c.getTo().getX(),c.getTo().getY());
    }

    @Override
    public boolean elementAt(List<Shape> list,int x,int y) {
        for(Shape s:list){
            if(s.contains(x,y)) return true;
        }
        return false;
    }
}
