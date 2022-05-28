package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.data.TourLogDao;
import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.api.mapAPI;
import com.example.tour_planner.utils.db.databaseImpl;
import com.example.tour_planner.utils.logger.Log4J2Wrapper;
import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class TourLogDaoImpl implements TourLogDao {

    // Connection
    private Connection conn = databaseImpl.getInstance().getConnection();
    private static final LoggerWrapper logger = LoggerFactory.getLogger();


    @Override
    public int createTourLog(TourLogImpl tourLog) {
        try {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement("""
                    INSERT INTO log("date", "comment", "difficulty", "time", rating, "tourName", "title") VALUES (?, ?,?,?, ?, ?, ?);
                    """);
            // ----- SET VAL ----- //
            stmt.setDate(1, new java.sql.Date(tourLog.getDateTime().getTime()));
            stmt.setString(2, tourLog.getComment().get());
            stmt.setInt(3, tourLog.getDifficulty().getValue());
            stmt.setFloat(4, tourLog.getTotalTime().get());
            stmt.setInt(5, tourLog.getRating().getValue());
            stmt.setString(6, tourLog.getTour());
            stmt.setString(7, tourLog.getTitle().get());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected == 0) {
                stmt.close();
                conn.close();
                return 0;
            }

            stmt.close();
            return 1;
        } catch (SQLException e) { logger.warn(e.toString()); }
        return 0;
    }

    @Override
    public int deleteTourLog(TourLogImpl tourLog) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM log WHERE title = ?;"
            );
            // ----- SET VAL ----- //
            stmt.setString(1, tourLog.getTitle().get());
            stmt.executeUpdate();

            // ----- CLOSE ----- //
            stmt.close();
            return 1;
        } catch (SQLException e) { logger.warn(e.toString()); }
        return 0;
    }

    @Override
    public int modifyTourLog(TourLogImpl tour, String oldTitle) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement("""
                    UPDATE log
                    SET "date" = ?, "comment" = ?, "difficulty" = ?, "time" = ?, "rating" = ?, "tourName" = ?, "title" = ?
                    WHERE title = ?;
            """);
            // ----- SET VAL ----- //
            stmt.setDate(1, new java.sql.Date(tour.getDateTime().getTime()));
            stmt.setString(2, tour.getComment().get());
            stmt.setInt(3, tour.getDifficulty().getValue());
            stmt.setFloat(4, tour.getTotalTime().get());
            stmt.setInt(5, tour.getRating().getValue());
            stmt.setString(6, tour.getTour());
            stmt.setString(7, tour.getTitle().get());
            stmt.setString(8, oldTitle);
            stmt.executeUpdate();

            // ----- CLOSE ----- //
            stmt.close();
            return 1;
        } catch (SQLException e) { logger.warn(e.toString()); }
        return 0;
    }

    @Override
    public ObservableList<TourLogImpl> getTourLogs(String tourName) {
        ObservableList<TourLogImpl> list =  FXCollections.observableArrayList();;

        try{
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT *
                    FROM log
                    WHERE "tourName" = ?
                    """);

            stmt.setString(1, tourName);
            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return null;
            }

            while(res.next()){
                String title = res.getString("title");
                Date date = res.getDate("date");
                String comment = res.getString("comment");
                Float time = res.getFloat("time");
                int difficulty = res.getInt("difficulty");
                int rating = res.getInt("rating");
                String tour = res.getString("tourName");

                // create Tour obj
                TourLogImpl tourLog = new TourLogImpl(title, date, comment, difficulty, time, rating, tour);
                // put into array
                list.add(tourLog);
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }

        return list;
    }

    @Override
    public boolean titleExist(String title) {
        try{
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT *
                    FROM log
                    WHERE "title" = ?
        """);

            stmt.setString(1, title);
            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return false;
            }

            return true;
        }  catch (SQLException e) { logger.warn(e.toString()); }
        return false;
    }

    @Override
    public float getAvgTime(String tour) {
        float avg = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement(
                    """
                     SELECT AVG("time") 
                     FROM log 
                     WHERE "tourName" = ?;
            """
            );

            stmt.setString(1, tour);

            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return 0;
            }

            while (res.next()) {
                avg = res.getFloat(1);
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }

        return avg;
    }

    @Override
    public float getAvgRating(String tour) {
        float avg = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement(
                    """
                     SELECT AVG(rating) 
                     FROM log 
                     WHERE "tourName" = ?;
            """
            );

            stmt.setString(1, tour);

            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return 0;
            }

            while (res.next()) {
                avg = res.getFloat(1);
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }

        return avg;
    }
}
