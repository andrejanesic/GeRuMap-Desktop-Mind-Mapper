package rs.edu.raf.dsw.rudok.app.repository;

import java.awt.*;
import java.util.Set;

public class Element extends IMapNode {

    private int stroke;
    private String color;

    public Element(String nodeName, int stroke, String color) {
        super(nodeName);
        this.stroke = stroke;
        this.color = color;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "stroke",
                        stroke
                )
        ));
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "color",
                        color
                )
        ));
    }

    @Override
    public void setParents(Set<IMapNodeComposite> parents) {
        for (IMapNode parent : parents) {
            if (!(parent instanceof MindMap)) return;
        }
        super.setParents(parents);
    }

    @Override
    public void addParent(IMapNodeComposite parent) {
        if (parent instanceof MindMap) super.addParent(parent);
    }
}
