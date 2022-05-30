package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.Collection;

public class TourReport
{
    private TourDaoImpl tourDao = new TourDaoImpl();


    public void reportForm(Tour details){
        //current scene of program
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: linear-gradient(to right bottom, lightpink, mistyrose); -fx-border-radius: 1.5em; -fx-padding:1.5em; -fx-border-width:1em;");


        Scene scene = new Scene(grid, 300, 275);

        Stage stage = new Stage();
        stage.setTitle("Report Tour: " + details.getName());

        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: whitesmoke; -fx-padding: 0.5em; -fx-background-radius: 40px; -fx-background-image: url('main/java/com/example/tour_planner/utils/css/pink-clouds.jpg'); -fx-background-repeat: no-repeat; -fx-background-position: center center;");
        grid.add(box, 1, 4);


        Text scenetitle = new Text("Are you sure you want to report\n" + details.getName() + "?");
        scenetitle.setStyle("-fx-font: normal bold 1.2em 'Courier New';");
        //scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
        grid.add(scenetitle, 0, 0, 2, 1);

        box.getChildren().add(scenetitle);

        Button btnN = new Button("No");
        btnN.setAlignment(Pos.BOTTOM_RIGHT);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        grid.add(hbBtn, 1, 6);

        // WINDOW CLOSE IF BUTTONS CLICKED
        btnN.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });

        Button btnY = new Button("Yes");
        btnY.setAlignment(Pos.BOTTOM_LEFT);

        // WINDOW CLOSE IF BUTTONS CLICKED
        btnY.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tourDao.reportTour(details.getName());
                stage.close();
            }
        });

        btnY.setStyle("-fx-background-color: pink; -fx-text-fill: white;");
        btnN.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");


        hbBtn.getChildren().add(btnY);

        hbBtn.getChildren().add(btnN);

        stage.setScene(scene);
        stage.show();
    }
}
