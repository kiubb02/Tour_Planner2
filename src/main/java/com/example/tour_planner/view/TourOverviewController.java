package com.example.tour_planner.view;


import com.example.tour_planner.model.Tour;
import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;
import com.example.tour_planner.utils.windows.TourEditForm;
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
import javafx.scene.layout.HBox;
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
    public HBox Tours;
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
        tourList = mediaOverviewViewModel.getTourList();
        myListView.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(tourList));
        //myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void onButtonAdd(ActionEvent actionEvent) {
        TourForm form = new TourForm();
        // show new window
        form.showForm();
        // TODO : add the object to the List View below
        Object tour = mediaOverviewViewModel.addTour();
        myListView.getItems().add(tour);
    }

    public void onButtonEdit(ActionEvent actionEvent){
        // get title of selected listview item
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        TourEditForm form = new TourEditForm();
        form.showForm(details);
        // Tour has now been edited
        Object tour = mediaOverviewViewModel.addTour();
        myListView.getItems().add(tour);
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        Object selectedTour = mediaOverviewViewModel.deleteTour(myListView);
        //delete element from list view
        myListView.getItems().remove(selectedTour);
    }

    // show tour details on click
    public void showTour(MouseEvent mouseEvent) {
        // delete previous children of the Vbox
        TourDetails.getChildren().clear();
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        // add image and details to Hbox
        createTable(details);
        // add Tour log Table under Hbox => to the Vbox
        addTourLogs();

    }

    public void addTourLogs(){
        TableView tableView = new TableView();
        // date/time, comment, difficulty, total time, and rating


    }

    public void createTable(Tour details){
        HBox horizontal = new HBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text(details.getName());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label from = new Label("From:");
        grid.add(from, 0, 1);
        Text fromField = new Text(details.getFrom());
        grid.add(fromField, 1, 1);

        Label toLabel = new Label("To:");
        grid.add(toLabel, 0, 2);
        Text to = new Text(details.getTo());
        grid.add(to, 1, 2);

        Label transportLabel = new Label("Transport:");
        grid.add(transportLabel, 0, 3);
        Text transport = new Text(details.getTransport());
        grid.add(transport, 1, 3);

        Label distlabel = new Label("Distance:");
        grid.add(distlabel, 0, 4);
        Double distance = details.getDistance();
        Text dist = new Text(distance.toString());
        grid.add(dist, 1, 4);

        Label durLabel = new Label("Duration:");
        grid.add(durLabel, 0, 5);
        Text dur = new Text(details.getDuration());
        grid.add(dur, 1, 5);

        Label descLabel = new Label("Description:");
        grid.add(descLabel, 0, 6);
        Text desc = new Text(details.getDescription());
        grid.add(desc, 1, 6);

        // add the edit button before anything else to the TourDetails
        Button edit = new Button("Edit");
        grid.add(edit, 2, 7);

        edit.setOnAction(this::onButtonEdit);

        // first add image to Hbox

        // Then add the grid
        horizontal.getChildren().add(grid);

        TourDetails.getChildren().add(horizontal);
    }

}
