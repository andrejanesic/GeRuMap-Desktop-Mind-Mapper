package rs.edu.raf.dsw.rudok.app.gui.swing;

import rs.edu.raf.dsw.rudok.app.gui.IGui;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.MainFrame;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.messagegenerator.standard.StandardMessageGenerator;
import rs.edu.raf.dsw.rudok.app.observer.IMessage;
import rs.edu.raf.dsw.rudok.app.observer.IMessageData;

import javax.swing.*;

public class GuiSwing extends IGui {

    @Override
    public void run() {
        MainFrame.getInstance().setVisible(true);
    }

    @Override
    public void showDialog(IMessage message, String messageText) {
        if(message instanceof IMessageGenerator.Message){
          switch (((IMessageGenerator.Message.MessageData) message.getData()).getType()) {
              case LOG:
                  break;
              case ERROR:
                  JOptionPane.showMessageDialog(MainFrame.getInstance(), messageText,"Error",JOptionPane.ERROR_MESSAGE);
                  break;
              case WARNING:
                  JOptionPane.showMessageDialog(MainFrame.getInstance(), messageText,"Warning",JOptionPane.WARNING_MESSAGE);
                  break;
          }


        }
    }
}
