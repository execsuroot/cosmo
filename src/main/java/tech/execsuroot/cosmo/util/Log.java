package tech.execsuroot.cosmo.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging.
 */
public class Log {

    @Setter
    @Getter
    private static Logger logger;

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void debug(String message, Throwable throwable) {
        logger.debug(message, throwable);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, Throwable throwable) {
        logger.info(message, throwable);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void warn(String message, Throwable throwable) {
        logger.warn(message, throwable);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}