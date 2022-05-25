package com.example.tour_planner.utils.logger;

public interface LoggerWrapper {
    void debug(String message);
    void fatal(String message);
    void error(String message);
    void warn(String message);
}