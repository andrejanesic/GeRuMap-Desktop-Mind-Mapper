package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.states;

import rs.edu.raf.dsw.rudok.app.gui.swing.command.CommandManagerFactory;
import rs.edu.raf.dsw.rudok.app.gui.swing.command.standard.RemoveElementCommand;
import rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.IState;
import rs.edu.raf.dsw.rudok.app.repository.MindMap;
import rs.edu.raf.dsw.rudok.app.repository.Topic;

public class StateDeleteElement extends IState {

    @Override
    public void migrate(MindMap parent, Topic... topics) {
        CommandManagerFactory.getCommandManager(parent)
                .addCommand(new RemoveElementCommand(parent, topics));
    }
}
