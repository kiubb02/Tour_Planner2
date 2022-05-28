package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.Tour;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourDaoImplTest {

    TourDaoImpl service = new TourDaoImpl();

    @Test
    void getDetails() {
        Tour tour = service.getDetails("hihi");
        Assertions.assertEquals(tour.getDescription(), "lolol");
    }

    @Test
    void getTourList() {
        ObservableList listT = service.getTourList();
        Assertions.assertNotEquals(null, listT);
    }

    @Test
    void searchTour() {
        ObservableList listT = service.searchTour("TourTest554466");
        assertNull(listT);
    }

    @Test
    void getSumRating() {
        int res = service.getSumRating("hihi");
        Assertions.assertEquals(3, res);
        Assertions.assertNotEquals(205, res);
    }

    @Test
    void getSumDifficulty() {
        int res = service.getSumDifficulty("hihi");
        Assertions.assertNotEquals(3, res);
        Assertions.assertNotEquals(205, res);
    }

    @Test
    void getTourStrikes() {
        int res = service.getTourStrikes("hihi");
        Assertions.assertNotEquals(6, res);
    }
}