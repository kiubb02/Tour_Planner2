package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.model.Tour;

import java.util.ArrayList;
import java.util.Date;

public interface TourService {
    String errorMessage(ArrayList input);
    ArrayList validateInput(ArrayList inputs);
    int validateTitle(String title, String oldTitle);

    // for editing
    ArrayList validateInputEdit(ArrayList inputs, Tour details);

    int calulateChildfriendl();
    int calculatePopularity();
}
