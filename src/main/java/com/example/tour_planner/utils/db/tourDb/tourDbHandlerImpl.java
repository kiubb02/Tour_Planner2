package com.example.tour_planner.utils.db.tourDb;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.utils.TransportType;
import com.example.tour_planner.utils.db.databaseImpl;
import java.sql.*;
import java.util.ArrayList;

public class tourDbHandlerImpl implements tourDbHandler
{
    // Connection
    private Connection conn = databaseImpl.getInstance().getConnection();

    @Override
    public int createTour(Tour tour)
    {
        try {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Tours title, to, from, transport_type, description, duration, distance " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            // ----- SET VAL ----- //
            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getTo());
            stmt.setString(3, tour.getFrom());
            stmt.setString(4, tour.getTransport());
            stmt.setString(5, tour.getDescription());
            stmt.setDouble(6, tour.getDuration());
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
                Tour nTour = new Tour (res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4), TransportType.valueOf(res.getString(5)), res.getInt(6),
                        res.getInt(7));
                TourList.add(nTour);
            }

            // ----- CLOSE ----- //
            stmt.close();
            conn.close();


        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }
}
