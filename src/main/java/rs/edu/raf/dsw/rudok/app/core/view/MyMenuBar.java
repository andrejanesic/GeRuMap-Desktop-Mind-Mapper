package rs.edu.raf.dsw.rudok.app.core.view;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar() {
        JMenu File = new JMenu("File");
        JMenuItem New = new JMenuItem("New");
        JMenu Help = new JMenu("Help");
        JMenuItem Edit = new JMenuItem("Edit");

        File.add(New);
        Help.add(Edit);

        this.add(File);
        this.add(Help);
    }
}
