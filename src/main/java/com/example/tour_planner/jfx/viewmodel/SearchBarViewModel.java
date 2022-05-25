package com.example.tour_planner.jfx.viewmodel;

import com.example.tour_planner.layers.data.TourDaoImpl;
import com.example.tour_planner.layers.model.Tour;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class SearchBarViewModel {

    private final TourDaoImpl handler = new TourDaoImpl();

    public interface SearchListener {
        void search(String searchString);
    }

    private List<SearchListener> listeners = new ArrayList<>();

    private final StringProperty searchString = new SimpleStringProperty("");
    private final BooleanBinding isSearchDisabledBinding = Bindings.createBooleanBinding( ()->searchString.get().isEmpty() );

    public SearchBarViewModel() {
        searchString.addListener( (arg, oldVal, newVal)->isSearchDisabledBinding.invalidate() );
    }

    public StringProperty searchStringProperty() {
        return searchString;
    }

    public BooleanBinding searchDisabledBinding() {
        return isSearchDisabledBinding;
    }

    public void addSearchListener(SearchListener listener) {
        listeners.add(listener);
    }

    public void removeSearchListener(SearchListener listener) {
        listeners.remove(listener);
    }

    public void doSearch() {

    }
}
