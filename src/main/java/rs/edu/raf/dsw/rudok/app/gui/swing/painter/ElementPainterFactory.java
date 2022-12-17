package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

import java.util.HashMap;
import java.util.Map;

public class ElementPainterFactory {

    /**
     * Each element will have at most one {@link ElementPainter}. This way, we can statically call to get the painter
     * for any {@link Element}.
     */
    private static Map<Element, ElementPainter> map = new HashMap<>();

    public static ElementPainter getPainter(Element e) {
        if (e instanceof Connection) {
            ElementPainter ep = map.getOrDefault(e, null);
            if (ep == null) {
                ep = new ConnectionPainter(e);
                map.put(e, ep);
            }
            return ep;
        } else if (e instanceof Topic) {
            ElementPainter ep = map.getOrDefault(e, null);
            if (ep == null) {
                ep = new TopicPainter(e);
                map.put(e, ep);
            }
            return ep;
        } else {
            throw new RuntimeException(
                    "Unexpected type of ElementPainterFactory argument: " + e.getClass().getSimpleName()
            );
        }
    }
}
