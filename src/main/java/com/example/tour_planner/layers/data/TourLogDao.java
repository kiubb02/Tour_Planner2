package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface TourLogDao {
    // --- TOUR LOG MODIFICATION ---
    int createTourLog(TourLogImpl tour);
    int deleteTourLog(TourLogImpl tour);
    int modifyTourLog(TourLogImpl tour, String oldTitle);
    // TOUR LOG DETAILS
    ObservableList<TourLogImpl> getTourLogs(String tourName);
    boolean titleExist(String title);
    float getAvgTime(String tour);
    float getAvgRating(String tour);
}
