package rs.edu.raf.dsw.rudok.app.gui.swing.tree.model;

import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.observer.IObserver;
import rs.edu.raf.dsw.rudok.app.repository.IMapNode;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
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

                        MapTreeItem itm = new MapTreeItem(data.getChild());
                        host.add(itm);
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

                        MapTreeItem child = null;
                        Iterator<TreeNode> iterator = host.children.iterator();
                        while (iterator.hasNext()) {
                            MapTreeItem itm = (MapTreeItem) iterator.next();
                            if (itm.mapNode == data.getChild()) {
                                child = itm;
                            }
                        }
                        if (child == null) {
                            // TODO log error, this should never happen. Structure of both MapTreeItems and IMapNode should be the same
                            throw new RuntimeException(
                                    "MapTreeItem's IMapNode detected removal of children, but no child with removed IMapNode child detected in MapTreeItem"
                            );
                        }
                        host.remove(child);
                        MainFrame.getInstance().getMapTree().refreshTree();
                        break;
                    }

                    default: {
                        break;
                    }
                }
            }
        }
    }
}
