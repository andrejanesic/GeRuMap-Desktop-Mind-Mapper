package rs.edu.raf.dsw.rudok.app.repository;

/**
 * Represents a topic/"bubble" in the {@link MindMap}.
 */
public class Topic extends Element {

    private int width, height, x, y;

    public Topic(String nodeName, int stroke, int color, int x, int y, int w, int h) {
        super(nodeName, stroke, color);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void setWidth(int width) {
        this.width = width;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "width",
                        width
                )
        ));
    }

    public void setHeight(int height) {
        this.height = height;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "height",
                        height
                )
        ));
    }

    public void setX(int x) {
        this.x = x;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "x",
                        x
                )
        ));
    }

    public void setY(int y) {
        this.y = y;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "y",
                        y
                )
        ));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
