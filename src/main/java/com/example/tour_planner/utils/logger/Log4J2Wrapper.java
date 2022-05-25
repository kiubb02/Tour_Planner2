package com.example.tour_planner.utils.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Wraps the log4j2 logger instances by realizing interface LoggerWrapper
// This avoids direct dependencies to log4j2 package
public class Log4J2Wrapper implements LoggerWrapper {

    private Logger logger;
    private LoggerStateBase state = new UninitializedState();

    @Override
    public void debug(String message) {
        this.state.debug(message);
    }
    @Override
    public void fatal(String message) {
        this.state.fatal(message);
    }
    @Override
    public void error(String message) {
        this.state.error(message);
    }
    @Override
    public void warn(String message) {
        this.state.warn(message);
    }

    public void initialize() {
        this.state = new InitializedState(LogManager.getLogger(this.getClass().getName()));
    }
}