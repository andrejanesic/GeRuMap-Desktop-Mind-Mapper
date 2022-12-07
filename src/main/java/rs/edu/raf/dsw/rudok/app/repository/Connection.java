package rs.edu.raf.dsw.rudok.app.repository;

/**
 * Connects two {@link Topic}s together under one {@link MindMap}.
 */
public class Connection extends Element {

    /**
     * Connected {@link Topic}s.
     */
    private Topic from, to;

    public Connection(String nodeName, int color, Topic from, Topic to) {
        super(nodeName, color);
        this.from = from;
        this.to = to;
    }

    public void setFrom(Topic from) {
        this.from = from;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "from",
                        from
                )
        ));
    }

    public void setTo(Topic to) {
        this.to = to;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "to",
                        to
                )
        ));
    }

    public Topic getFrom() {
        return from;
    }

    public Topic getTo() {
        return to;
    }
}
