package rs.edu.raf.dsw.rudok.app.constants.standard;

import rs.edu.raf.dsw.rudok.app.constants.IConstants;

public class StandardConstants implements IConstants {

    @Override
    public String MINDMAP_TEMPLATES_DIR() {
        return System.getProperty("user.home") + "/GeRuMap/templates/";
    }

    @Override
    public String FILESYSTEM_LOCAL_CONFIG_FOLDER() {
        return System.getProperty("user.home") + "/GeRuMap/config/";
    }

    @Override
    public String FILESYSTEM_LOCAL_PROJECTS_FOLDER() {
        return "";
    }

    @Override
    public String FILESYSTEM_LOCAL_LOGS_FOLDER() {
        return System.getProperty("user.home") + "/GeRuMap/logs/";
    }
}
