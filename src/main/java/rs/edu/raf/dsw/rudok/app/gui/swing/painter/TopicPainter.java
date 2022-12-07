package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class TopicPainter extends ElementPainter{
    @Override
    public void draw(Graphics g, Element e) {
        Topic topic = (Topic)e;
        g.drawString(e.getNodeName(), topic.getX(), topic.getY());
        ((Graphics2D)g).draw(new Ellipse2D.Float(topic.getX(), topic.getY(),e.getNodeName().length()+10,g.getFont().getSize2D()+10));
    }

    @Override
    public boolean elementAt(List<Shape> list,int x,int y) {
        for(Shape s:list){
            if(s.contains(x,y)) return true;
        }
        return false;
    }
}
