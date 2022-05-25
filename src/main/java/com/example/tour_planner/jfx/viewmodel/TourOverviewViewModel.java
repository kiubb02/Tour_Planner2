package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class TourOverviewViewModel {

    TourDaoImpl handler = new TourDaoImpl();
    TourLogDaoImpl handlerLog = new TourLogDaoImpl();

    public Tour getDetails(ListView myListView) {
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        Tour details = handler.getDetails(selectedTour.toString());
        return details;
    }

    public TourLogImpl getTourLog(TableView<TourLogImpl> tableView){
        // get selected item
        return tableView.getSelectionModel().getSelectedItem();
    }

    public Object deleteTour(ListView myListView) {
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
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
        return list;
    }

    public void deleteTourLog(TableView<TourLogImpl> tableView) {
        TourLogImpl tourLog = tableView.getSelectionModel().getSelectedItem();
        tourLog.deleteTourLog();
    }

    public Object getTour(ListView myListView) {
        return myListView.getSelectionModel().getSelectedItem();
    }
}