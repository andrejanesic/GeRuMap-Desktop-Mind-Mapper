package rs.edu.raf.dsw.rudok.app.repository;

import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Composite design pattern unit. Cannot have any children. Can have multiple {@link IMapNodeComposite} parents.
 */
public abstract class IMapNode extends IPublisher {

    /**
     * Set of parents.
     */
    public Set<IMapNodeComposite> parents = new HashSet<>();

    private String nodeName;

    public IMapNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public Set<IMapNodeComposite> getParents() {
        return parents;
    }

    public void setParents(Set<IMapNodeComposite> parents) {
        this.parents = new HashSet<>();
        Iterator<IMapNodeComposite> iterator = parents.iterator();
        while (iterator.hasNext()) {
            addParent(iterator.next());
        }
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
        this.publish(new IMapNode.Message(
                Message.Type.EDITED,
                new Message.EditedMessageData(
                        this,
                        "nodeName",
                        nodeName
                )
        ));
    }

    @Override
    public String toString() {
        return nodeName;
    }

    /**
     * Adds a parent to the node.
     *
     * @param parent Node to add as parent.
     */
    public void addParent(IMapNodeComposite parent) {
        if (parent == null) return;
        if (this.parents.contains(parent)) return;
        this.parents.add(parent);

        this.addObserver(parent);
        this.publish(new Message(Message.Type.PARENT_ADDED, new Message.ParentChangeMessageData(this, this, parent)));
    }

    /**
     * Removes a parent from the node.
     *
     * @param parent Node to remove from parents.
     */
    public void removeParent(IMapNodeComposite parent) {
        if (parent == null) return;
        if (!this.parents.contains(parent)) return;
        this.parents.remove(parent);

        this.removeObserver(parent);
        this.publish(new Message(Message.Type.PARENT_REMOVED, new Message.ParentChangeMessageData(this, this, parent)));
    }


    @Override
    public void receive(Object message) {

        if (message instanceof IMapNodeComposite.Message) {

            switch (((IMapNodeComposite.Message) message).getStatus()) {

                case CHILD_ADDED:
                case CHILD_REMOVED: {
                    IMapNodeComposite.Message.ChildChangeMessageData data = (IMapNodeComposite.Message.ChildChangeMessageData)
                            ((IMapNodeComposite.Message) message).getData();
                    if (data.getChild().equals(this)) {
                        if (((IMapNodeComposite.Message) message).getStatus().equals(IMapNodeComposite.Message.Type.CHILD_ADDED)) {
                            this.addParent(data.getParent());
                        } else {
                            this.removeParent(data.getParent());
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Message for publishing.
     */
    public static class Message extends IMessage<IMapNode.Message.Type, IMessageData> {

        public Message(Type status, IMessageData data) {
            super(status, data);
        }

        /**
         * Types of message.
         */
        public enum Type {
            // When some value of a node is edited TODO this will be overwritten once we have Projects, Maps, etc.
            EDITED,
            // When a parent is added
            PARENT_ADDED,
            // When a parent is removed
            PARENT_REMOVED,
        }

        /**
         * For messages about changed values of the node.
         */
        public static class EditedMessageData extends IMessageData<IMapNode> {

            private final String key;
            private final Object value;

            public EditedMessageData(IMapNode mapNode, String key, Object value) {
                super(mapNode);
                this.key = key;
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public Object getValue() {
                return value;
            }
        }

        public static class ParentChangeMessageData extends IMessageData<IMapNode> {

            private final IMapNode child;
            private final IMapNodeComposite parent;

            public ParentChangeMessageData(IMapNode sender, IMapNode child, IMapNodeComposite parent) {
                super(sender);
                this.child = child;
                this.parent = parent;
            }

            public IMapNode getChild() {
                return child;
            }

            public IMapNodeComposite getParent() {
                return parent;
            }
        }
    }
}