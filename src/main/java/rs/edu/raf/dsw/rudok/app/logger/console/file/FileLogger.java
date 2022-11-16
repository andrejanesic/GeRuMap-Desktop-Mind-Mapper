package rs.edu.raf.dsw.rudok.app.logger.console.file;

import rs.edu.raf.dsw.rudok.app.logger.console.ConsoleLogger;
import rs.edu.raf.dsw.rudok.app.messagegenerator.IMessageGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;

/**
 * <h1>File logger</h1>
 * Logs to the console and to system file.
 */
public class FileLogger extends ConsoleLogger {

    @Override
    public void log(String content, IMessageGenerator.Type type, String timestamp) {
        super.log(content, type, timestamp);
        try{
            FileWriter fileWriter = new FileWriter("/logfile.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content + type + timestamp);
            bufferedWriter.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
