package com.example.tour_planner.model;

import com.example.tour_planner.utils.TransportType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Tour implements Serializable {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String from;
    @Getter @Setter
    private String to;
    // enum
    @Setter
    private String transport;
    // in km
    @Getter @Setter
    private double distance;
    @Getter @Setter
    private String duration;

    // PUBLIC Constructor //
    public Tour (String name, String desc, String from, String to, String type, double dist, String duration) {
        this.name = name;
        this.description = desc;
        this.distance = dist;
        this.duration = duration;
        this.from = from;
        this.to = to;
        this.transport = type;
    }

    public String getTransport()
    { return transport.toString(); }

    @Override
    public String toString() {
        return name;
    }
}
