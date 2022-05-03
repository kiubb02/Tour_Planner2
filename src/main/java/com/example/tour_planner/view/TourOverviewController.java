package com.example.tour_planner.view;


import com.example.tour_planner.model.Tour;
import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import com.example.tour_planner.utils.windows.TourForm;
import javafx.scene.control.SelectionMode;

import java.util.ArrayList;


public class TourOverviewController {
    @FXML
    public ArrayList<Tour> tourList;
    @FXML
    private ListView myListView;
    protected ListProperty<Tour> listProperty = new SimpleListProperty<>();

    private final TourOverviewViewModel mediaOverviewViewModel;

    public TourOverviewController(TourOverviewViewModel mediaOverviewViewModel) {
        this.mediaOverviewViewModel = mediaOverviewViewModel;
    }

    public TourOverviewViewModel getMediaOverviewViewModel() {
        return mediaOverviewViewModel;
    }

    @FXML
    void initialize() {
        // get the new Items and show them in there
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        tourList = handler.getTourList();
        myListView.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(tourList));
        myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void onButtonAdd(ActionEvent actionEvent) {
        TourForm form = new TourForm();
        //show new window
        form.showForm();
        //add element to listview
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        // get selected tour
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        // delete selected tour
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        handler.deleteTour(selectedTour.toString());
        //delete element from list view

    }
}
