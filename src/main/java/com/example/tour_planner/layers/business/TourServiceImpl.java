package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.layers.model.TourLogImpl;
import com.example.tour_planner.utils.api.mapAPI;

import java.io.*;

import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;
import com.itextpdf.io.image.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.*;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.MalformedURLException;
import java.util.ArrayList;
import com.itextpdf.layout.element.Image;

public class TourServiceImpl implements TourService{

    TourDaoImpl handler = new TourDaoImpl();
    TourLogDaoImpl handlerLog = new TourLogDaoImpl();
    TourLogServiceImpl serviceLog = new TourLogServiceImpl();

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    @Override
    public String errorMessage(ArrayList input) {
        ArrayList error = validateInput(input);

        if(error.get(0).equals("title")) return "Enter valid Title";
        if(error.get(0).equals("desc")) return "Enter Description";
        if(error.get(0).equals("dest")) return "Enter valid Destination";
        if(error.get(0).equals("trans")) return "Enter Transport";

        return "";
    }

    @Override
    public ArrayList validateInput(ArrayList inputs) {
        ArrayList error = new ArrayList<>();

        if(validateTitle(inputs.get(0).toString(), inputs.get(5).toString()) == 0) {
            error.add("title");
            error.add(0);
        }
        if(inputs.get(1).toString().equals("")){
            error.add("desc");
            error.add(0);
        }
        if(inputs.get(2).toString().equals("") || inputs.get(3).toString().equals("")){
            error.add("dest");
            error.add(0);
        }
        if(inputs.get(4).toString().equals("")){
            error.add("trans");
            error.add(0);
        }

        error.add("none");
        error.add(1);
        return error;
    }

    @Override
    public int validateTitle(String title, String oldTitle) {

        if(title.equals("")) return 0;
        if(title.equals(oldTitle) && !oldTitle.equals("create144")){
            return 1;
        } else {
            if(handler.getDetails(title) != null) return 0;
        }

        return 1;
    }

    @Override
    public ArrayList validateInputEdit(ArrayList inputs, Tour details) {
        ArrayList output = new ArrayList();

        if(handler.getDetails(inputs.get(0).toString()) != null) return output;
        if(inputs.get(0).toString().equals("")){
            output.add(details.getName());
        } else {
            output.add(inputs.get(0).toString());
        }
        if(inputs.get(1).toString().equals("")) output.add(details.getDescription());
        if(inputs.get(2).toString().equals("")) output.add(details.getFrom());
        if(inputs.get(3).toString().equals("")) output.add(details.getTo());
        if(inputs.get(4).toString().equals("")){
            output.add(details.getTransport());
        } else {
            output.add(inputs.get(4).toString());
        }

        return output;
    }

    @Override
    public int calulateChildfriendl(String title) {
        int popularity = 0;
        int sumDifficulty = handler.getSumDifficulty(title);
        popularity = sumDifficulty % 6;
        return popularity;
    }

    @Override
    public int calculatePopularity(String title) {
        int popularity = 0;
        int sumRating = handler.getSumRating(title);
        popularity = sumRating % 6;
        return popularity;
    }

    @Override
    public void importTour(File file) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray tourDetails = (JSONArray) obj;
            tourDetails.forEach( emp -> {
                try {
                    createTour( (JSONObject) emp );
                } catch (IOException e) {
                    logger.warn(e.toString());
                }
            });

        } catch (FileNotFoundException e) {
            logger.warn(e.toString());
        } catch (IOException e) {
            logger.warn(e.toString());
        } catch (org.json.simple.parser.ParseException e) {
            logger.warn(e.toString());
        }
    }

    @Override
    public void exportTour(Tour tour) {
        // first we want the save dialog to open so we can get the file we want to work with
        Stage stage = new Stage();

        // create JSON Object
        JSONObject tourDetails = new JSONObject();
        tourDetails.put("title", tour.getName());
        tourDetails.put("description", tour.getDescription());
        tourDetails.put("start", tour.getFrom());
        tourDetails.put("destination", tour.getTo());
        tourDetails.put("transport", tour.getTransport());

        JSONObject tourObject = new JSONObject();
        tourObject.put("tour", tourDetails);

        JSONArray tourList = new JSONArray();
        tourList.add(tourObject);

        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                // We can write any JSONArray or JSONObject instance to the file
                fileWriter.write(tourList.toJSONString());
                fileWriter.flush();
            } catch (IOException e) {
                logger.warn(e.toString());
            }
        }

    }

    public void createTour(JSONObject tours) throws IOException {
        JSONObject tourObject = (JSONObject) tours.get("tour");
        String title = (String) tourObject.get("title");
        String description = (String) tourObject.get("description");
        String start = (String) tourObject.get("start");
        String end = (String) tourObject.get("destination");
        String transport = (String) tourObject.get("transport");

        mapAPI.sendRequest("old", start, end, transport, title, description, "create");
    }

    @Override
    public void generateTourReport(Tour tour) {
        // get all TourLogs
        ObservableList<TourLogImpl> tourLogs = handlerLog.getTourLogs(tour.getName());

        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );

        File file = fileChooser.showSaveDialog(stage); // file we will write into
        if(file != null){
            try {
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
                Document doc = new Document(pdfDoc);

                // Tour Details
                Text titleTour = new Text("Title: " + tour.getName());
                Paragraph para1 = new Paragraph(titleTour);
                Text descTour = new Text("Description: " + tour.getDescription());
                Paragraph para2 = new Paragraph(descTour);
                Text fromTour = new Text("From: " + tour.getFrom());
                Paragraph para3 = new Paragraph(fromTour);
                Text transTour = new Text("To: " + tour.getTransport());
                Paragraph para4 = new Paragraph(transTour);
                Text distTour = new Text("Distance: " + tour.getDistance());
                Paragraph para5 = new Paragraph(distTour);
                Text durTour = new Text("Duration: " + tour.getDuration());
                Paragraph para6 = new Paragraph(durTour);

                doc.add(para1);
                doc.add(para2);
                doc.add(para3);
                doc.add(para4);
                doc.add(para5);
                doc.add(para6);

                // Tour Logs
                Table table = new Table(6);

                Cell nameT = new Cell(); // Creating a cell
                nameT.add(new Paragraph(new Text("Name")));
                table.addCell(nameT); // Adding cell to the table
                Cell cell1 = new Cell(); // Creating a cell
                cell1.add(new Paragraph(new Text("Date")));
                table.addCell(cell1); // Adding cell to the table
                Cell cell2 = new Cell(); // Creating a cell
                cell2.add(new Paragraph(new Text("Comment")));
                table.addCell(cell2); // Adding cell to the table
                Cell cell3 = new Cell(); // Creating a cell
                cell3.add(new Paragraph(new Text("Difficulty")));
                table.addCell(cell3); // Adding cell to the table
                Cell cell4 = new Cell(); // Creating a cell
                cell4.add(new Paragraph(new Text("Time")));
                table.addCell(cell4); // Adding cell to the table
                Cell cell5 = new Cell(); // Creating a cell
                cell5.add(new Paragraph(new Text("Rating")));
                table.addCell(cell5); // Adding cell to the table

                // add image underneath
                try {
                    ImageData imageData = ImageDataFactory.create("src/main/java/com/example/tour_planner/utils/maps/" + tour.getName() + "_map.jpg");
                    Image img = new Image(imageData);
                    doc.add(img);
                } catch (MalformedURLException e){ e.printStackTrace(); }


                //iterate through the observeable list
                for(TourLogImpl log: tourLogs){
                    Cell nameL = new Cell(); // Creating a cell
                    nameL.add(new Paragraph(new Text(log.getTitle().get())));
                    table.addCell(nameL); // Adding cell to the table

                    Cell cell6 = new Cell(); // Creating a cell
                    cell6.add(new Paragraph(new Text(log.getDateTime().toString())));
                    table.addCell(cell6); // Adding cell to the table

                    Cell cell7 = new Cell(); // Creating a cell
                    cell7.add(new Paragraph(new Text(log.getComment().get())));
                    table.addCell(cell7); // Adding cell to the table

                    Cell cell8 = new Cell(); // Creating a cell
                    cell8.add(new Paragraph(new Text(String.valueOf(log.getDifficulty().getValue()))));
                    table.addCell(cell8); // Adding cell to the table

                    Cell cell9 = new Cell(); // Creating a cell
                    cell9.add(new Paragraph(new Text(String.valueOf(log.getTotalTime().getValue()))));
                    table.addCell(cell9); // Adding cell to the table

                    Cell cell10 = new Cell(); // Creating a cell
                    cell10.add(new Paragraph(new Text(String.valueOf(log.getRating().getValue()))));
                    table.addCell(cell10); // Adding cell to the table
                }

                doc.add(table);
                doc.close();


            } catch (FileNotFoundException e){
                logger.warn(e.toString());
            }

        }
    }

    @Override
    public void generateSummary(Tour tour) {
        ArrayList<Float> summaryData = serviceLog.summarizeTourLogs(tour.getName());
        if(summaryData != null){
            Stage stage = new Stage();

            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );

            File file = fileChooser.showSaveDialog(stage); // file we will write into
            if(file != null) {
                try {
                    PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
                    Document doc = new Document(pdfDoc);

                    Text titleTour = new Text("Title: " + tour.getName());
                    Paragraph para1 = new Paragraph(titleTour);

                    // Add the stats
                    Text stats = new Text("Statistics: ");
                    Paragraph para2 = new Paragraph(stats);

                    Text avgTime = new Text("Average Time: " + summaryData.get(1));
                    Paragraph para3 = new Paragraph(avgTime);
                    Text avgRating = new Text("Average Rating: " + summaryData.get(0));
                    Paragraph para4 = new Paragraph(avgRating);

                    doc.add(para1);
                    doc.add(para2);
                    doc.add(para3);
                    doc.add(para4);
                    doc.close();

                } catch (FileNotFoundException e) { logger.warn(e.toString()); }
            }

        }
    }
}