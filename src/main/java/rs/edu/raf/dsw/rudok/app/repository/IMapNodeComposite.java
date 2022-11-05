package rs.edu.raf.dsw.rudok.app.repository;

import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Composite design pattern unit. Allowed to have children.
 */
public abstract class IMapNodeComposite extends IMapNode {

    /**
     * Set of all children.
     */
    private Set<IMapNode> children = new HashSet<>();

    public IMapNodeComposite(String nodeName) {
        super(nodeName);
    }

    public Set<IMapNode> getChildren() {
        return children;
    }

    public void setChildren(Set<IMapNode> children) {
        this.children = new HashSet<>();
        Iterator<IMapNode> iterator = children.iterator();
        while (iterator.hasNext()) {
            addChild(iterator.next());
        }
    }

    /**
     * Adds a child to the node.
     *
     * @param child Child to be added.
     */
    public void addChild(IMapNode child) {
        if (child == null) return;
        if (this.children.contains(child)) return;
        this.children.add(child);

        this.addObserver(child);
        this.publish(new Message(Message.Type.CHILD_ADDED,
                new Message.ChildChangeMessageData(this, this, child)));
    }

    /**
     * Removes a child from the node.
     *
     * @param child Child to be removed.
     */
    public void removeChild(IMapNode child) {
        if (child == null) return;
        if (!this.children.contains(child)) return;
        this.children.remove(child);

        this.removeObserver(child);
        this.publish(new Message(Message.Type.CHILD_REMOVED,
                new Message.ChildChangeMessageData(this, this, child)));
    }


    @Override
    public void receive(Object message) {

        if (message instanceof IMapNode.Message) {

            switch (((IMapNode.Message) message).getStatus()) {

                case PARENT_ADDED:
                    this.addChild(((IMapNode.Message.ParentChangeMessageData) ((IMapNode.Message) message).getData()).getChild());
                    return;

                case PARENT_REMOVED:
                    this.removeChild(((IMapNode.Message.ParentChangeMessageData) ((IMapNode.Message) message).getData()).getChild());
                    return;
            }
        }
    }

    /**
     * Message for publishing.
     */
    public static class Message extends IMessage<IMapNodeComposite.Message.Type, IMessageData> {

        public Message(Type status, IMessageData data) {
            super(status, data);
        }

        /**
         * Types of message.
         */
        public enum Type {
            // When a parent is added
            CHILD_ADDED,
            // When a parent is removed
            CHILD_REMOVED,
        }

        /**
         * For messages about changes on its children.
         */
        public static class ChildChangeMessageData extends IMessageData<IMapNodeComposite> {

            private final IMapNodeComposite parent;
            private final IMapNode child;

            public ChildChangeMessageData(IMapNodeComposite sender, IMapNodeComposite parent, IMapNode child) {
                super(sender);
                this.parent = parent;
                this.child = child;
            }

            public IMapNodeComposite getParent() {
                return parent;
            }

            public IMapNode getChild() {
                return child;
            }
        }
    }
}