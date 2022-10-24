package rs.edu.raf.dsw.rudok.app.repository;

import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Composite design pattern unit. Cannot have any children.
 */
public abstract class IMapNode extends IPublisher {

    /**
     * Set of parents.
     */
    public Set<IMapNodeComposite> parents = new HashSet<>();

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
        this.publish(new Message(Message.Type.PARENT_ADDED, this));
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
        this.publish(new Message(Message.Type.PARENT_REMOVED, this));
    }

    /**
     * Message for publishing.
     */
    public static class Message extends IMessage<IMapNode.Message.Type, IMapNode> {

        public Message(Type status, IMapNode data) {
            super(status, data);
        }

        /**
         * Types of message.
         */
        public enum Type {
            // When a parent is added
            PARENT_ADDED,
            // When a parent is removed
            PARENT_REMOVED,
        }
    }
}