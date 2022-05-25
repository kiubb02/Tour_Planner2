package com.example.tour_planner.utils.logger;

public class LoggerFactory {
    public static LoggerWrapper getLogger() {
        var logger = new Log4J2Wrapper();
        logger.initialize();
        return logger;
    }
}
