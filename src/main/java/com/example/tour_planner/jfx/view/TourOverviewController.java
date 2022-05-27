package com.example.tour_planner.jfx.view;


import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLog;
import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import com.example.tour_planner.utils.windows.*;
import com.example.tour_planner.jfx.viewmodel.TourOverviewViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;


public class TourOverviewController {
    @FXML
    public ArrayList<Tour> tourList;
    @FXML
    public HBox TourDetails;
    @FXML
    public HBox ButtonEdits = addButtons();
    @FXML
    public HBox Tours;
    public VBox tourListFxml;
    @FXML
    private ListView myListView = new ListView<>();
    @FXML
    private TableView<TourLogImpl> tableView = new TableView<TourLogImpl>();
    @FXML
    private ObservableList<TourLogImpl> data = FXCollections.observableArrayList();
    @FXML
    protected ListProperty<Tour> listProperty = new SimpleListProperty<>();
    @FXML
    private final TourOverviewViewModel mediaOverviewViewModel;

    // FUNCTIONS //

    public TourOverviewController(TourOverviewViewModel mediaOverviewViewModel) {
        this.mediaOverviewViewModel = mediaOverviewViewModel;
    }

    public TourOverviewViewModel getMediaOverviewViewModel() {
        return mediaOverviewViewModel;
    }

    @FXML
    void initialize() {
        // get the new Items and show them in there
        tourList = mediaOverviewViewModel.getTourList();
        System.out.println(tourList);
        myListView.itemsProperty().bind(listProperty);
        if(tourList != null) listProperty.set(FXCollections.observableArrayList(tourList));
        //myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    public void onButtonAdd() {
        TourForm form = new TourForm();
        form.showForm();
        initialize();
    }

    public void onButtonEdit(ActionEvent actionEvent){
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        TourEditForm form = new TourEditForm();
        form.showForm(details);
        initialize();
    }

    public void onButtonReport(ActionEvent actionEvent)
    {
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        TourReport report = new TourReport();
        report.reportForm(details);
        initialize();
    }

    public void onButtonRemove() {
        Object selectedTour = mediaOverviewViewModel.deleteTour(myListView);
        myListView.getItems().remove(selectedTour);
        initialize();
    }

    // show tour details on click
    public void showTour(MouseEvent mouseEvent) throws FileNotFoundException {
        ButtonEdits.getChildren().clear();
        // clear window
        TourDetails.getChildren().clear();
        // clear table
        if(tableView.getItems() != null) tableView.getItems().clear();
        tableView.getColumns().clear();
        if(data != null) data.clear();
        data = mediaOverviewViewModel.getTourLogList(myListView);
        Tour details = mediaOverviewViewModel.getDetails(myListView);
        tourView(details);
        TourLogsView();
        //addButtons();
    }

    public void addButtons()
    {
        HBox box = new HBox(10);
        Button add = new Button("+");
        add.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white; -fx-padding: 0.5em; -fx-margin: 5em;");
        add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onButtonAdd();
            }
        });
        Button delete = new Button("-");
        delete.setStyle("-fx-background-color: mistyrose; -fx-text-fill: white; -fx-padding: 0.5em; -fx-margin: 5em;");
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onButtonRemove();
            }
        });
        box.getChildren().add(add);
        box.getChildren().add(delete);
    }


    public void TourLogsView(){
        // --- CONTAINER BOXES --- //
        // horizontal box with title and add/delete button
        VBox LogContainer = new VBox();
        HBox LogDetailsContainer = new HBox();

        LogContainer.setStyle("-fx-background-color: linear-gradient(to right bottom, lightpink, mistyrose); -fx-border-radius: 1.5em; -fx-margin: 2em; -fx-padding:3.5em;");
        // maybe put that in a gridpane
        final Label label = new Label("Tour Logs:      ");
        label.setStyle("-fx-font: normal bold 2.2em 'Courier New';");

        Button add = new Button("+");
        Button delete = new Button("-");
        Button edit = new Button("Edit");

        add.setOnAction(this::createTourLog);
        add.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        delete.setOnAction(this::deleteTourLog);
        delete.setStyle("-fx-background-color: mistyrose; -fx-text-fill: white;");
        edit.setOnAction(this::editTourLog);
        edit.setStyle("-fx-background-color: lightpink; -fx-text-fill: white;");

        LogDetailsContainer.getChildren().add(label);
        LogDetailsContainer.getChildren().add(add);
        LogDetailsContainer.getChildren().add(delete);
        LogDetailsContainer.getChildren().add(edit);
        LogContainer.getChildren().add(LogDetailsContainer);

        // date/time, comment, difficulty, total time, and rating
        TableColumn name = new TableColumn("Title");
        name.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Date>("title"));
        TableColumn date = new TableColumn("Date/Time");
        date.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Date>("dateTime"));
        TableColumn comment = new TableColumn("Comment");
        comment.setCellValueFactory(new PropertyValueFactory<TourLogImpl, String>("comment"));
        TableColumn difficulty = new TableColumn("Difficulty");
        difficulty.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Integer>("difficulty"));
        TableColumn time = new TableColumn("Time");
        time.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Float>("totalTime"));
        TableColumn rating = new TableColumn("Rating");
        rating.setCellValueFactory(new PropertyValueFactory<TourLogImpl, Integer>("rating"));

        tableView.setItems(data);
        tableView.getColumns().addAll(name, date, comment, difficulty, time, rating);
        LogContainer.getChildren().add(tableView);
        TourDetails.getChildren().add(LogContainer);
    }

    private void editTourLog(ActionEvent actionEvent) {
        TourLogImpl selectedLog = mediaOverviewViewModel.getTourLog(tableView);
        Object selectedTour = mediaOverviewViewModel.getTour(myListView);
        TourLogEditForm form = new TourLogEditForm();
        form.showForm(selectedLog, selectedTour.toString());
        tableView.refresh();
    }

    private void deleteTourLog(ActionEvent actionEvent) {
        mediaOverviewViewModel.deleteTourLog(tableView);
        tableView.refresh();
    }

    private void createTourLog(ActionEvent actionEvent) {
        TourLogForm form = new TourLogForm();
        form.showForm(myListView);
        tableView.refresh();
    }

    public void tourView(Tour details) throws FileNotFoundException {
        // --- CONTAINER BOXES --- //
        VBox TourContainer = new VBox();
        TourContainer.setStyle("-fx-background-color: linear-gradient(to right bottom, lightpink, mistyrose); -fx-border-radius: 1.5em; -fx-margin: 2em; -fx-padding:3.5em;");
        HBox TourDetailsContainer = new HBox();
        HBox OptionBtn = new HBox();
        OptionBtn.setStyle("-fx-margin: 1em;");

        // --- MAP --- //
        FileInputStream input = new FileInputStream("src/main/java/com/example/tour_planner/utils/maps/"+details.getName()+"_map.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);
        imageView.setStyle("-fx-margin: 2em;");
        TourContainer.getChildren().add(imageView);

        // --- TEXT FIELDS --- //
        Text scenetitle = new Text("\n"+details.getName());
        scenetitle.setStyle("-fx-font: normal bold 2.2em 'Courier New';");
        TourContainer.getChildren().add(scenetitle);

        Text popularity = new Text("Popularity: " + mediaOverviewViewModel.getPopularity(details.getName()) + "\t");
        popularity.setStyle("-fx-font: normal 1.2em 'Courier New';");
        Text friendly = new Text("Childfriendly: " + mediaOverviewViewModel.getFriendly(details.getName()));
        friendly.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourDetailsContainer.getChildren().add(popularity);
        TourDetailsContainer.getChildren().add(friendly);
        TourContainer.getChildren().add(TourDetailsContainer);

        Label from = new Label("From: " + details.getFrom());
        from.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourContainer.getChildren().add(from);

        Label toLabel = new Label("To: " + details.getTo());
        toLabel.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourContainer.getChildren().add(toLabel);

        Label transportLabel = new Label("Transport: " + details.getTransport());
        transportLabel.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourContainer.getChildren().add(transportLabel);

        Label distlabel = new Label("Distance: " + details.getDistance());
        distlabel.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourContainer.getChildren().add(distlabel);

        Label durLabel = new Label("Duration: " + details.getDuration());
        durLabel.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourContainer.getChildren().add(durLabel);

        Label descLabel = new Label("Description: " + details.getDescription());
        descLabel.setStyle("-fx-font: normal 1.2em 'Courier New';");
        TourContainer.getChildren().add(descLabel);

        // Instead of buttons there is an Option drop down
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");
        MenuItem itemEdit = new MenuItem("Edit");
        itemEdit.setOnAction(this::onButtonEdit);
        MenuItem itemExport = new MenuItem("Export");
        itemExport.setOnAction(this::onButtonExport);
        MenuItem itemTourReport = new MenuItem("Tour Report");
        itemTourReport.setOnAction(this::onButtonTourReport);
        MenuItem itemSummary = new MenuItem("Summary");
        itemSummary.setOnAction(this::onButtonSummary);
        MenuItem reportTour = new MenuItem("Report Tour");
        reportTour.setOnAction(this::onButtonReport);

        menu.getItems().add(itemEdit);
        menu.getItems().add(itemExport);
        menu.getItems().add(itemTourReport);
        menu.getItems().add(itemSummary);
        menu.getItems().add(reportTour);

        menuBar.getMenus().add(menu);
        menuBar.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");

        OptionBtn.getChildren().add(menuBar);
        TourContainer.getChildren().add(OptionBtn);
        TourDetails.getChildren().add(TourContainer);
    }

    private void onButtonSummary(ActionEvent actionEvent) {
        mediaOverviewViewModel.createSummary(myListView);
    }

    private void onButtonTourReport(ActionEvent actionEvent) {
        mediaOverviewViewModel.createTourReport(myListView);
    }

    private void onButtonExport(ActionEvent actionEvent) {
        try{
            mediaOverviewViewModel.exportTour(myListView);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}