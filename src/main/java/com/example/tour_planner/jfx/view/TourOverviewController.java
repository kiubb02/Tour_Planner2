package com.example.tour_planner.jfx.view;


import com.example.tour_planner.layers.business.TourServiceImpl;
import com.example.tour_planner.layers.model.Tour;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;


public class TourOverviewController {
    @FXML
    public ArrayList<Tour> tourList;
    @FXML
    public VBox TourDetails;
    @FXML
    public HBox Tours;
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


    public void onButtonAdd(ActionEvent actionEvent) {
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

    public void onButtonRemove(ActionEvent actionEvent) {
        Object selectedTour = mediaOverviewViewModel.deleteTour(myListView);
        myListView.getItems().remove(selectedTour);
        initialize();
    }

    // show tour details on click
    public void showTour(MouseEvent mouseEvent) throws FileNotFoundException {
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
    }

    public void TourLogsView(){
        // horizontal box with title and add/delete button
        HBox horizontal = new HBox();
        // maybe put that in a gridpane
        final Label label = new Label("Tour Logs:      ");
        label.setFont(new Font("Arial", 20));

        Button add = new Button("+");
        Button delete = new Button("-");
        Button edit = new Button("Edit");

        add.setOnAction(this::createTourLog);
        delete.setOnAction(this::deleteTourLog);
        edit.setOnAction(this::editTourLog);

        horizontal.getChildren().add(label);
        horizontal.getChildren().add(add);
        horizontal.getChildren().add(delete);
        horizontal.getChildren().add(edit);


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

        TourDetails.getChildren().add(horizontal);
        TourDetails.getChildren().add(tableView);
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
        HBox horizontal = new HBox();

        FileInputStream input = new FileInputStream("src/main/java/com/example/tour_planner/utils/maps/"+details.getName()+"_map.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text(details.getName());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text popularity = new Text("Popularity: " + mediaOverviewViewModel.getPopularity(details.getName()));
        grid.add(popularity, 0, 1);

        Text friendly = new Text("Childfriendly: " + mediaOverviewViewModel.getFriendly(details.getName()));
        grid.add(friendly, 1, 1);


        Label from = new Label("From:");
        grid.add(from, 0, 2);
        Text fromField = new Text(details.getFrom());
        grid.add(fromField, 1, 2);

        Label toLabel = new Label("To:");
        grid.add(toLabel, 0, 3);
        Text to = new Text(details.getTo());
        grid.add(to, 1, 3);

        Label transportLabel = new Label("Transport:");
        grid.add(transportLabel, 0, 4);
        Text transport = new Text(details.getTransport());
        grid.add(transport, 1, 4);

        Label distlabel = new Label("Distance:");
        grid.add(distlabel, 0, 5);
        Double distance = details.getDistance();
        Text dist = new Text(distance.toString());
        grid.add(dist, 1, 5);

        Label durLabel = new Label("Duration:");
        grid.add(durLabel, 0, 6);
        Text dur = new Text(details.getDuration());
        grid.add(dur, 1, 6);

        Label descLabel = new Label("Description:");
        grid.add(descLabel, 0, 7);
        Text desc = new Text(details.getDescription());
        grid.add(desc, 1, 7);

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

        menu.getItems().add(itemEdit);
        menu.getItems().add(itemExport);
        menu.getItems().add(itemTourReport);
        menu.getItems().add(itemSummary);

        menuBar.getMenus().add(menu);

        grid.add(menuBar, 2, 8);

        // first add image to Hbox
        horizontal.getChildren().add(imageView);
        // Then add the grid
        horizontal.getChildren().add(grid);

        TourDetails.getChildren().add(horizontal);
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