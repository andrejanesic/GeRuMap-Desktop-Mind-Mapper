package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Element;

import java.awt.*;
import java.util.List;

public abstract class ElementPainter {

    /**
     * The {@link Element} to be painted.
     */
    private final Element element;

    public ElementPainter(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    /**
     * Draws the wrapped {@link Element} on the canvas {@link Graphics} g.
     *
     * @param g {@link Graphics} canvas to paint on.
     */
    public abstract void draw(Graphics g);

    /**
     * Returns true if the element is painted in one of the passed {@link Shape}s.
     *
     * @param list List of {@link Shape}s the element may be located in.
     * @return True if the element is found in any of the given {@link Shape}s.
     */
    public abstract boolean elementAt(List<Shape> list);

}
