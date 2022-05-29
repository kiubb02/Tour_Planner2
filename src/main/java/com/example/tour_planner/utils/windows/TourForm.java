// TODO: add language strings

package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.business.TourLogService;
import com.example.tour_planner.layers.business.TourLogServiceImpl;
import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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

    // Input check through Business
    ArrayList inputs = new ArrayList();
    TourServiceImpl service = new TourServiceImpl();

    //build a new scene to open as a pop up form
    public void showForm(){

        // --- GRID --- //
        //current scene of program
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(13);
        grid.setVgap(13);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: linear-gradient(to right bottom, lightpink, mistyrose); -fx-border-radius: 1.5em; -fx-padding:0.5em;");

        // --- SCENE --- //
        Scene scene = new Scene(grid, 400, 400);

        // --- STAGE --- //
        Stage stage = new Stage();
        stage.setTitle("Tour Form");

        // --- VBOX --- //
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: whitesmoke; -fx-padding: 3.5em; -fx-background-radius: 40px; -fx-background-repeat: no-repeat; -fx-background-position: center center;");
        grid.add(box, 1, 3);

        // --- HBOX --- //
        HBox hbox_1 = new HBox(10);
        hbox_1.setAlignment(Pos.CENTER_LEFT);
        HBox hbox_2 = new HBox(10);
        hbox_2.setAlignment(Pos.CENTER_LEFT);
        HBox hbox_3 = new HBox(10);
        hbox_3.setAlignment(Pos.CENTER_LEFT);
        HBox hbox_4 = new HBox(10);
        hbox_4.setAlignment(Pos.CENTER_LEFT);
        HBox hbox_5 = new HBox(10);
        hbox_5.setAlignment(Pos.CENTER_LEFT);
        HBox hbox_6 = new HBox(10);
        hbox_6.setAlignment(Pos.BOTTOM_RIGHT);


        // --- FIELDS --- //
        Text scenetitle = new Text("New Tour: ");
        scenetitle.setStyle("-fx-font: normal bold 2.2em 'Courier New';");
        box.getChildren().add(scenetitle);

        // 1
        Label tourName = new Label("Title:");
        TextField tourNameField = new TextField();
        hbox_1.getChildren().add(tourName);
        hbox_1.getChildren().add(tourNameField);
        box.getChildren().add(hbox_1);

        // 2
        Label from = new Label("From:");
        TextField fromField = new TextField();
        hbox_2.getChildren().add(from);
        hbox_2.getChildren().add(fromField);
        box.getChildren().add(hbox_2);

        // 3
        Label to = new Label("To:");
        TextField toField = new TextField();
        hbox_3.getChildren().add(to);
        hbox_3.getChildren().add(toField);
        box.getChildren().add(hbox_3);

        // 4
        // Description in form of a textfield
        Label desc = new Label("Description:");
        TextField descField = new TextField();
        hbox_4.getChildren().add(desc);
        hbox_4.getChildren().add(descField);
        box.getChildren().add(hbox_4);

        // MAP API

        // Distance can be calculated
        // Duration will be calculated

        // 5
        Label trType = new Label("Transport Type:");
        final ComboBox transportComboBox = new ComboBox();
        transportComboBox.getItems().addAll(
                "AUTO",
                "PEDESTRIAN",
                "BICYCLE"
        );
        hbox_5.getChildren().add(trType);
        hbox_5.getChildren().add(transportComboBox);
        box.getChildren().add(hbox_5);

        // final button our add tour button
        Button btn = new Button("+");
        btn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });


        final Text actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        hbox_6.getChildren().add(actiontarget);
        hbox_6.getChildren().add(btn);
        box.getChildren().add(hbox_6);

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