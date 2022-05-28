package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TourOverviewViewModel {

    TourDaoImpl handler = new TourDaoImpl();

    @FXML @Getter
    public ListProperty<Tour> listProperty = new SimpleListProperty<>();
    public ArrayList<Tour> tourList;

    @FXML
    void open(){
        tourList = getTourList("");
        if(tourList != null) listProperty.set(FXCollections.observableArrayList(tourList));
    }

    public void doSearch(String search){
        tourList = getTourList(search);
        if(tourList != null) listProperty.set(FXCollections.observableArrayList(tourList));
    }

    public Tour getDetails(ListView myListView) {
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        Tour details = handler.getDetails(selectedTour.toString());
        return details;
    }

    public Object deleteTour(ListView myListView) {
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        handler.deleteTour(selectedTour.toString());
        return selectedTour;
    }

    public ArrayList<Tour> getTourList(String search) {
        ArrayList<Tour> tourList = new ArrayList<>();
        if(search.equals("")) return handler.getTourList();
        if(!search.equals("")) return handler.searchTour(search);
        return tourList;
    }
}