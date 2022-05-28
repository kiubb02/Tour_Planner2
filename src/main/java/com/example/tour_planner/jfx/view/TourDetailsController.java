package com.example.tour_planner.jfx.view;

import com.example.tour_planner.jfx.viewmodel.TourDetailsViewModel;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.windows.TourEditForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.text.NumberFormat;

public class TourDetailsController {
    @FXML
    public ImageView imageMap;
    public GridPane gridPane;
    public Text titleTour;
    public Text popularityTour;
    public Text friendlinessTour;
    public Text fromTour;
    public Text toTour;
    public Text transportTour;
    public Text distanceTour;
    public Text durationTour;
    public Text descriptionTour;

    private final TourDetailsViewModel tourDetailsViewModel;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        return tourDetailsViewModel;
    }

    @FXML
    void initialize(){
        titleTour.textProperty().bindBidirectional(tourDetailsViewModel.titleProperty());
        popularityTour.textProperty().bindBidirectional(tourDetailsViewModel.popularityProperty());
        friendlinessTour.textProperty().bindBidirectional(tourDetailsViewModel.friendlinessProperty());
        fromTour.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        toTour.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        transportTour.textProperty().bindBidirectional(tourDetailsViewModel.transportProperty());
        distanceTour.textProperty().bindBidirectional(tourDetailsViewModel.distanceProperty(), NumberFormat.getInstance());
        durationTour.textProperty().bindBidirectional(tourDetailsViewModel.durationProperty());
        descriptionTour.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
        imageMap.imageProperty().bindBidirectional(tourDetailsViewModel.imageProperty());
    }

    public void onButtonEdit(ActionEvent actionEvent) {
        Tour selected = tourDetailsViewModel.getTour(titleTour.textProperty().getValue());
        TourEditForm form = new TourEditForm();
        form.showForm(selected);
    }

    public void onButtonExport(ActionEvent actionEvent) {
        tourDetailsViewModel.exportTour(titleTour.textProperty().getValue());
    }

    public void onButtonTourReport(ActionEvent actionEvent) {
        tourDetailsViewModel.createReport(titleTour.textProperty().getValue());
    }

    public void onButtonSummary(ActionEvent actionEvent) {
        tourDetailsViewModel.createSummary(titleTour.textProperty().getValue());
    }

    public void onButtonReport(ActionEvent actionEvent) {
        tourDetailsViewModel.reportTour(titleTour.textProperty().getValue());
    }
}
