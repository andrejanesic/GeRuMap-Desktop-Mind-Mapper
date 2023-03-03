package rs.edu.raf.dsw.rudok.app.gui.swing.controller.actions;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.confighandler.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.gui.swing.view.dialogs.ConfigDialog;

import java.awt.event.ActionEvent;

public class OpenPreferencesAction extends IAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        exec();
    }

    private void exec() {

        ConfigDialog cd = new ConfigDialog();
        cd.setVisible(true);
        IConfigHandler ref = AppCore.getInstance().getConfigHandler();
        if (cd.getResult() == null) return;
        switch (cd.getResult()) {

            case CONFIG_LOADED: {
                ref.loadConfig(cd.getLoadedConfig());
                break;
            }

            case CONFIG_SAVED: {
                String name = cd.getName();
                String lang = cd.getLang();
                String autosave = cd.getAutosave() ? "true" : "false";
                ref.set("config", name);
                ref.set("language", lang);
                ref.set("autosave", autosave);
                ref.saveConfig();
                AppCore.getInstance().getConfigHandler().saveConfig();
                break;
            }

            case CONFIG_NEW: {
                ref.resetConfig();
                exec();
                break;
            }
        }
    }
}
