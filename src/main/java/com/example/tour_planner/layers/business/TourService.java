package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.model.Tour;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface TourService {
    String errorMessage(ArrayList input);
    ArrayList validateInput(ArrayList inputs);
    int validateTitle(String title, String oldTitle);

    // for editing
    ArrayList validateInputEdit(ArrayList inputs, Tour details);

    int calulateChildfriendl(String title);
    int calculatePopularity(String title);
    void importTour(File file);
    void exportTour(Tour tour) throws FileNotFoundException;
    void createTour(JSONObject tours) throws IOException;
    File getFile(File file);
}
