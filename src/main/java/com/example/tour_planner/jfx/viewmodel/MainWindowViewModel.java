package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.model.Tour;
import javafx.scene.control.TextField;

public class MainWindowViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourOverviewViewModel mediaOverviewViewModel;
    private TourDetailsViewModel tourDetailsViewModel;
    private TourLogsViewModel tourLogsViewModel;

    public MainWindowViewModel(SearchBarViewModel searchBarViewModel, TourOverviewViewModel mediaOverviewViewModel, TourDetailsViewModel tourDetailsViewModel, TourLogsViewModel tourLogsViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.mediaOverviewViewModel = mediaOverviewViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.tourLogsViewModel = tourLogsViewModel;

        showTourList("KeyA23456bbnrefreshList");

        //this.searchBarViewModel.addSearchListener(new SearchBarViewModel.SearchListener() {
        //    private final TourOverviewViewModel mediaOverviewViewModel = new TourOverviewViewModel();
        //    @Override
        //    public void search(String searchString) {
        //        this.mediaOverviewViewModel.setTourList(searchString);
        //    }
        //});
    }

    public void selectTour(Tour selectedMediaItem) {
        tourDetailsViewModel.setTourModel(selectedMediaItem);
    }
    public void showTourLogs(Tour selectedMediaItem) { tourLogsViewModel.setTourLogs(selectedMediaItem); }
    public void showTourList(String s) { mediaOverviewViewModel.setTourList(s); }
    public void searchTour(String searchTextField) { mediaOverviewViewModel.setTourList(searchTextField);}
}
