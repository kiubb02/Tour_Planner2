package com.example.tour_planner.layers.model;

import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.TourLog;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.Date;

public class TourLogImpl implements TourLog
{
    private TourLogDaoImpl handler = new TourLogDaoImpl();
    @Getter @Setter
    public Date dateTime;
    @Setter @Getter
    public final StringProperty comment;
    @Getter @Setter
    public SimpleIntegerProperty difficulty;
    @Getter @Setter
    public SimpleFloatProperty totalTime;
    @Getter @Setter
    public SimpleIntegerProperty rating;
    @Setter
    public SimpleStringProperty tour;
    @Setter @Getter
    private SimpleStringProperty title;

    public TourLogImpl(String title, Date dateTime, String comment, int difficulty, Float totalTime, int rating, String tour)
    {
        this.title = new SimpleStringProperty(title);
        this.dateTime = dateTime;
        this.comment =  new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.totalTime = new SimpleFloatProperty(totalTime);
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

    public final FloatProperty totalTimeProperty() {
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

    @Override
    public void modifyLog(TourLogImpl log, String oldTitle) {
        handler.modifyTourLog(log, oldTitle);
    }
}
