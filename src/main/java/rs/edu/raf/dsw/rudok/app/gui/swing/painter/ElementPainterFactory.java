package rs.edu.raf.dsw.rudok.app.gui.swing.painter;

import rs.edu.raf.dsw.rudok.app.repository.Connection;
import rs.edu.raf.dsw.rudok.app.repository.Element;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

public class ElementPainterFactory {

    public static ElementPainter getPainter(Element e) {
        if (e instanceof Connection) {
            return new ConnectionPainter(e);
        } else if (e instanceof Topic) {
            return new TopicPainter(e);
        } else {
            throw new RuntimeException(
                    "Unexpected type of ElementPainterFactory argument: " + e.getClass().getSimpleName()
            );
        }
    }
}
