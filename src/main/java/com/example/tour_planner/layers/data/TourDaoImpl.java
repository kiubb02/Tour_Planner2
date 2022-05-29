// we r in Data Layer ==> access database here
package com.example.tour_planner.layers.data;

import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.api.mapAPI;
import com.example.tour_planner.utils.db.databaseImpl;
import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourDaoImpl implements TourDao {

    // Connection
    private Connection conn = databaseImpl.getInstance().getConnection();
    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    @Override
    public void createTour(Tour tour) {
        logger.debug("Test message");
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
                return;
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }
    }

    @Override
    public void deleteTour(String tour_name) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM tour WHERE title = ?;"
            );
            // ----- SET VAL ----- //
            stmt.setString(1, tour_name);
            stmt.executeUpdate();

            // ----- CLOSE ----- //
            stmt.close();
            mapAPI.deleteRouteImage(tour_name);
        } catch (SQLException e) { logger.warn(e.toString()); }
    }

    @Override
    public void modifyTour(String old_name, Tour tour) {
        try
        {
            // ----- PREPARED STATEMENT ----- //
            PreparedStatement stmt = conn.prepareStatement("""
                    UPDATE tour
                    SET title = ?, description = ?, "from" = ?, "to" = ?, "transportType" = ?, distance = ?, duration = ?
                    WHERE title = ?;
            """);
            // ----- SET VAL ----- //
            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getDescription());
            stmt.setString(3, tour.getFrom());
            stmt.setString(4, tour.getTo());
            stmt.setString(5, tour.getTransport());
            stmt.setDouble(6, tour.getDistance());
            stmt.setString(7, tour.getDuration());
            stmt.setString(8, old_name);
            stmt.executeUpdate();

            // ----- CLOSE ----- //
            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }
    }

    @Override
    public Tour getDetails(String name) {
        Tour tour = null;

        try
        {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM tour WHERE title = ?;"
            );

            stmt.setString(1, name);

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

                // fill JSONObject
                //String string = "{\"name\": \""+title+"\", \"desc\": \""+desc+"\", \"from\": \""+from+"\", \"to\": \""+to+"\", \"transport\": \""+transport+"\", \"distance\": \""+dist+"\", \"duration\": \""+duration+"\"}";
                //details = new JSONObject(string);
                tour = new Tour(title, desc, from, to, transport, dist, duration);
            }

            res.close();
            stmt.close();
            //conn.close();
        }catch(SQLException e){ logger.warn(e.toString()); }

        return tour;
    }

    @Override
    public ObservableList<Tour> getTourList() {
        ObservableList<Tour> tourList = FXCollections.observableArrayList();
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
        } catch (SQLException e) { logger.warn(e.toString()); }
        return tourList;
    }

    @Override
    public ObservableList<Tour> searchLogs(String search) {
        ObservableList<Tour> tourList = FXCollections.observableArrayList();

        try{

            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT *
                    FROM log
                    WHERE ( "tourName" LIKE ?
                    OR title LIKE ? 
                    OR comment LIKE ?);
                    """);

            stmt.setString(1,"%" + search + "%");
            stmt.setString(2,"%" + search + "%");
            stmt.setString(3,"%" + search + "%");

            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return null;
            }

            while(res.next()){
                String title = res.getString("tourName");
                Tour tour = getDetails(title);
                tourList.add(tour);
            }
            stmt.close();

        } catch (SQLException e) { logger.warn(e.toString()); }

        return tourList;
    }

    @Override
    public ObservableList<Tour> searchTour(String search) {
        ObservableList<Tour> tourList = FXCollections.observableArrayList();

        try{
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT *
                    FROM tour
                    WHERE ( title LIKE ?
                    OR description LIKE ? 
                    OR "from" LIKE ?
                    OR "to" LIKE ? 
                    OR "transportType" LIKE ? );
                    """);

            stmt.setString(1,"%" + search + "%");
            stmt.setString(2,"%" + search + "%");
            stmt.setString(3,"%" + search + "%");
            stmt.setString(4,"%" + search + "%");
            stmt.setString(5,"%" + search + "%");

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

        } catch (SQLException e) { logger.warn(e.toString()); }


        // before we return the Tours we also search in the Tour Logs
        if(searchLogs(search) != null) tourList.addAll(searchLogs(search));


        return tourList;
    }

    @Override
    public int getSumRating(String title) {
        int sum = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement(
                    """
                     SELECT SUM(rating) 
                     FROM log 
                     WHERE "tourName" = ?;
            """
            );

            stmt.setString(1, title);

            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return 0;
            }

            while (res.next()) {
                int c = res.getInt(1);
                sum = sum + c;
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }

        return sum;
    }

    @Override
    public int getSumDifficulty(String title) {
        int sum = 0;
        try
        {
            PreparedStatement stmt = conn.prepareStatement(
                    """
                             SELECT SUM(difficulty) FROM log WHERE "tourName" = ?;
                """
            );

            stmt.setString(1, title);

            ResultSet res = stmt.executeQuery();
            if(!res.isBeforeFirst()){
                return 0;
            }

            while (res.next()) {
                int c = res.getInt(1);
                sum = sum + c;
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }

        return sum;
    }

    @Override
    public int getTourStrikes(String title)
    {
        int strikes = -1;
        try
        {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT strikes FROM tour WHERE title = ?;"
            );

            stmt.setString(1, title);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                strikes = res.getInt(1);
            }

            // add one strike
            strikes++;

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }

        return strikes;
    }

    @Override
    public void reportTour(String title)
    {
        int strikes = getTourStrikes(title);
        if(strikes == -1) { logger.debug("no such tour"); }
        else if(strikes == 5) { deleteTour(title); }

        try
        {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE tour SET strikes = ? WHERE title = ?;"
            );

            stmt.setInt(1, strikes);
            stmt.setString(2, title);

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected == 0) {
                stmt.close();
                logger.debug("no report made");
                return;
            }

            stmt.close();
        } catch (SQLException e) { logger.warn(e.toString()); }
    }
}
