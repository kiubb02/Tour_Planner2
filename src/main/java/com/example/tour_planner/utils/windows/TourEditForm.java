// Form for when a Tour gets modified

package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.business.TourLogServiceImpl;
import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.api.mapAPI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

        Text scenetitle = new Text("Edit Tour: ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label tourName = new Label("Title:");
        grid.add(tourName, 0, 1);

        TextField tourNameField = new TextField();
        tourNameField.setPromptText(details.getName());
        grid.add(tourNameField, 1, 1);

        Label from = new Label("From:");
        grid.add(from, 0, 2);

        TextField fromField = new TextField();
        fromField.setPromptText(details.getFrom());
        grid.add(fromField, 1, 2);

        Label to = new Label("To:");
        grid.add(to, 0, 3);

        TextField toField = new TextField();
        toField.setPromptText(details.getTo());
        grid.add(toField, 1, 3);

        // Description in form of a textfield
        Label desc = new Label("Description:");
        grid.add(desc, 0, 4);

        TextField descField = new TextField();
        descField.setPromptText(details.getDescription());
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
        Button btn = new Button("Edit Tour");
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

        stage.setScene(scene);
        stage.show();
    }

}