package com.example.tour_planner.model;

import com.example.tour_planner.utils.db.tourDb.tourDbHandlerImpl;
import com.example.tour_planner.utils.db.tourLogDb.tourLogDbHandlerImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TourLogImpl implements TourLog
{
    private tourLogDbHandlerImpl handler = new tourLogDbHandlerImpl();
    @Getter @Setter
    public Date dateTime;
    @Setter @Getter
    public final StringProperty comment;
    @Getter @Setter
    public SimpleIntegerProperty difficulty;
    @Getter @Setter
    public SimpleStringProperty totalTime;
    @Getter @Setter
    public SimpleIntegerProperty rating;
    @Setter
    public SimpleStringProperty tour;
    @Setter @Getter
    private SimpleStringProperty title;

    public TourLogImpl(String title, Date dateTime, String comment, int difficulty, String totalTime, int rating, String tour)
    {
        this.title = new SimpleStringProperty(title);
        this.dateTime = dateTime;
        this.comment =  new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.totalTime = new SimpleStringProperty(totalTime);
        this.rating = new SimpleIntegerProperty(rating);
        this.tour = new SimpleStringProperty(tour);
    }

    public String getTour(){
        return this.tour.get();
    }

    public final StringProperty commentProperty() {
        return comment;
    }
    public final StringProperty titleProperty() {
        return title;
    }

    public final StringProperty totalTimeProperty() {
        return totalTime;
    }

    public final IntegerProperty difficultyProperty() {
        return difficulty;
    }

    public final IntegerProperty ratingProperty() {
        return rating;
    }


    @Override
    public void createLog() {
        handler.createTourLog(this);
    }

    @Override
    public void deleteTourLog(){handler.deleteTourLog(this); }
}
