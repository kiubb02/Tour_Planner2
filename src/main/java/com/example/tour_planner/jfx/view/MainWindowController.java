package com.example.tour_planner.jfx.view;


import com.example.tour_planner.jfx.viewmodel.MainWindowViewModel;
import com.example.tour_planner.jfx.viewmodel.TourOverviewViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowController {
    // Controllers of included fxml-files are injected here
    // fx:id Attribute of <fx:include> tag + "Controller"
    // tutorial see https://riptutorial.com/javafx/example/7285/nested-controllers
    @FXML private SearchBarController searchBarController;    // injected controller of SearchBar.fxml
    @FXML private TourOverviewViewModel mediaOverviewController;    // injected controller of MediaOverview.fxml
    //@FXML private MediaDetailsController mediaDetailsController;    // injected controller of MediaDetails.fxml

    private final MainWindowViewModel mainViewModel;

    public MainWindowController(MainWindowViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public MainWindowViewModel getMainViewModel() {
        return mainViewModel;
    }

    @FXML void initialize() {
    }

    public void onMenuFileQuitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
    public void onMenuHelpAboutClicked(ActionEvent actionEvent) {
        Alert aboutBox = new Alert(Alert.AlertType.INFORMATION, "Semesterproject BIF4-SWE2\nby Chiara Babiak and Franchezca Meree Cruz");
        aboutBox.setTitle("About TourPlanner");
        aboutBox.showAndWait();
    }

    public void importFile(ActionEvent actionEvent) {
        //open file explorer
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(stage);
    }

    public void exportFile(ActionEvent actionEvent) {

    }
}
