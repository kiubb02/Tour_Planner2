package com.example.tour_planner.view;


import com.example.tour_planner.model.Tour;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import com.example.tour_planner.utils.windows.TourForm;


public class TourOverviewController {
    @FXML
    public ListView<Tour> mediaItemList;

    private final TourOverviewViewModel mediaOverviewViewModel;

    public TourOverviewController(TourOverviewViewModel mediaOverviewViewModel) {
        this.mediaOverviewViewModel = mediaOverviewViewModel;
    }

    public TourOverviewViewModel getMediaOverviewViewModel() {
        return mediaOverviewViewModel;
    }

    @FXML
    void initialize() {
        //get the new Items and show them in there
        mediaItemList.setItems(mediaOverviewViewModel.getObservableTours());
        mediaItemList.getSelectionModel().selectedItemProperty().addListener(mediaOverviewViewModel.getChangeListener());
    }

    public void onButtonAdd(ActionEvent actionEvent) {
        TourForm form = new TourForm();
        //show new window
        form.showForm();
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        //mediaOverviewViewModel.deleteTour(mediaItemList.getSelectionModel().getSelectedItem());
    }
}
