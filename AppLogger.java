
// Java program to illustrate logging in Java 
// The following code shows a basic example how logging  
// works in Java 
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

class AppLogger {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Get the Logger from the log manager which corresponds
    // to the given name <Logger.GLOBAL_LOGGER_NAME here>
    // static so that it is linked to the class and not to
    // a particular log instance because Log Manage is universal
    public void makeSomeLog() {
        // add some code of your choice here
        // Moving to the logging part now
        LOGGER.log(Level.INFO, "My first Log Message");

        // A log of INFO level with the message "My First Log Message"
    }
}