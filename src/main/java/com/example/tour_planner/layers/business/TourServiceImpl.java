package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import com.example.tour_planner.utils.api.mapAPI;

import java.io.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;

public class TourServiceImpl implements TourService{

    TourDaoImpl handler = new TourDaoImpl();

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
        if(inputs.get(0).toString().equals("")) output.add(details.getName());
        if(inputs.get(1).toString().equals("")) output.add(details.getDescription());
        if(inputs.get(2).toString().equals("")) output.add(details.getFrom());
        if(inputs.get(3).toString().equals("")) output.add(details.getTo());
        if(inputs.get(4).toString().equals("")) output.add(details.getTransport());

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
                    e.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
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
                e.printStackTrace();
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
    public File getFile(File file) {
        return null;
    }
}
