package rs.edu.raf.dsw.rudok.app.core.view;

import rs.edu.raf.dsw.rudok.app.core.view.controller.ActionManager;
import rs.edu.raf.dsw.rudok.app.core.view.listeners.MyWindowListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static MainFrame instance = null;
    private MyMenuBar menu;
    private Toolbar toolbar;
    private ActionManager actionManager;


    private MainFrame() {
    }

    private void initialise() {
        actionManager = new ActionManager();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.setSize( width / 2,height / 2);

        this.setTitle("GeRuMap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.addWindowListener(new MyWindowListener());


        JPanel projectTree = new JPanel();
        JScrollPane treeScroll = new JScrollPane(projectTree,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel workspace = new JPanel();
        workspace.setMinimumSize(new Dimension(600,300));
        treeScroll.setMinimumSize(new Dimension(70,300));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treeScroll,workspace);
        this.add(splitPane);


        menu = new MyMenuBar();
        toolbar = new Toolbar();
        this.setJMenuBar(menu);
        this.add(toolbar,BorderLayout.NORTH);


    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.initialise();

        }
        return instance;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }
}




