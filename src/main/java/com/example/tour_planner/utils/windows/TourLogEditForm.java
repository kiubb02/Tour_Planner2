package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.model.TourLogImpl;
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

import java.util.Date;

public class TourLogEditForm {

    private DatePicker dateTime;
    int InputError = 0;
    Date date;

    public void showForm(TourLogImpl log){
        VBox vBox = new VBox(); //for now
        //current scene of program
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);

        Stage stage = new Stage();
        stage.setTitle("Tour Log Form");

        Text scenetitle = new Text("Edit Tour Log: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        dateTime = new DatePicker();

        Label dateLable = new Label("Date/Time:");
        grid.add(dateLable, 0, 1);
        grid.add(dateTime, 1, 1);

        Label titleLabel = new Label("Title");
        grid.add(titleLabel, 0, 2);

        TextField titleField = new TextField();
        titleField.setPromptText(log.getTitle().getValue());
        grid.add(titleField, 1, 2);

        Label totalLable = new Label("Total Time:");
        grid.add(totalLable, 0, 3);

        TextField totalField = new TextField();
        totalField.setPromptText(log.getTotalTime().getValue());
        grid.add(totalField, 1, 3);

        // Radio Button for rating
        Label rat = new Label("Rating:");
        grid.add(rat, 0, 4);

        // Difficulty ComboBox
        Label diff = new Label("Difficulty:");
        grid.add(diff, 1, 4);


        final ComboBox cm1 = new ComboBox();
        cm1.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        grid.add(cm1, 0, 5);

        final ComboBox cm2 = new ComboBox();
        cm2.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        grid.add(cm2, 1, 5);


        Label commentLabel = new Label("Comment:");
        grid.add(commentLabel, 0, 6);

        TextField commentField = new TextField();
        commentField.setPromptText(log.getComment().getValue());
        grid.add(commentField, 1, 6);

        // add a button to actually add
        Button btn = new Button("Edit");
        grid.add(btn, 1, 7);

        // for the error Messages
        final Text actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        grid.add(actiontarget, 0, 7);

        stage.setScene(scene);
        stage.show();
    }

}
