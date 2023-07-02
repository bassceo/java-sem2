package common.io;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorHandler {
    private final static Logger logger = Logger.getLogger(ErrorHandler.class.getName());

    public static void logError(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }
}