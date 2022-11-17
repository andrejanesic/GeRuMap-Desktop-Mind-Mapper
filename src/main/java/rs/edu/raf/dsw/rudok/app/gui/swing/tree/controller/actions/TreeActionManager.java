package rs.edu.raf.dsw.rudok.app.gui.swing.tree.controller.actions;

public class TreeActionManager implements ITreeActionManager {

    private TreeNewAction treeNewAction;
    private TreeDeleteAction treeDeleteAction;

    public TreeActionManager() {
        initActions();
    }

    public void initActions() {
        treeNewAction = new TreeNewAction();
        treeDeleteAction = new TreeDeleteAction();
    }

    @Override
    public TreeNewAction getTreeNewAction() {
        return treeNewAction;
    }

    @Override
    public TreeDeleteAction getTreeDeleteAction() {
        return treeDeleteAction;
    }
}
