package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.model.TourLog;
import com.example.tour_planner.layers.model.TourLogImpl;

import java.util.ArrayList;
import java.util.Date;

public interface TourLogService {
    String errorMessage(ArrayList input);
    ArrayList validateInput(ArrayList inputs);
    int validateTitle(String title, String oldTitle);
    int validateDate(Date date);
    int validateTime(Float time);
    ArrayList<Float> summarizeTourLogs(String tour);
}
