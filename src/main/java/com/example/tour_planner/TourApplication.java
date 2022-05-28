package com.example.tour_planner;

import com.example.tour_planner.FXMLDependencyInjection;
import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class TourApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLDependencyInjection.load("MainWindow.fxml", Locale.GERMAN );  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root);
        //File file = new File("src/main/java/com/example/tour_planner/utils/css/StyleSheet.css");
        //scene.getStylesheets().clear();
        //scene.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));
        //scene.getStylesheets().add(this.getClass().getResource("/utils/css/StyleSheet.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tour planner");
        primaryStage.show();
    }
}
