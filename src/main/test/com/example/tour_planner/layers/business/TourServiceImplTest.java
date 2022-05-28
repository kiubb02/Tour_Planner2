package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.model.Tour;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class TourServiceImplTest {

    TourServiceImpl service = new TourServiceImpl();

    // Test tour
    Tour tour = new Tour("newTour", "Cool Tour", "Wien", "Graz","AUTO" ,120.00900268554688, "01:59:52");
    ArrayList input = new ArrayList<>();

    @Test
    void errorMessage() throws Exception {
        // create Input
        input.add("");
        input.add("description");
        input.add("Wien");
        input.add("Graz");
        input.add("AUTO");
        input.add("create144");
        // get the message
        String message = service.errorMessage(input);
        input.clear();
        // test the function
        Assert.assertEquals("Enter valid Title", message);
    }

    @Test
    void validateInput() throws Exception{
        input.add("TestTour123");
        input.add("");
        input.add("Wien");
        input.add("Graz");
        input.add("AUTO");
        input.add("create144");
        // get the array list
        ArrayList error = service.validateInput(input);
        input.clear();
        Assert.assertEquals("desc", error.get(0).toString());
    }

    @Test
    void validateTitle() {
        // create Input
        String title = "lol2"; // already existing tour
        String oldTitle = "lol2";
        int res = service.validateTitle(title, oldTitle);
        Assert.assertEquals(1, res);
    }

    @Test
    void validateInputEdit() {
        input.add("lol2");
        input.add("Lol");
        input.add("Wien");
        input.add("Graz");
        input.add("AUTO");
        input.add("TestTour123");

        ArrayList output = service.validateInputEdit(input, tour);

        Assert.assertEquals(null, output);
    }

    @Test
    void calulateChildfriendl() {
        int res = service.calulateChildfriendl("hihi");
        Assert.assertEquals(5, res);
    }

    @Test
    void calculatePopularity() {
        int res = service.calulateChildfriendl("hihi");
        Assert.assertEquals(3, res);
    }
}