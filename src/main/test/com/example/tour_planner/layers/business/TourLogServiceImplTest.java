package com.example.tour_planner.layers.business;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TourLogServiceImplTest {

    TourLogServiceImpl service = new TourLogServiceImpl();
    ArrayList input = new ArrayList<>();

    @Test
    void errorMessage() {
        input.add("lol5");
        input.add("description");
        input.add("Wien");
        input.add("Graz");
        input.add("AUTO");
        input.add("lol5");

        String message = service.errorMessage(input);
        Assertions.assertEquals("Enter valid Title", message);
    }

    @Test
    void validateInput() {
        ArrayList error = service.validateInput(input);
        Assertions.assertEquals("title", error.get(0).toString());
    }

    @Test
    void validateTitle() {
        int res = service.validateTitle("lol5", "lol67");
        Assertions.assertEquals(1, res);
    }

    @Test
    void validateDate() {
        Date date = new Date();
        int res = service.validateDate(date);
        Assertions.assertEquals(0, res);
    }

    @Test
    void validateTime() {
        int res = service.validateTime(Float.valueOf("-4.5"));

        Assertions.assertEquals(0, res);
    }

    @Test
    void summarizeTourLogs() {
        ArrayList<Float> summary = service.summarizeTourLogs("hihi");

        Assertions.assertEquals(3, summary.get(0).intValue());
        Assertions.assertEquals(2.2, summary.get(1).intValue());
    }
}