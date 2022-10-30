package rs.edu.raf.dsw.rudok.app.core.view.dialogs;

import javax.swing.*;
import java.awt.*;

public class StudentInfoDialog extends JDialog {
    public StudentInfoDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        this.setSize(200,200);
        this.setLocationRelativeTo(owner);
        JLabel studentInfoLb = new JLabel("Students who worked on this project:/nAleksa Vučinić 60/20 RN/nAndreja Nešić 31/19 RN");
        this.add(studentInfoLb);
    }

}
