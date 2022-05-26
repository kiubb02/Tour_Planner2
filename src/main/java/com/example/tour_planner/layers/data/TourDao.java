package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.Tour;

import java.util.ArrayList;

public interface TourDao {
    void createTour(Tour tour);
    void deleteTour(String name);
    void modifyTour(String oldName, Tour tour);
    // --- TOUR DETAILS --- //
    ArrayList<Tour> getTour(Tour tour);
    // -- new tour details function -- //
    Tour getDetails(String name);
    // Get list of tour
    ArrayList<Tour> getTourList();
    // Get latest tour
    Object latestTour();
    int getSumRating(String title);
    int getSumDifficulty(String title);
    int getTourStrikes(String title);
    void reportTour(String title);
}
