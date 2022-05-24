package com.example.tour_planner.utils.db.tourDb;
import com.example.tour_planner.layers.model.Tour;

import java.util.ArrayList;

public interface tourDbHandler {
    // --- TOUR MODIFICATION --- //
    int createTour(Tour tour);
    int deleteTour(String name);
    int modifyTour(String oldName, Tour tour);

    // --- TOUR DETAILS --- //
    ArrayList<Tour> getTour(Tour tour);
    // -- new tour details function -- //
    Tour getDetails(String name);

    // Get list of tour
    ArrayList<Tour> getTourList();

    // Get latest tour
    Object latestTour();
}
