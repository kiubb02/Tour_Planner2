package com.example.tour_planner.utils.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Tour class
import com.example.tour_planner.model.Tour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//JSONObject
import org.json.simple.JSONObject;

public class TourForm {

    //build a new scene to open as a pop up form
    public void showForm(){
        VBox vBox = new VBox(); //for now
        //current scene of program
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);

        Stage stage = new Stage();
        stage.setTitle("Tour Form");

        Text scenetitle = new Text("New Tour: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label tourName = new Label("Title:");
        grid.add(tourName, 0, 1);

        TextField tourNameField = new TextField();
        grid.add(tourNameField, 1, 1);

        Label from = new Label("From:");
        grid.add(from, 0, 2);

        TextField fromField = new TextField();
        grid.add(fromField, 1, 2);

        Label to = new Label("To:");
        grid.add(to, 0, 3);

        TextField toField = new TextField();
        grid.add(toField, 1, 3);

        // MAP API

        // Distance can be calculated
        // Duration will be calculated

        Label trType = new Label("Transport Type:");
        grid.add(trType, 0, 4);

        final ComboBox transportComboBox = new ComboBox();
        transportComboBox.getItems().addAll(
                "AUTO",
                "PEDESTRIAN",
                "BICYCLE"
        );
        grid.add(transportComboBox, 1, 4);

        // final button our add tour button
        Button btn = new Button("Add Tour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);

        // on click create a new Tour Object with all the input from user
        btn.setOnAction(e -> {
            // get values of input
            String title = tourNameField.getText();
            String start = fromField.getText();
            String end = toField.getText();
            String transport = (String) transportComboBox.getValue();

            // send strings as get parameters to the map api
            // for the get request u need the key ( I created an account on the map api website ):
            // key = FVrDjDoWMVJKy6xoLfkNOVoafr6Z7XoP
            try {
                sendRequest(start, end, transport);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // create Tour Object and create Request to add a new Tour to DB
        });

        stage.setScene(scene);
        stage.show();
    }

    public void sendRequest(String start, String end, String transport) throws IOException {
        // URL for the MAP API
        String url = "https://www.mapquestapi.com/directions/v2/route?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&from="+start+"&to="+end+"&transportMode="+transport+"&routeType="+"";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // set it as a GET request
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        // add that to the Logger instead of a system out print
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        // get the Response => response is a JSON object
    }

}
