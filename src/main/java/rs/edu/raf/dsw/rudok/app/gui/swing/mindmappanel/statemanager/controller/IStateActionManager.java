package rs.edu.raf.dsw.rudok.app.gui.swing.mindmappanel.statemanager.controller;

public interface IStateActionManager {

    IStateAction getStartAddTopicStateAction();

    IStateAction getStartDeleteElementStateAction();

    IStateAction getStartDrawConnectionStateAction();

    IStateAction getStartMoveTopicStateAction();

    IStateAction getStartSelectTopicStateAction();

}
