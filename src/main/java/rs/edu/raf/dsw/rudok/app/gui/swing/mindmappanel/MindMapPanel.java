package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel;

import rs.edu.raf.dsw.rudok.app.repository.MindMap;

import javax.swing.*;
import java.awt.*;

public class MindMapPanel extends JPanel implements IMindMapPanel {

    private final MindMap mindMap;

    public MindMapPanel(MindMap mindMap) {
        super(new BorderLayout());
        this.mindMap = mindMap;
    }
}
