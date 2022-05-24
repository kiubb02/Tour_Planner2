package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface TourLogDao {
    // --- TOUR LOG MODIFICATION ---
    int createTourLog(TourLogImpl tour);
    int deleteTourLog(TourLogImpl tour);
    int modifyTourLog(TourLogImpl tour);
    // TOUR LOG DETAILS
    ArrayList<TourLogImpl> getTourLog(TourLogImpl tourlog);
    ObservableList<TourLogImpl> getTourLogs(String tourName);
}
