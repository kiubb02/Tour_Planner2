package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.Tour;

import java.util.ArrayList;

public interface TourDao {
    void createTour(Tour tour);
    void deleteTour(String name);
    void modifyTour(String oldName, Tour tour);
    Tour getDetails(String name);
    ArrayList<Tour> getTourList();
    ArrayList<Tour> searchTour(String search);
    int getSumRating(String title);
    int getSumDifficulty(String title);
    int getTourStrikes(String title);
    void reportTour(String title);
}
