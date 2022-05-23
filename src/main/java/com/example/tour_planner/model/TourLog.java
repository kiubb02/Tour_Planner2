package com.example.tour_planner.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

public class TourLog
{
    @Getter @Setter
    private Time date;
    @Getter @Setter
    private Time time;
    @Getter @Setter
    private String comment;
    @Getter @Setter
    private String difficulty;
    // Hard, Moderate, Easy
    @Getter @Setter
    private int totalTime;
    @Getter @Setter
    private int rating;

    public TourLog(Time date, Time time, String comment, String difficulty, int totalTime, int rating)
    {
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }
}
