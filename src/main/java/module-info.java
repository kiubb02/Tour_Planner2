module com.example.tour_planner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires org.json;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires json.simple;


    opens com.example.tour_planner to javafx.fxml;

    exports com.example.tour_planner;
    exports com.example.tour_planner.jfx.view;
    opens com.example.tour_planner.jfx.view to javafx.fxml;
    exports com.example.tour_planner.jfx.viewmodel;
    opens com.example.tour_planner.jfx.viewmodel to javafx.fxml;
    exports com.example.tour_planner.layers.model;
    opens com.example.tour_planner.layers.model to javafx.fxml;
}