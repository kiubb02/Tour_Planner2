package com.example.tour_planner.layers.model;

public interface TourLog {
    void createLog();
    void deleteTourLog();
    void modifyLog(TourLogImpl log, String oldTitle);
}
