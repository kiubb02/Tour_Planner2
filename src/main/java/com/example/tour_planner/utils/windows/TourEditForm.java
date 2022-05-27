// Form for when a Tour gets modified

package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.business.TourLogServiceImpl;
import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.api.mapAPI;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TourEditForm {

    ArrayList inputs = new ArrayList();
    TourServiceImpl service = new TourServiceImpl();

    //build a new scene to open as a pop up form
    public void showForm(Tour details){
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
        box.setStyle("-fx-background-color: whitesmoke; -fx-padding: 3.5em; -fx-background-radius: 40px; -fx-background-image: url('main/java/com/example/tour_planner/utils/css/pink-clouds.png'); -fx-background-repeat: no-repeat; -fx-background-position: center center;");
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

        // --- FIELDS --- //
        Text scenetitle = new Text("Edit Tour: ");
        scenetitle.setStyle("-fx-font: normal bold 2.2em 'Courier New';");
        box.getChildren().add(scenetitle);

        // 1
        Label tourName = new Label("Title:");
        TextField tourNameField = new TextField();
        tourNameField.setPromptText(details.getName());
        hbox_1.getChildren().add(tourName);
        hbox_1.getChildren().add(tourNameField);
        box.getChildren().add(hbox_1);

        // 2
        Label from = new Label("From:");
        TextField fromField = new TextField();
        fromField.setPromptText(details.getFrom());
        hbox_2.getChildren().add(from);
        hbox_2.getChildren().add(fromField);
        box.getChildren().add(hbox_2);

        // 3
        Label to = new Label("To:");
        TextField toField = new TextField();
        toField.setPromptText(details.getTo());
        hbox_3.getChildren().add(to);
        hbox_3.getChildren().add(toField);
        box.getChildren().add(hbox_3);

        // 4
        // Description in form of a textfield
        Label desc = new Label("Description:");
        TextField descField = new TextField();
        descField.setPromptText(details.getDescription());
        hbox_4.getChildren().add(desc);
        hbox_4.getChildren().add(descField);
        box.getChildren().add(hbox_4);

        // MAP API

        // Distance can be calculated
        // Duration will be calculated

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
        Button btn = new Button("edit");
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

        // on click create a new Tour Object with all the input from user
        btn.setOnAction(e -> {
            // get values of input
            String title = tourNameField.getText();
            String description = descField.getText();
            String start = fromField.getText();
            String end = toField.getText();
            String transport = (String) transportComboBox.getValue();

            inputs.add(title);
            inputs.add(description);
            inputs.add(start);
            inputs.add(end);
            inputs.add(transport);
            inputs.add("edit");

            ArrayList output = service.validateInputEdit(inputs, details);
            if(output == null) {
                actiontarget.setText("Title already exists");
            } else {
                try {
                    int status = mapAPI.sendRequest(details.getName(), output.get(2).toString(), output.get(3).toString(), output.get(4).toString(), output.get(0).toString(), output.get(1).toString(), "edit");
                    if (status == 0) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Enter valid Destinations");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
        hbox_6.getChildren().add(btn);
        box.getChildren().add(hbox_6);


        stage.setScene(scene);
        stage.show();
    }

}