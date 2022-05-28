package com.example.tour_planner.layers.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourLogDaoImplTest {

    TourLogDaoImpl service = new TourLogDaoImpl();

    @Test
    void titleExist() {
        boolean exist = service.titleExist("new65Test");
        Assertions.assertFalse(exist);
    }

    @Test
    void getAvgRating() {
        float res = service.getAvgRating("hihi");
        Assertions.assertEquals(3, res);
    }
}