package rs.edu.raf.dsw.rudok.app.logger.console.file;

import rs.edu.raf.dsw.rudok.app.AppCore;
import rs.edu.raf.dsw.rudok.app.logger.console.ConsoleLogger;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;

/**
 * <h1>File logger</h1>
 * Logs to the console and to system file.
 */
public class FileLogger extends ConsoleLogger {

    @Override
    public void log(String content, IMessageGenerator.Type type, String timestamp) {
        super.log(content, type, timestamp);
        AppCore.getInstance().getFileSystem().log(super.getLine());
    }
}
