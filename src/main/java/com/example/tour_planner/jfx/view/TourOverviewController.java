package com.example.tour_planner.jfx.view;

import com.example.tour_planner.jfx.viewmodel.MainWindowViewModel;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.windows.*;
import com.example.tour_planner.jfx.viewmodel.TourOverviewViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class TourOverviewController {
    @FXML
    public VBox TourDetails;
    public VBox tourDetails;
    public VBox tourLogs;
    public VBox TourLogs;
    @FXML
    private ListView myListView = new ListView<>();

    private final TourOverviewViewModel mediaOverviewViewModel;
    private final MainWindowViewModel mainWindowViewModel;

    // FUNCTIONS //

    public TourOverviewController(TourOverviewViewModel mediaOverviewViewModel, MainWindowViewModel mainWindowViewModel) {
        this.mediaOverviewViewModel = mediaOverviewViewModel;
        this.mainWindowViewModel = mainWindowViewModel;
    }

    @FXML
    void initialize() {
        myListView.itemsProperty().bindBidirectional(mediaOverviewViewModel.getListProperty());
    }


    public void onButtonAdd(ActionEvent actionEvent) {
        TourForm form = new TourForm();
        form.showForm();
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        Object selectedTour = mediaOverviewViewModel.deleteTour(myListView);
        myListView.getItems().remove(selectedTour);
    }

    // show tour details on click
    public void showTour(MouseEvent mouseEvent) {
        // get the tour data
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        // show the Tour Details
        mainWindowViewModel.selectTour(details);
        mainWindowViewModel.showTourLogs(details);
    }
}