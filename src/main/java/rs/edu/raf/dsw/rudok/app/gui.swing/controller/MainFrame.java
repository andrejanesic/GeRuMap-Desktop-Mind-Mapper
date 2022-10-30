package rs.edu.raf.dsw.rudok.app.gui.swing.controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private Menu menu;
    private Toolbar toolbar;

    private MainFrame(){
    }

    private void initialise(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        this.setSize(height/2,width/2);

        this.setTitle("GeRuMap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.addWindowListener(new MyWindowListener());

        menu = new Menu();
        toolbar = new Toolbar();
    }

    public static MainFrame getInstance(){
        if (instance == null){
            instance  = new MainFrame();
            instance.initialise();

        }
        return instance;
    }
}
