package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.business.TourLogService;
import com.example.tour_planner.layers.business.TourLogServiceImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLog;
import com.example.tour_planner.layers.model.TourLogImpl;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TourLogEditForm {

    private DatePicker dateTime;
    int InputError = 0;
    Date date;
    int difficulty = 0;
    int rating = 0;
    ArrayList inputs = new ArrayList();
    TourLogServiceImpl service = new TourLogServiceImpl();

    public void showForm(TourLogImpl log, String tour){
        // --- GRID --- //
        //current scene of program
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(13);
        grid.setVgap(13);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: linear-gradient(to right bottom, lightpink, mistyrose); -fx-border-radius: 1.5em; -fx-padding:0.5em;");

        // --- SCENE --- //
        Scene scene = new Scene(grid, 400, 430);

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
        hbox_6.setAlignment(Pos.CENTER_LEFT);
        HBox hbox_7 = new HBox(10);
        hbox_7.setAlignment(Pos.BOTTOM_RIGHT);


        // --- FIELDS --- //
        Text scenetitle = new Text("New Tour Log: ");
        scenetitle.setStyle("-fx-font: normal bold 2.2em 'Courier New';");
        box.getChildren().add(scenetitle);

        // 1
        dateTime = new DatePicker();
        Label dateLable = new Label("Date/Time:");
        hbox_1.getChildren().add(dateLable);
        hbox_1.getChildren().add(dateTime);
        box.getChildren().add(hbox_1);

        // 2
        Label titleLabel = new Label("Title");
        TextField titleField = new TextField();
        titleField.setPromptText(log.getTitle().getValue());
        hbox_2.getChildren().add(titleLabel);
        hbox_2.getChildren().add(titleField);
        box.getChildren().add(hbox_2);

        // 3
        Label totalLable = new Label("Total Time:");
        TextField totalField = new TextField();
        totalField.setPromptText(String.valueOf(log.getTotalTime().getValue()));
        hbox_3.getChildren().add(totalLable);
        hbox_3.getChildren().add(totalField);
        box.getChildren().add(hbox_3);

        // 4
        // Radio Button for rating
        Label rat = new Label("Rating:");
        final ComboBox cm1 = new ComboBox();
        cm1.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        hbox_4.getChildren().add(rat);
        hbox_4.getChildren().add(cm1);
        box.getChildren().add(hbox_4);

        // 5
        // Difficulty ComboBox
        Label diff = new Label("Difficulty:");
        final ComboBox cm2 = new ComboBox();
        cm2.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        hbox_5.getChildren().add(diff);
        hbox_5.getChildren().add(cm2);
        box.getChildren().add(hbox_5);

// 6
        Label commentLabel = new Label("Comment:");
        TextField commentField = new TextField();
        commentField.setPromptText(log.getComment().getValue());
        hbox_6.getChildren().add(commentLabel);
        hbox_6.getChildren().add(commentField);
        box.getChildren().add(hbox_6);

        // add a button to actually add
        Button btn = new Button("+");
        btn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });

        // for the error Messages
        final Text actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);

        btn.setOnAction(e -> {
            String title = titleField.getText();
            LocalDate localDate = dateTime.getValue();
            if(localDate == null){
                actiontarget.setText("Choose Date");
                InputError = 1;
            } else {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date = Date.from(instant);
            }
            Float totalTime = Float.valueOf(totalField.getText());
            String rat1 = (String) cm1.getValue();
            String difficulty1 = (String) cm2.getValue();
            String comment = commentField.getText();

            inputs.add(title);
            inputs.add(localDate);
            inputs.add(totalTime);
            inputs.add(rat1);
            inputs.add(difficulty1);
            inputs.add(comment);
            inputs.add(log.getTitle().getValue()); // old title

            String message = service.errorMessage(inputs);
            if(!message.equals("")){
                actiontarget.setText(message);
            } else {
                if(InputError == 0){
                    // modify a TourLog
                    difficulty = Integer.parseInt(difficulty1);
                    rating = Integer.parseInt(rat1);
                    TourLogImpl newLog = new TourLogImpl(title, date, comment, difficulty, totalTime, rating, tour);
                    newLog.modifyLog(newLog, log.getTitle().getValue());
                }
            }

        });
        hbox_7.getChildren().add(actiontarget);
        hbox_7.getChildren().add(btn);
        box.getChildren().add(hbox_7);

        stage.setScene(scene);
        stage.show();
    }

}