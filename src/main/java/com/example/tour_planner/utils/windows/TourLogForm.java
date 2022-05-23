package com.example.tour_planner.utils.windows;

import com.example.tour_planner.model.TourLogImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;

public class TourLogForm {

    private DatePicker dateTime;
    int InputError = 0;
    int difficulty = 0;
    int rating = 0;
    Date date;

    public void showForm(ListView myList) {
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

        Text scenetitle = new Text("New Tour Log: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        dateTime = new DatePicker();

        Label dateLable = new Label("Date/Time:");
        grid.add(dateLable, 0, 1);
        grid.add(dateTime, 1, 1);

        Label totalLable = new Label("Total Time:");
        grid.add(totalLable, 0, 2);

        TextField totalField = new TextField();
        totalField.setPromptText("2.5");
        grid.add(totalField, 1, 2);

        // Radio Button for rating
        Label rat = new Label("Rating:");
        grid.add(rat, 0, 3);

        // Difficulty ComboBox
        Label diff = new Label("Difficulty:");
        grid.add(diff, 1, 3);


        final ComboBox cm1 = new ComboBox();
        cm1.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        grid.add(cm1, 0, 4);

        final ComboBox cm2 = new ComboBox();
        cm2.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        grid.add(cm2, 1, 4);


        Label commentLabel = new Label("Comment:");
        grid.add(commentLabel, 0, 5);

        TextField commentField = new TextField();
        grid.add(commentField, 1, 5);

        // add a button to actually add
        Button btn = new Button("Create");
        grid.add(btn, 1, 6);

        // for the error Messages
        final Text actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        grid.add(actiontarget, 0, 6);

        btn.setOnAction(e -> {
            LocalDate localDate = dateTime.getValue();
            if(localDate == null){
                actiontarget.setText("Choose Date");
                InputError = 1;
            } else {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date = Date.from(instant);
            }
            String totalTime = totalField.getText();
            if(totalTime.equals("")){
                actiontarget.setText("Enter total Time");
                InputError = 1;
            }
            String rat1 = (String) cm1.getValue();
            if(rat1 == null){
                actiontarget.setText("Choose Rating");
                InputError = 1;
            } else {
                rating = Integer.parseInt(rat1);
            }
            String difficulty1 = (String) cm2.getValue();
            if(difficulty1 == null){
                actiontarget.setText("Choose Difficulty");
                InputError = 1;
            } else {
                difficulty = Integer.parseInt(difficulty1);
            }
            String comment = commentField.getText();
            if(comment.equals("")){
                actiontarget.setText("Enter Comment");
                InputError = 1;
            }

            System.out.println(totalTime);

            // create Tour Log instant
            if(InputError == 0){
                Object selectedTour = myList.getSelectionModel().getSelectedItem();
                TourLogImpl newLog = new TourLogImpl(date, comment, difficulty, totalTime, rating, selectedTour.toString());
                newLog.createLog();
            }
        });


        stage.setScene(scene);
        stage.show();
    }
}
