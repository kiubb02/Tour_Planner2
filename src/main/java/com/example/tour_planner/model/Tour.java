package com.example.tour_planner.model;

import com.example.tour_planner.utils.TransportType;

import java.io.Serializable;

public class Tour implements Serializable {
    private int id;
    private String name;
    private String description;
    private String from;
    private String to;
    // enum
    private TransportType transport;
    // in km
    private double distance;
    private double duration;

    @Override
    public String toString() {
        return name;
    }
}
