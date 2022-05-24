package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;
import com.example.tour_planner.utils.db.tourLogDb.tourLogDbHandlerImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class TourOverviewViewModel {

    tourDbHandlerImpl handler = new tourDbHandlerImpl();
    tourLogDbHandlerImpl handlerLog = new tourLogDbHandlerImpl();

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

    public ObservableList<TourLogImpl> getTourLogList(ListView myListView) {
        ObservableList<TourLogImpl> list =  FXCollections.observableArrayList();
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        list = handlerLog.getTourLogs(selectedTour.toString());
        System.out.println(list);
        return list;
    }

    public void deleteTourLog(TableView<TourLogImpl> tableView) {
        TourLogImpl tourLog = tableView.getSelectionModel().getSelectedItem();
        tourLog.deleteTourLog();
    }
}
