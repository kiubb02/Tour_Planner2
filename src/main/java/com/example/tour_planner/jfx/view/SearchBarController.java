package com.example.tour_planner.jfx.view;


import com.example.tour_planner.jfx.viewmodel.MainWindowViewModel;
import com.example.tour_planner.jfx.viewmodel.SearchBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SearchBarController {
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchTextField;
    @FXML
    public HBox hbox;

    private final SearchBarViewModel searchBarViewModel;
    private final MainWindowViewModel mainWindowViewModel;

    public SearchBarController(SearchBarViewModel searchBarViewModel, MainWindowViewModel mainWindowController) {
        this.searchBarViewModel = searchBarViewModel;
        this.mainWindowViewModel = mainWindowController;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        return searchBarViewModel;
    }

    @FXML
    void initialize() {
        searchTextField.textProperty().bindBidirectional( searchBarViewModel.searchStringProperty() );
        searchButton.disableProperty().bind( searchBarViewModel.searchDisabledBinding() );
    }

    public void onSearchButton(ActionEvent actionEvent) {
        mainWindowViewModel.searchTour(searchTextField.getText());
    }
}
