package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.standard.AddElementCommand;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.nodefactory.ElementFactory;

public class StateAddTopic extends IState {

    @Override
    public void migrate(MindMap parent, int x1, int y1) {
        CommandManagerFactory.getCommandManager(parent)
                .addCommand(new AddElementCommand(
                        parent,
                        x1,
                        y1,
                        ElementFactory.TOPIC_DEFAULT_WIDTH, // TODO customization
                        ElementFactory.TOPIC_DEFAULT_HEIGHT  // TODO customization
                ));
    }
}
