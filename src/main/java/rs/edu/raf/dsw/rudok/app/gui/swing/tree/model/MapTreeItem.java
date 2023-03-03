package rs.edu.raf.dsw.rudok.app.gui.swing.tree.model;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Wrapper for node items.
 */
public class MapTreeItem extends DefaultMutableTreeNode {

    private IMapNode mapNode;

    public MapTreeItem(IMapNode mapNode) {
        this.mapNode = mapNode;
        MapTreeItemObserver observer = new MapTreeItemObserver(this);
        this.mapNode.addObserver(observer);
    }

    public IMapNode getMapNode() {
        return mapNode;
    }

    public void setMapNode(IMapNode mapNode) {
        this.mapNode = mapNode;
    }

    public void setName(String name) {
        this.mapNode.setNodeName(name);
    }

    @Override
    public String toString() {
        return mapNode.getNodeName();
    }

    /**
     * Observer sub-class for the {@link MapTreeItem}.
     */
    private static class MapTreeItemObserver extends IObserver {

        private final MapTreeItem host;

        public MapTreeItemObserver(MapTreeItem host) {
            this.host = host;
        }

        @Override
        public void receive(Object message) {
            super.receive(message);

            // If message from IMapNode
            if (message instanceof IMapNode.Message) {
                switch (((IMapNode.Message) message).getStatus()) {

                    case EDITED: {
                        MainFrame.getInstance().getMapTree().refreshTree();
                        break;
                    }

                    case PARENT_ADDED:
                    case PARENT_REMOVED:
                    default: {
                        // support for multi-parent nodes doable, but not implemented
                        break;
                    }
                }
                return;
            }

            // If message from IMapNodeComposite
            if (message instanceof IMapNodeComposite.Message) {
                switch (((IMapNodeComposite.Message) message).getStatus()) {

                    // If child added, refresh tree and expand this node
                    case CHILD_ADDED: {

                        // If not sent by child of this node, abort
                        IMapNodeComposite.Message.ChildChangeMessageData data =
                                (IMapNodeComposite.Message.ChildChangeMessageData)
                                        ((IMapNodeComposite.Message) message).getData();
                        if (data.getParent() != host.mapNode) return;

                        MapTreeItem itm = addRecursively(host, data.getChild());
                        MainFrame.getInstance().getMapTree().refreshTree();
                        MainFrame.getInstance().getMapTree().expandTree(itm);
                        break;
                    }

                    // If child removed,
                    case CHILD_REMOVED: {

                        // If not sent by child of this node, abort
                        IMapNodeComposite.Message.ChildChangeMessageData data =
                                (IMapNodeComposite.Message.ChildChangeMessageData)
                                        ((IMapNodeComposite.Message) message).getData();
                        if (data.getParent() != host.mapNode) return;

                        MapTreeItem last = null;
                        MapTreeItem child = null;
                        MapTreeItem t = null;
                        Iterator<TreeNode> iterator = host.children.iterator();
                        while (iterator.hasNext()) {
                            last = t;
                            t = (MapTreeItem) iterator.next();
                            if (t.mapNode == data.getChild()) {
                                child = t;
                                break;
                            }
                        }
                        if (child == null) {
                            // This should never happen. Structure of both MapTreeItems and IMapNode should be the same
                            AppCore.getInstance().getMessageGenerator().warning(
                                    "MapTreeItem's IMapNode detected removal of children, but no child with removed IMapNode child detected in MapTreeItem"
                            );
                        }
                        // Edge-case where the child is the first element and possibly the last
                        if (last == null) {
                            if (iterator.hasNext()) {
                                last = (MapTreeItem) iterator.next();
                            }
                        }
                        host.remove(child);
                        MainFrame.getInstance().getMapTree().refreshTree();
                        MainFrame.getInstance().getMapTree().expandTree(last == null ? host : last);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            }
        }

        /**
         * Checks whether the passed {@link MapTreeItem} node contains a {@link IMapNode}.
         *
         * @param parent {@link MapTreeItem} parent node.
         * @param node   {@link IMapNode} child node.
         * @return Found MapTreeItem node, or null.
         */
        private MapTreeItem containsNode(MapTreeItem parent, IMapNode node) {
            Enumeration<TreeNode> it = parent.children();
            while (it.hasMoreElements()) {
                TreeNode tn = it.nextElement();
                if (((MapTreeItem) tn).mapNode.equals(node)) {
                    return ((MapTreeItem) tn);
                }
            }
            return null;
        }

        /**
         * Add children recursively.
         *
         * @param parent Tree node.
         * @param node   Repository node.
         */
        private MapTreeItem addRecursively(MapTreeItem parent, IMapNode node) {
            MapTreeItem subParent = containsNode(parent, node);
            if (subParent == null) {
                subParent = new MapTreeItem(node);
                parent.add(subParent);
            }
            if (node instanceof IMapNodeComposite) {
                IMapNodeComposite root = (IMapNodeComposite) node;
                Iterator<IMapNode> it = root.getChildren().iterator();
                while (it.hasNext()) {
                    addRecursively(subParent, it.next());
                }
            }
            return subParent;
        }
    }
}
