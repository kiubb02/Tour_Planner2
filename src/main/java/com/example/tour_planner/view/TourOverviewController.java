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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import com.example.tour_planner.utils.windows.TourForm;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        // delete previous children of the Vbox
        TourDetails.getChildren().clear();

        // get selected tour item
        Object selectedTour = myListView.getSelectionModel().getSelectedItem();
        // Test if object is correct : Yes correct object is shown
        // System.out.println(selectedTour);
        // get details from the tour
        tourDbHandlerImpl handler = new tourDbHandlerImpl();
        Tour details = handler.getDetails(selectedTour.toString());
        // Test if object is correct : Object is correct
        // System.out.println(details.getString("name"));

        // add children to Vbox
        createTable(details);
        // add the image

    }

    public void createTable(Tour details){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label tourName = new Label("Title:");
        grid.add(tourName, 0, 1);
        Text title = new Text(details.getName());
        grid.add(title, 1, 1);

        Label from = new Label("From:");
        grid.add(from, 0, 2);
        Text fromField = new Text(details.getFrom());
        grid.add(fromField, 1, 2);

        Label toLabel = new Label("To:");
        grid.add(toLabel, 0, 3);
        Text to = new Text(details.getTo());
        grid.add(to, 1, 3);

        Label transportLabel = new Label("Transport:");
        grid.add(transportLabel, 0, 4);
        Text transport = new Text(details.getTransport());
        grid.add(transport, 1, 4);

        Label distlabel = new Label("Distance:");
        grid.add(distlabel, 0, 5);
        Double distance = details.getDistance();
        Text dist = new Text(distance.toString());
        grid.add(dist, 1, 5);

        Label durLabel = new Label("Duration:");
        grid.add(durLabel, 0, 7);
        Text dur = new Text(details.getDuration());
        grid.add(dur, 1, 7);



        TourDetails.getChildren().add(grid);
    }

}
