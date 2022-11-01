package rs.edu.raf.dsw.rudok.app.gui.swing.view.tree.model;

import rs.edu.raf.dsw.rudok.app.repository.IMapNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class MapTreeItem extends DefaultMutableTreeNode {

    private IMapNode iMapNode;
    private String name;

    public MapTreeItem(IMapNode iMapNode) {
        this.iMapNode = iMapNode;
    }

    public IMapNode getiMapNode() {
        return iMapNode;
    }

    public void setiMapNode(IMapNode iMapNode) {
        this.iMapNode = iMapNode;
    }

    public void setName(String name) {
        this.name = name;
    }
    //TODO razresiti name konvenciju
}
