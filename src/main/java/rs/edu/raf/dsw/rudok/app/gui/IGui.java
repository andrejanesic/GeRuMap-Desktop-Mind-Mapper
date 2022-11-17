package rs.edu.raf.dsw.rudok.app.gui;

import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.observer.IPublisher;

/**
 * GUI component specification.
 */
public abstract class IGui extends IPublisher {

    /**
     * Runs the GUI.
     */
    public abstract void run();

    public abstract void showDialog(IMessageGenerator.Message message);

    @Override
    public void receive(Object message) {
        super.receive(message);

        if (message instanceof IMessageGenerator.Message) {
            IMessageGenerator.Message m = (IMessageGenerator.Message) message;
            assert (m.getStatus().equals(IMessageGenerator.Message.Type.Message));
            showDialog(m);
        }

    }
}
