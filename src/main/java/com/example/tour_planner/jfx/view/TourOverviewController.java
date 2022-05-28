package com.example.tour_planner.jfx.view;


import com.example.tour_planner.jfx.viewmodel.MainWindowViewModel;
import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import com.example.tour_planner.utils.windows.*;
import com.example.tour_planner.jfx.viewmodel.TourOverviewViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;


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
        initialize();
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        Object selectedTour = mediaOverviewViewModel.deleteTour(myListView);
        myListView.getItems().remove(selectedTour);
        initialize();
    }

    // show tour details on click
    public void showTour(MouseEvent mouseEvent) {
        // get the tour data
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        // show the tour data
        mainWindowViewModel.selectTour(details);
        // set the tour Logs
        mainWindowViewModel.showTourLogs(details);
    }
}