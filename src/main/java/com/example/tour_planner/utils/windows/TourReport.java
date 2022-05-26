package com.example.tour_planner.utils.windows;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TourReport
{
    private TourDaoImpl tourDao = new TourDaoImpl();


    public void reportForm(Tour details){

        VBox vBox = new VBox();

        //current scene of program
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);

        Stage stage = new Stage();
        stage.setTitle("Report Tour: " + details.getName());

        Text scenetitle = new Text("Are you sure you want to report " + details.getName() + "?");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 13));
        grid.add(scenetitle, 0, 0, 2, 1);

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


        hbBtn.getChildren().add(btnY);

        hbBtn.getChildren().add(btnN);

        stage.setScene(scene);
        stage.show();
    }
}
