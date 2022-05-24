package com.example.tour_planner.utils.db.tourLogDb;

import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.db.databaseImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

// --- STATEMENTS
// INSERT INTO (table) (column) VAlUES (?,?, ...);
// DELETE FROM (table) WHERE = ?;
// UPDATE (table) SET (column) = ? WHERE = ?;
// SELECT (column) FROM (table) WHERE = ?;

// --- TOUR  LOG INFO
// DATE
// TIME
// COMMENT
// DIFFICULTY
// TOTAL TIME
// RATING

public class tourLogDbHandlerImpl implements  tourLogDbHandler
{
    // Connection
    private Connection conn = databaseImpl.getInstance().getConnection();

    @Override
    public int createTourLog(TourLogImpl tourLog)
    {
        try {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement("""
                    INSERT INTO log("date", "comment", "difficulty", "time", rating, "tourName", "title") VALUES (?, ?,?,?, ?, ?, ?);
                    """);

            // ----- SET VAL ----- //
            stmt.setDate(1, new java.sql.Date(tourLog.getDateTime().getTime()));
            stmt.setString(2, tourLog.getComment().get());
            stmt.setInt(3, tourLog.getDifficulty().getValue());
            stmt.setString(4, tourLog.getTotalTime().get());
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
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ObservableList<TourLogImpl> getTourLogs(String tourName){
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
                String time = res.getString("time");
                int difficulty = res.getInt("difficulty");
                int rating = res.getInt("rating");
                String tour = res.getString("tourName");

                // create Tour obj
                TourLogImpl tourLog = new TourLogImpl(title, date, comment, difficulty, time, rating, tour);
                // put into array
                list.add(tourLog);
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
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
            conn.close();
            return 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int modifyTourLog(TourLogImpl TourLog) {
        return 0;
    }

    @Override
    public ArrayList<TourLogImpl> getTourLog(TourLogImpl tourlog) {
        ArrayList<TourLogImpl> tours = null;
        return tours;
    }
}
