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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.util.ArrayList;


public class TourOverviewController {
    @FXML
    public ArrayList<Tour> tourList;
    public VBox TourDetails;
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
        //myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void onButtonAdd(ActionEvent actionEvent) {
        TourForm form = new TourForm();
        // show new window
        form.showForm();
        // TODO : add the object to the List View below
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        Object tour = handler.latestTour();
        myListView.getItems().add(tour);
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        // get selected tour
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        // delete selected tour
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        handler.deleteTour(selectedTour.toString());
        //delete element from list view
        myListView.getItems().remove(selectedTour);
    }

    // show tour details on click
    public void showTour(MouseEvent mouseEvent) {
        // get selected tour item
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        // Test if object is correct : Yes correct object is shown
        // System.out.println(selectedTour);
        // get details from the tour
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        JSONObject details = handler.getDetails(selectedTour.toString());
        // Test if object is correct : Object is correct
        // System.out.println(details.getString("name"));

        // add children to Vbox
        createTable(details);
        // add the image

    }

    public void createTable(JSONObject details){
        TableView table = new TableView();
        TableColumn firstCol = new TableColumn("-");
        TableColumn secondCol = new TableColumn("Values");

        table.getColumns().addAll(firstCol, secondCol);


        TourDetails.getChildren().add(table);
    }

}
