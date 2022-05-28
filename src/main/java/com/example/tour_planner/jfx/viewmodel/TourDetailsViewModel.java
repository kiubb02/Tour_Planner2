package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import javafx.beans.property.*;
import javafx.scene.image.Image;

public class TourDetailsViewModel {

    TourDaoImpl handler = new TourDaoImpl();
    TourServiceImpl service = new TourServiceImpl();

    private Tour tour = null;

    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty friendliness = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transport = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty duration = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<Image> mapImage = new SimpleObjectProperty<>();

    public TourDetailsViewModel(){
        // get the current tour

    }

    public Property<String> titleProperty() {
        return title;
    }

    public Property<String> popularityProperty() {
        return popularity;
    }

    public Property<String> friendlinessProperty() {
        return friendliness;
    }

    public Property<String> fromProperty() {
        return from;
    }

    public Property<String> toProperty() {
        return to;
    }

    public Property<String> transportProperty() {
        return transport;
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public Property<String> durationProperty() {
        return duration;
    }

    public Property<String> descriptionProperty() {
        return  description;
    }

    public Property<Image> imageProperty() {
        return mapImage;
    }

    // Function from the
    public void setTourModel(Tour selectedMediaItem) {
        if(selectedMediaItem == null){
            title.set("");
            duration.set("00:00:00");
            distance.set(0);
            return;
        }
        this.tour = selectedMediaItem;
        title.set(selectedMediaItem.getName());
        distance.set(selectedMediaItem.getDistance());
        from.set(selectedMediaItem.getFrom());
        to.set(selectedMediaItem.getTo());
        description.set(selectedMediaItem.getDescription());
        transport.set((selectedMediaItem.getTransport()));
        duration.set(selectedMediaItem.getDuration());
        mapImage.set(new Image("file:src/main/java/com/example/tour_planner/utils/maps/" + selectedMediaItem.getName()+ "_map.jpg"));

        // calculated/automated attributes
        popularity.set(String.valueOf(service.calculatePopularity(selectedMediaItem.getName())));
        friendliness.set(String.valueOf(service.calulateChildfriendl(selectedMediaItem.getName())));
    }

    public Tour getTour(String current) {
        return handler.getDetails(current);
    }

    public void exportTour(String value) {
        this.tour = getTour(value);
        service.exportTour(tour);
    }

    public void createReport(String value) {
        this.tour = getTour(value);
        service.generateTourReport(tour);
    }

    public void createSummary(String value) {
        this.tour = getTour(value);
        service.generateSummary(tour);
    }

    public void reportTour(String value) {
    }
}
