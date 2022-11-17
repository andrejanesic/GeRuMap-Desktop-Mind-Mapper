package rs.edu.raf.dsw.rudok.app.gui.swing.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar {
    public Toolbar() {
        JButton btnNew = new JButton();
        JButton btnDelete = new JButton();

        this.add(btnNew);
        this.add(btnDelete);

        this.setFloatable(false);

        try {
            Image plusImg = ImageIO.read(getClass().getResource("/images/plus.png"));
            Image delImg = ImageIO.read(getClass().getResource("/images/iconmonstr-trash-can-lined-24.png"));
            btnNew.setIcon(new ImageIcon(plusImg));
            btnDelete.setIcon(new ImageIcon(delImg));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        btnNew.addActionListener(MainFrame.getInstance().getActionManager().getNewAction());
        btnDelete.addActionListener(MainFrame.getInstance().getActionManager().getDeleteAction());
    }
}
