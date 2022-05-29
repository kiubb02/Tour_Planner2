package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
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

    private final ListProperty<Tour> listProperty = new SimpleListProperty<>();
    private Property<ObservableList<Tour>> tourList = new SimpleListProperty<>();
    private ObservableList<Tour> obsData = FXCollections.observableArrayList();


    public void setTourList(String s) {
        listProperty.bindBidirectional(tourList);
        obsData = getTourList(s);
        tourList.setValue(obsData);
    }

    public ListProperty<Tour> tourListProperty(){ return listProperty; }

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

    public ObservableList<Tour> getTourList(String search) {
        ObservableList<Tour> tourList = FXCollections.observableArrayList();
        if(search.equals("")) return handler.getTourList();
        if(!search.equals("")) return handler.searchTour(search);
        return tourList;
    }

}