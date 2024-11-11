package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.model.messages.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class FileLogger extends Logger{
    @Override
    public void notify(Object state) {
        if(state instanceof Message message) {
            try {
                String path = STR."\{Paths.get("").toAbsolutePath()}\\src\\main\\resources\\log.txt";
                File file = new File(path);
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file,true);
                fileWriter.write(STR."\{formatMessage(message)}\n");
                fileWriter.close();
            } catch (IOException exception){
                System.err.println(exception.getMessage());
            }
        }
    }
}
