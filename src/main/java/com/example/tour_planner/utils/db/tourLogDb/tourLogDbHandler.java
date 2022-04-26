package com.example.tour_planner.utils.db.tourLogDb;

import com.example.tour_planner.model.TourLog;

import java.util.ArrayList;

public interface tourLogDbHandler
{
    // --- TOUR LOG MODIFICATION ---
    int createTourLog(TourLog tour);
    int deleteTourLog(TourLog tour);
    int modifyTourLog(TourLog tour);

    // TOUR LOG DETAILS
    ArrayList<TourLog> getTourLog(TourLog tourlog);
}
