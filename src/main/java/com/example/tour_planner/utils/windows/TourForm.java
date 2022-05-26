// TODO: add language strings

package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.business.TourLogService;
import com.example.tour_planner.layers.business.TourLogServiceImpl;
import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Tour class
import com.example.tour_planner.utils.api.mapAPI;

import java.io.IOException;
import java.util.ArrayList;

//JSONObject
// TODO: add JSONObject as valid import


public class TourForm {

    // create a Map API Object
    mapAPI map = new mapAPI();
    TourDaoImpl handler = new TourDaoImpl();

    // Input check through Business
    ArrayList inputs = new ArrayList();
    TourServiceImpl service = new TourServiceImpl();

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

        // Description in form of a textfield
        Label desc = new Label("Description:");
        grid.add(desc, 0, 4);

        TextField descField = new TextField();
        grid.add(descField, 1, 4);

        // MAP API

        // Distance can be calculated
        // Duration will be calculated

        Label trType = new Label("Transport Type:");
        grid.add(trType, 0, 5);

        final ComboBox transportComboBox = new ComboBox();
        transportComboBox.getItems().addAll(
                "AUTO",
                "PEDESTRIAN",
                "BICYCLE"
        );
        grid.add(transportComboBox, 1, 5);

        // final button our add tour button
        Button btn = new Button("Add Tour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);

        final Text actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        grid.add(actiontarget, 0, 6);

        // on click create a new Tour Object with all the input from user
        btn.setOnAction(e -> {
            // get values of input
            String title = tourNameField.getText();
            String description = descField.getText();
            String start = fromField.getText();
            String end = toField.getText();
            String transport = (String) transportComboBox.getValue();
            // if one of those is empty ==> print error message and dont send to map api

            inputs.add(title);
            inputs.add(description);
            inputs.add(start);
            inputs.add(end);
            inputs.add(transport);
            inputs.add("create144");

            String errorMessage = service.errorMessage(inputs);
            if(!errorMessage.equals("")){
                actiontarget.setText(errorMessage);
            } else {
                try {
                    int status = mapAPI.sendRequest("old", start, end, transport, title, description, "create");
                    if (status == 0) {
                        // print an error message
                        actiontarget.setText("Enter valid Destinations");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            // create Tour Object and create Request to add a new Tour to DB
        });

        stage.setScene(scene);
        stage.show();
    }

}