package com.example.tour_planner.utils.db.tourDb;
import com.example.tour_planner.model.Tour;
import javafx.scene.control.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface tourDbHandler {
    // --- TOUR MODIFICATION --- //
    int createTour(Tour tour);
    int deleteTour(Tour tour);
    int modifyTour(Tour tour);

    // --- TOUR DETAILS --- //
    ArrayList<Tour> getTour(Tour tour);


    // Get list of tour
    ArrayList<Tour> getTourList();
}
