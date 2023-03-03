package rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class StudentInfoDialog extends JDialog {
    public StudentInfoDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.setSize(350,200);

        this.setLocationRelativeTo(owner);

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel studentInfoLb = new JLabel("Students who worked on this project:");
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        header.add(studentInfoLb);

        JPanel student1Pan = new JPanel(new FlowLayout(FlowLayout.TRAILING,10,10));
        JLabel student1Lb = new JLabel("Aleksa Vučinić 60/20RN");
        student1Pan.add(student1Lb);

        JPanel student2Pan = new JPanel(new FlowLayout(FlowLayout.TRAILING,10,10));
        JLabel student2Lb = new JLabel("Andreja Nešić 31/19RN");
        student2Pan.add(student2Lb);

        JPanel display = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        display.add(student1Pan);
        display.add(student2Pan);

        mainPanel.add(header);
        mainPanel.add(display);
        this.add(mainPanel);

    }

}
