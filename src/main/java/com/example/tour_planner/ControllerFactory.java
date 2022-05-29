package com.example.tour_planner;

import com.example.tour_planner.jfx.view.*;
import com.example.tour_planner.jfx.viewmodel.*;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourOverviewViewModel mediaOverviewViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourLogsViewModel tourLogsViewModel;

    public ControllerFactory() {
        searchBarViewModel = new SearchBarViewModel();
        mediaOverviewViewModel = new TourOverviewViewModel();
        tourDetailsViewModel = new TourDetailsViewModel();
        tourLogsViewModel = new TourLogsViewModel();
        mainWindowViewModel = new MainWindowViewModel(searchBarViewModel, mediaOverviewViewModel, tourDetailsViewModel, tourLogsViewModel);
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
       if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel, mainWindowViewModel);
        }else if (controllerClass == TourOverviewController.class) {
            return new TourOverviewController(mediaOverviewViewModel, mainWindowViewModel);
        } else if (controllerClass == TourDetailsController.class) {
           return new TourDetailsController(tourDetailsViewModel);
       } else if (controllerClass == TourLogsController.class) {
           return new TourLogsController(tourLogsViewModel);
       }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }


    //
    // Singleton-Pattern with early-binding
    //
    private static final ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }

}
