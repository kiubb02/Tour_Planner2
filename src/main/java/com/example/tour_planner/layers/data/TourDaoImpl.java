// we r in Data Layer ==> access database here
package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;

public class TourDaoImpl implements TourDao {

    private tourDbHandlerImpl handler = new tourDbHandlerImpl();

    @Override
    public void createTour(Tour tour) {

    }

    @Override
    public void deleteTour(String tour_name) {

    }

    @Override
    public void modifyTour(String old_name, Tour tour) {

    }
}
