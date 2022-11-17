package rs.edu.raf.dsw.rudok.app.logger.console;

import rs.edu.raf.dsw.rudok.app.logger.ILogger;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;

/**
 * <h1>Console logger</h1>
 * Logs to the console.
 */
public class ConsoleLogger extends ILogger {

    private String lastLine = null;

    @Override
    public void log(String content, IMessageGenerator.Type type, String timestamp) {
        String typeStr = "[" + type.name() + "]";
        String timestampStr = "[" + timestamp + "]";
        lastLine = typeStr + timestampStr + " " + content;
        System.out.println(lastLine);
    }

    /**
     * Returns the last logged line.
     *
     * @return Last logged line.
     */
    public String getLine() {
        return lastLine;
    }
}
