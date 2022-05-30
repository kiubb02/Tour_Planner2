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
        input.add("");
        input.add(null);
        input.add(4.5);
        input.add("5");
        input.add("4");
        input.add("not cool");
        input.add("create144");

        String message = service.errorMessage(input);
        Assertions.assertEquals("Enter valid Title", message);
        input.clear();
    }

    @Test
    void validateInput() {
        input.add("");
        input.add(null);
        input.add(4.5);
        input.add("5");
        input.add("4");
        input.add("not cool");
        input.add("create144");

        ArrayList error = service.validateInput(input);
        Assertions.assertEquals("title", error.get(0).toString());
        input.clear();
    }

    @Test
    void validateTitle() {
        int res = service.validateTitle("lol5", "lol67");
        Assertions.assertEquals(1, res);
    }

    @Test
    void validateDate() {
        Date date = new Date();
        date = null;
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
        Assertions.assertEquals(2.0, summary.get(1).intValue());
    }
}