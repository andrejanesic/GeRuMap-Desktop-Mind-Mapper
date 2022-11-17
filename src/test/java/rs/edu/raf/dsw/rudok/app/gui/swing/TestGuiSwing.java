package rs.edu.raf.dsw.rudok.app.gui.swing;

import org.junit.After;
import org.junit.Test;
import rs.edu.raf.dsw.rudok.app.Helper;
import rs.edu.raf.dsw.rudok.app.confighandler.IConfigHandler;
import rs.edu.raf.dsw.rudok.app.constants.IConstants;
import rs.edu.raf.dsw.rudok.app.core.ApplicationFramework;
import rs.edu.raf.dsw.rudok.app.filesystem.IFileSystem;
import rs.edu.raf.dsw.rudok.app.gui.IGui;
import rs.edu.raf.dsw.rudok.app.logger.ILogger;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;
import rs.edu.raf.dsw.rudok.app.repository.Project;
import rs.edu.raf.dsw.rudok.app.repository.ProjectExplorer;

import java.util.Map;

public class TestGuiSwing {

    @Test
    public void testGuiSwing() {
        IGui gui = new GuiSwing();

        ApplicationFramework applicationFramework = new ApplicationFramework() {
        };

        applicationFramework.initialize(
                new IConstants() {
                    @Override
                    public String FILESYSTEM_LOCAL_CONFIG_FOLDER() {
                        return null;
                    }

                    @Override
                    public String FILESYSTEM_LOCAL_PROJECTS_FOLDER() {
                        return null;
                    }
                },
                new IMessageGenerator() {
                    @Override
                    public void send(String content, Type type, String timestamp) {

                    }
                },
                new IConfigHandler() {
                    @Override
                    public boolean loadConfig(String name) {
                        return false;
                    }

                    @Override
                    public void saveConfig() {

                    }

                    @Override
                    public void resetConfig() {

                    }

                    @Override
                    public void createConfig(String name) {

                    }

                    @Override
                    public void set(String key, String val) {

                    }

                    @Override
                    public Object get(String key) {
                        return null;
                    }

                    @Override
                    public Object get(String key, Object defaultValue) {
                        return null;
                    }
                },
                gui,
                new ProjectExplorer(Helper.randString()) {
                },
                new IFileSystem()
                {
                    @Override
                    public void saveConfig(Map<String, String> config) {

                    }

                    @Override
                    public Map<String, String> loadConfig(String name) {
                        return null;
                    }

                    @Override
                    public void saveProject(Project project) {

                    }

                    @Override
                    public Project loadProject(String filepath) {
                        return null;
                    }

                    @Override
                    public boolean deleteProject(Project p) {
                        return false;
                    }

                    @Override
                    public void log(String content, IMessageGenerator.Type type, String timestamp) {

                    }
                },
                new ILogger() {
                    @Override
                    public void log(String content, IMessageGenerator.Type type, String timestamp) {

                    }
                });
    }

    @After
    public void tearDown() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
