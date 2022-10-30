package rs.edu.raf.dsw.rudok.app.core.view;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar() {
        JMenu File = new JMenu("File");
        JMenuItem New = new JMenuItem("New");
        JMenu Help = new JMenu("Help");
        JMenuItem Edit = new JMenuItem("Edit");

        File.add(New);
        Edit.add(Help);

        this.add(File);
        this.add(Edit);
    }
}
