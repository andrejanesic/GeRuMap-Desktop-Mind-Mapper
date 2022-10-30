package rs.edu.raf.dsw.rudok.app.core.view;

import rs.edu.raf.dsw.rudok.app.core.view.listeners.MyWindowListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static MainFrame instance = null;
    private MyMenuBar menu;
    private Toolbar toolbar;
    private JPanel panel;

    private MainFrame() {
    }

    private void initialise() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.setSize(height / 2, width / 2);

        this.setTitle("GeRuMap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.addWindowListener(new MyWindowListener());


        panel = new JPanel();
        this.add(panel);

        menu = new MyMenuBar();
        toolbar = new Toolbar();
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.initialise();

        }
        return instance;
    }

    public JPanel getPanel() {
        return panel;
    }
}




