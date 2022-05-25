package com.example.tour_planner.layers.business;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.data.TourLogDaoImpl;
import com.example.tour_planner.layers.model.TourLogImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TourLogServiceImpl implements TourLogService{

    private TourLogDaoImpl handlerLog = new TourLogDaoImpl();

    @Override
    public String errorMessage(ArrayList input) {
        ArrayList error = validateInput(input);

        if(error.get(0).equals("title")) return "Enter valid Title";
        if(error.get(0).equals("date")) return "Enter valid Date";
        if(error.get(0).equals("time")) return "Enter valid Time";
        if(error.get(0).equals("rat")) return "Enter Rating";
        if(error.get(0).equals("diff")) return "Enter Difficulty";
        if(error.get(0).equals("comment")) return "Enter Comment";

        return "";
    }

    @Override
    public ArrayList validateInput(ArrayList inputs) {
        ArrayList error = new ArrayList<>();

        // check for the title
        if(validateTitle(inputs.get(0).toString(), inputs.get(6).toString()) == 0) {
            error.add("title");
            error.add(0);
        }
        if(validateTime(inputs.get(2).toString()) == 0){
            error.add("time");
            error.add(0);
        }
        if(inputs.get(3) == null){
            error.add("rat");
            error.add(0);
        }
        if(inputs.get(4) == null){
            error.add("diff");
            error.add(0);
        }
        if(inputs.get(5).toString().equals("")){
            error.add("comment");
            error.add(0);
        }

        error.add("none");
        error.add(1);
        return error;
    }

    @Override
    public int validateTitle(String title, String oldTitle) {
        // check if the title already exists or not

        if(title.equals("")) return 0;
        if(title.equals(oldTitle) && !oldTitle.equals("create144")){
            return 1;
        } else {
            if(handlerLog.titleExist(title)) return 0;
        }
        return 1;
    }

    @Override
    public int validateDate(Date date) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();

        if(date == null) return 0;

        return 1;
    }

    @Override
    public int validateTime(String time) {

        Double timeInt = Double.parseDouble(time);
        if(timeInt <= 0) return 0;

        return 1;
    }
}
