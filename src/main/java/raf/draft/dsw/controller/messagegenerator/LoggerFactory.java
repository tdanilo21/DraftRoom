package raf.draft.dsw.controller.messagegenerator;

public class LoggerFactory {

    public static final int CONSOLE = 0;
    public static final int FILE = 1;
    public static Logger createLogger(int type){
        return switch (type){
            case CONSOLE -> new ConsoleLogger();
            case FILE -> new FileLogger();
            default -> null;
        };
    }
}
