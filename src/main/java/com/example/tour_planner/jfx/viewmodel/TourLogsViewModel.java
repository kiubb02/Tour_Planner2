package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.Getter;

public class TourLogsViewModel {
    @Getter
    private Property<ObservableList<TourLogImpl>> data = new SimpleListProperty<>();

    private ObservableList<TourLogImpl> obsData = FXCollections.observableArrayList();

    private final ListProperty<TourLogImpl> tourLogs = new SimpleListProperty<>();
    private final StringProperty tourTitle = new SimpleStringProperty();

    TourLogDaoImpl handlerLog = new TourLogDaoImpl();

    public void setTourLogs(Tour selectedMediaItem) {
        tourTitle.set(selectedMediaItem.getName());
        tourLogs.bindBidirectional(data);
        obsData = handlerLog.getTourLogs(selectedMediaItem.toString());
        data.setValue(obsData);
    }

    public ListProperty<TourLogImpl> tourLogListProperty() {
        return tourLogs;
    }

    public void deleteLog(TableView<TourLogImpl> tableView) {
        TourLogImpl tourLog = tableView.getSelectionModel().getSelectedItem();
        tourLog.deleteTourLog();
    }

    public TourLogImpl getSelectedLog(TableView<TourLogImpl> tableView) {
        return tableView.getSelectionModel().getSelectedItem();
    }

    public Property<String> titleProperty() {
        return tourTitle;
    }
}
