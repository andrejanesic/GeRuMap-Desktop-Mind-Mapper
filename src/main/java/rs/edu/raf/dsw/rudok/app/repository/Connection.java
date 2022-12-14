package rs.edu.raf.dsw.rudok.app.repository;

/**
 * Connects two {@link Topic}s together under one {@link MindMap}.
 */
public class Connection extends Element {

    /**
     * Connected {@link Topic}s.
     */
    private Topic from, to;

    public Connection(String nodeName, int stroke, String color, Topic from, Topic to) {
        super(nodeName, stroke, color);
        this.from = from;
        this.to = to;
        from.addObserver(this);
        to.addObserver(this);
    }

    public Topic getFrom() {
        return from;
    }

    public void setFrom(Topic from) {
        this.from = from;
        this.publish(new IMapNode.Message(
                IMapNode.Message.Type.EDITED,
                new IMapNode.Message.EditedMessageData(
                        this,
                        "from",
                        from
                )
        ));
    }

    public Topic getTo() {
        return to;
    }

    public void setTo(Topic to) {
        this.to = to;
        this.publish(new IMapNode.Message(
                IMapNode.Message.Type.EDITED,
                new IMapNode.Message.EditedMessageData(
                        this,
                        "to",
                        to
                )
        ));
    }

    @Override
    public void receive(Object message) {
        super.receive(message);

        // Listen to connected topics being removed and remove the connection if associated.
        if (message instanceof IMapNodeComposite.Message) {
            if (((IMapNodeComposite.Message) message).getStatus()
                    .equals(IMapNodeComposite.Message.Type.CHILD_REMOVED)) {
                IMapNodeComposite.Message.ChildChangeMessageData d = (IMapNodeComposite.Message.ChildChangeMessageData)
                        ((IMapNodeComposite.Message) message).getData();

                if (!(d.getChild() instanceof Topic)) return;
                Topic target = (Topic) d.getChild();
                if (from != target && to != target) return;
                d.getParent().removeChild(this);
            }
        }
    }
}
