package com.example.tour_planner.utils.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Tour class
import com.example.tour_planner.model.Tour;

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

        // Distance can be calculated

        Label trType = new Label("Transport Type:");
        grid.add(trType, 0, 4);

        TextField trTypeField = new TextField();
        grid.add(trTypeField, 1, 4);

        Label dur = new Label("Duration:");
        grid.add(dur, 0, 5);

        TextField durField = new TextField();
        grid.add(durField, 1, 5);

        // final button our add tour button
        Button btn = new Button("Add Tour");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);

        // on click create a new Tour Object with all the input from user
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

            }
        });

        stage.setScene(scene);
        stage.show();
    }

}
