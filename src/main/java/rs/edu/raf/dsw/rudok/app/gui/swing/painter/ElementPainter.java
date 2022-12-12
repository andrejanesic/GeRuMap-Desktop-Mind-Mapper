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
     * Returns true if the element's shape includes the passed {@link Point}.
     *
     * @param p {@link Point} where the interaction happened.
     * @return True if the element was clicked, false otherwise.
     */
    public abstract boolean elementAt(Point p);

}
