package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;
import java.util.List;

public abstract class ElementPainter {
    public abstract void draw(Graphics g, Element e);

    public abstract boolean elementAt(List<Shape> list,int x,int y);


}
