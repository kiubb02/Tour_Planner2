package com.example.tour_planner.utils.db.tourLogDb;

import com.example.tour_planner.model.TourLog;
import com.example.tour_planner.utils.db.databaseImpl;

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
    public int createTourLog(TourLog tourLog)
    {
        try {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO TourLogs date, time, comment, difficulty, totalTime, rating " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            // ----- SET VAL ----- //
            stmt.setTime(1, tourLog.getDate());
            stmt.setTime(2, tourLog.getTime());
            stmt.setString(3, tourLog.getComment());
            stmt.setString(4, tourLog.getDifficulty());
            stmt.setInt(5, tourLog.getTotalTime());
            stmt.setInt(6, tourLog.getRating());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected == 0) {
                stmt.close();
                conn.close();
                return 0;
            }

            int num = 0;

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next())
            { num = generatedKeys.getInt(1); }

            // ----- CLOSE ----- //
            generatedKeys.close();
            stmt.close();
            conn.close();

            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteTourLog(TourLog tourLog) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM TourLogs WHERE id = ?;"
            );
            // ----- SET VAL ----- //
            stmt.setInt(1, tourLog.getId());
            stmt.executeUpdate();

            // ----- CLOSE ----- //
            stmt.close();
            conn.close();
            return 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int modifyTourLog(TourLog TourLog) {
        return 0;
    }

    @Override
    public ArrayList<TourLog> getTourLog(TourLog tourlog) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM TourLogs WHERE id= ?;"
            );
            // ----- SET VAL ----- //
            stmt.setInt(1, tourlog.getId());
            stmt.executeUpdate();

            ResultSet res = stmt.executeQuery();

            ArrayList<TourLog> TourLogList = new ArrayList<>();

            // ADD TOURLOGS TO LIST
            while (res.next())
            {
                TourLog nTourLog = new TourLog(res.getTime(1), res.getTime(2), res.getString(3),
                        res.getString(4), res.getInt(5), res.getInt(6));
                TourLogList.add(nTourLog);
            }

            // ----- CLOSE ----- //
            stmt.close();
            conn.close();


        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }
}
