package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.model.Tour;

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

        //this.searchBarViewModel.addSearchListener(searchString->searchTours(searchString));
        // instead of the lambda-expression from above, you also can use the following "classical" event-handler implementation with anonymous inner classes
//        this.searchBarViewModel.addSearchListener(new SearchBarViewModel.SearchListener() {
//            @Override
//            public void search(String searchString) {
//                var tours = BL.getInstance().findMatchingTours( searchString );
//                toursOverviewViewModel.setTours(tours);
//            }
//        });

        //this.mediaOverviewViewModel.addSelectionChangedListener(selectedTour->selectTour(selectedTour));
    }

    public void selectTour(Tour selectedMediaItem) {
        tourDetailsViewModel.setTourModel(selectedMediaItem);
    }
    public void showTourLogs(Tour selectedMediaItem) { tourLogsViewModel.setTourLogs(selectedMediaItem); }


    /*private void searchTours(String searchString) {
        var tours = BL.getInstance().findMatchingTours( searchString );
        mediaOverviewViewModel.setTours(tours);
    }*/
}
