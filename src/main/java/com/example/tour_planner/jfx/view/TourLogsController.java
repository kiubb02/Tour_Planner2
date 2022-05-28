package com.example.tour_planner.jfx.view;

import com.example.tour_planner.jfx.viewmodel.TourLogsViewModel;
import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.windows.TourLogEditForm;
import com.example.tour_planner.utils.windows.TourLogForm;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TourLogsController {
    @FXML
    public TableView<TourLogImpl> tableView = new TableView<TourLogImpl>();
    @FXML
    private ObservableList<TourLogImpl> data = FXCollections.observableArrayList();
    private final ListProperty<TourLogImpl> tourLogs = new SimpleListProperty<>();
    public Text selectedTour;

    private final TourLogsViewModel tourLogsOverviewModel;
    public TableColumn commentColumn;
    public TableColumn titleColumn;
    public TableColumn dateColumn;
    public TableColumn timeColumn;
    public TableColumn ratingColumn;
    public TableColumn difficultyColumn;

    public TourLogsController(TourLogsViewModel tourLogsOverviewModel) {
        this.tourLogsOverviewModel = tourLogsOverviewModel;
    }

    public TourLogsViewModel getTourLogsOverviewModel() {
        return tourLogsOverviewModel;
    }

    @FXML
    void initialize(){
        tourLogs.bindBidirectional(tourLogsOverviewModel.tourLogListProperty());
        data = tourLogs;
        selectedTour.textProperty().bindBidirectional(tourLogsOverviewModel.titleProperty());
        commentColumn.setCellValueFactory(new PropertyValueFactory<TourLogImpl, String>("comment"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<TourLogImpl, String>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Date>("dateTime"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Float>("totalTime"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Integer>("rating"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Integer>("difficulty"));
        tableView.setItems(data);
    }

    public void createTourLog(ActionEvent actionEvent) {
        TourLogForm form = new TourLogForm();
        form.showForm(selectedTour.textProperty().getValue());
        tableView.refresh();
    }

    public void editTourlog(ActionEvent actionEvent) {
        TourLogImpl tourLog = tourLogsOverviewModel.getSelectedLog(tableView);
        TourLogEditForm form = new TourLogEditForm();
        form.showForm(tourLog, tourLog.getTour());
        tableView.refresh();
    }

    public void deleteTourLog(ActionEvent actionEvent) {
        tourLogsOverviewModel.deleteLog(tableView);
        tableView.refresh();
    }
}
