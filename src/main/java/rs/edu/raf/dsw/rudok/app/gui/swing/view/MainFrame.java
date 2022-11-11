package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions.ActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions.IActionManager;
import rs.edu.raf.dsw.rudok.app.gui.swing.controller.listeners.WindowListener;
import rs.edu.raf.dsw.rudok.app.gui.swing.projectexplorerpanel.ProjectExplorerPanel;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.IMapTree;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.MapTree;
import rs.edu.raf.dsw.rudok.app.gui.swing.tree.view.MapTreeView;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import javax.swing.*;
import java.awt.*;

/**
 * TODO add documentation
 */
public class MainFrame extends JFrame {

    private static final ProjectExplorer projectExplorer = new ProjectExplorer("Project Explorer");
    private static MainFrame instance = null;
    private MainMenuBar menu;
    private Toolbar toolbar;
    private IActionManager actionManager;
    private IMapTree mapTree;
    private MapTreeView mapTreeView;
    private ProjectExplorerPanel projectExplorerPanel;

    private MainFrame() {
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.initialise();

        }
        return instance;
    }

    public ProjectExplorerPanel getProjectExplorerPanel() {
        return projectExplorerPanel;
    }

    public MainMenuBar getMenu() {
        return menu;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public IMapTree getMapTree() {
        return mapTree;
    }

    public MapTreeView getMapTreeView() {
        return mapTreeView;
    }

    private void initialise() {
        actionManager = new ActionManager();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.setSize(width / 2, height / 2);

        this.setTitle("GeRuMap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.addWindowListener(new WindowListener());

        mapTree = new MapTree();
        mapTreeView = mapTree.generateTree(projectExplorer);
        projectExplorerPanel = new ProjectExplorerPanel(projectExplorer);

        JScrollPane treeScroll = new JScrollPane(
                mapTreeView,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        JScrollPane projectExplorerScroll = new JScrollPane(
                projectExplorerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        projectExplorerScroll.setMinimumSize(new Dimension(600, 300));
        treeScroll.setMinimumSize(new Dimension(70, 300));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, projectExplorerScroll);
        this.add(splitPane);

        menu = new MainMenuBar();
        toolbar = new Toolbar();
        this.setJMenuBar(menu);
        this.add(toolbar, BorderLayout.NORTH);
    }

    public IActionManager getActionManager() {
        return actionManager;
    }
}




