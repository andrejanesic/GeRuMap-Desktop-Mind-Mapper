package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;

public abstract class ElementPainter {
    Element e;
    public abstract void draw(Graphics g);
    public abstract boolean elementAt();

}
