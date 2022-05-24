// business layer for the tour component

package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.Tour;

public interface TourDao {
    void createTour(Tour tour);
    void deleteTour(String tour_name);
    void modifyTour(String old_name, Tour tour);


}
