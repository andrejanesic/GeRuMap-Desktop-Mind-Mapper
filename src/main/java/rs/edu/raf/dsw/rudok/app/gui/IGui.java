package rs.edu.raf.dsw.rudok.app.gui;
import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;
import rs.edu.raf.dsw.rudok.app.repository.IMapNodeComposite;

import javax.swing.*;

/**
 * GUI component specification.
 */
public abstract class IGui extends IPublisher {

    /**
     * Runs the GUI.
     */
    public abstract void run();

    public abstract void showDialog(IMessage message, String messageText);
    @Override
    public void receive(Object message) {
        super.receive(message);

        if (message instanceof IMessageGenerator.Message) {
            IMessageGenerator.Message m = (IMessageGenerator.Message) message;
            assert (m.getStatus().equals(IMessageGenerator.Message.Type.Message));
        }

    }
}
