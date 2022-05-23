package com.example.tour_planner.viewmodel;

import com.example.tour_planner.model.Tour;
import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class TourOverviewViewModel {

    tourDbHandlerImpl handler = new tourDbHandlerImpl();

    public Tour getDetails(ListView myListView) {
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        Tour details = handler.getDetails(selectedTour.toString());

        return details;
    }


    public Object deleteTour(ListView myListView) {
        // get selected tour
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        // delete selected tour
        handler.deleteTour(selectedTour.toString());
        return selectedTour;
    }

    public Object addTour() {
        Object tour = handler.latestTour();
        return tour;
    }

    public ArrayList<Tour> getTourList() {
        ArrayList<Tour> tourList = handler.getTourList();
        return tourList;
    }
}
