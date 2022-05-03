package com.example.tour_planner.utils.db.tourDb;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.utils.db.databaseImpl;
import javafx.scene.control.ListView;

import java.sql.*;
import java.util.ArrayList;

// --- STATEMENTS
// INSERT INTO (table) (column) VAlUES (?,?, ...);
// DELETE FROM (table) WHERE = ?;
// UPDATE (table) SET (column) = ? WHERE = ?;
// SELECT (column) FROM (table) WHERE = ?;

// --- TOUR INFO
// TITLE - String
// TO - String
// FROM - String
// TRANSPORT TYPE - String
// DESCRIPTION - String
// DATE - Time
// DURATION - Int
// DISTANCE - Int

public class tourDbHandlerImpl implements tourDbHandler
{
    // Connection
    private Connection conn = databaseImpl.getInstance().getConnection();

    @Override
    public int createTour(Tour tour)
    {
        try {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement("""
                    INSERT INTO tour(title, "to", "from", "transportType", description, duration, distance) VALUES (?,?,?, ?, ?, ?, ?);
                    """);

            // ----- SET VAL ----- //
            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getTo());
            stmt.setString(3, tour.getFrom());
            stmt.setString(4, tour.getTransport());
            stmt.setString(5, tour.getDescription());
            stmt.setString(6, tour.getDuration());
            stmt.setDouble(7, tour.getDistance());

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

            tour.setId(num);

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
    public int deleteTour(Tour tour) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Tours WHERE id = ?;"
            );
            // ----- SET VAL ----- //
            stmt.setInt(1, tour.getId());
            stmt.executeUpdate();

            // ----- CLOSE ----- //
            stmt.close();
            conn.close();
            return 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int modifyTour(Tour tour) {
        return 0;
    }

    @Override
    public ArrayList<Tour> getTour(Tour tour)
    {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM Tours WHERE title = ?;"
            );
            // ----- SET VAL ----- //
            stmt.setString(1, tour.getName());
            stmt.executeUpdate();

            ResultSet res = stmt.executeQuery();

            // title, to, from, transport_type, description, duration, distance
            ArrayList<Tour> TourList = new ArrayList<>();

            // ADD TOURS TO LIST
            while (res.next())
            {
                //Tour nTour = new Tour (res.getString(1), res.getString(2), res.getString(3),
                //        res.getString(4), TransportType.valueOf(res.getString(5)), res.getInt(6),
                //        res.getInt(7));
                //TourList.add(nTour);
            }

            // ----- CLOSE ----- //
            stmt.close();
            conn.close();


        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    @Override
    public ArrayList<Tour> getTourList(){
        ArrayList<Tour> tourList = new ArrayList<>();

        try{
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT *
                    FROM tour
                    """);

            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return null;
            }

            while(res.next()){
                String title = res.getString("title");
                String desc = res.getString("description");
                String from = res.getString("from");
                String to = res.getString("to");
                String transport = res.getString("transportType");
                Double dist = res.getDouble("distance");
                String duration = res.getString("duration");

                // create Tour obj
                Tour tour = new Tour(title, desc, from, to, transport, dist, duration);

                // put into array
                tourList.add(tour);

            }

            stmt.close();
            conn.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return tourList;
    }
}
