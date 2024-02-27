package gui;

import Objects.Attraction;
import Objects.Location;
import Objects.Schedule;
import Objects.Visitor;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import javax.swing.*;
import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;

public class LocationsOverview{
    private static Schedule schedule;
    private static HashMap<Integer, Location> locations;
    private static ComboBox<Location> locationComboBox = new ComboBox();

    // Used for generating Location Id's
    private static int hiddenId;
    // Location variable to temporarily save the location that is getting updated
    private static Location theLocation;
    // Boolean value is either true: add location, or false: update location
    private static boolean add = true;
    
    public static VBox getComponent(){
        schedule = Planner.getSCHEDULE();

        VBox mainBox = new VBox();

        HBox listsContainerBox = new HBox();

        // List
        ListView<String> collumnNames = new ListView<>();
        collumnNames.getItems().add("Name");
        ListView<String> collumnHeights = new ListView<>();
        collumnHeights.getItems().add("Height");
        ListView<String> collumnWidths = new ListView<>();
        collumnWidths.getItems().add("Width");
        ListView<String> collumnVisitors = new ListView<>();
        collumnVisitors.getItems().add("Visitors");
        ListView<Button> collumnUpdate = new ListView<>();
        Button buttonEmpty = new Button("Update");
        buttonEmpty.setPrefHeight(3);
        buttonEmpty.setDisable(true);
        collumnUpdate.getItems().add(buttonEmpty);

        listsContainerBox.getChildren().addAll(collumnNames, collumnHeights, collumnWidths, collumnVisitors, collumnUpdate);

        // Add location
        VBox locationAddBox = new VBox();
        HBox boxLocationName = new HBox();
        Label labelLocationName = new Label("Name: ");
        TextField textLocationName = new TextField();
        boxLocationName.getChildren().addAll(labelLocationName, textLocationName);
        HBox boxLocationHeight = new HBox();
        Label labelLocationHeight = new Label("Height: ");
        TextField textLocationHeight = new TextField();
        boxLocationHeight.getChildren().addAll(labelLocationHeight, textLocationHeight);
        HBox boxLocationWidth = new HBox();
        Label labelLocationWidth = new Label("Width: ");
        TextField textLocationWidth = new TextField();
        boxLocationWidth.getChildren().addAll(labelLocationWidth, textLocationWidth);
        Button buttonLocationAdd = new Button("Add location");
        Label locationAdded = new Label();

        // Fill list
        locations = schedule.getLocations();
        hiddenId = locations.size()+1;
        // Walk through all locations
        for (int i = 0; i < locations.size(); i++) {
            collumnNames.getItems().add(locations.get(i+1).getName());
            collumnHeights.getItems().add(String.valueOf(locations.get(i+1).getHeight()));
            collumnWidths.getItems().add(String.valueOf(locations.get(i+1).getWidth()));
            if (locations.get(i+1).getVisitors().size() > 1){
                collumnVisitors.getItems().add("True");
            } else {
                collumnVisitors.getItems().add("False");
            }
            // Add update button
            Button buttonUpdate = new Button("Update");
            buttonUpdate.setPrefHeight(3);
            int finalI = i+1;
            buttonUpdate.setOnAction(event -> {
                refreshLocations();
                add = false;
                theLocation = locations.get(finalI);
                if (theLocation != null){
                    textLocationName.setText(theLocation.getName());
                    textLocationHeight.setText(String.valueOf(theLocation.getHeight()));
                    textLocationWidth.setText(String.valueOf(theLocation.getWidth()));
                } else {
                    textLocationName.setText("");
                    textLocationHeight.setText("");
                    textLocationWidth.setText("");
                }
            });
            collumnUpdate.getItems().add(buttonUpdate);
            refreshLocations();
        }

        buttonLocationAdd.setOnAction(event -> {
            if (!textLocationName.getText().equalsIgnoreCase("") &&
                    !textLocationHeight.getText().equalsIgnoreCase("") &&
                    Integer.valueOf(textLocationHeight.getText()) > 0 &&
                    !textLocationWidth.getText().equalsIgnoreCase("") &&
                    Integer.valueOf(textLocationWidth.getText()) > 0){
                // Adding an item
                if (add){
                    try {
                        // Add location to lists and schedule
                        Location location = new Location(hiddenId++, Integer.valueOf(textLocationHeight.getText()), Integer.valueOf(textLocationWidth.getText()), textLocationName.getText());
                        refreshLocations();
                        schedule.addLocation(location);
                        locationAdded.setText("Location added!");

                        // Update list
                        collumnHeights.getItems().add(textLocationHeight.getText());
                        collumnWidths.getItems().add(textLocationWidth.getText());
                        collumnNames.getItems().add(textLocationName.getText());
                        collumnVisitors.getItems().add("False");
                        Button newButton = new Button("Update");
                        newButton.setPrefHeight(3);
                        newButton.setOnAction(event1 -> {
                            refreshLocations();
                            add = false;
                            theLocation = locations.get(location.getId());
                            if (theLocation != null){
                                textLocationName.setText(theLocation.getName());
                                textLocationHeight.setText(String.valueOf(theLocation.getHeight()));
                                textLocationWidth.setText(String.valueOf(theLocation.getWidth()));
                            } else{
                                textLocationName.setText("");
                                textLocationHeight.setText("");
                                textLocationWidth.setText("");
                            }
                        });
                        collumnUpdate.getItems().add(newButton);

                        // Update combobox
                        locationComboBox.getItems().add(location);

                        // Update textboxes
                        textLocationName.setText("");
                        textLocationHeight.setText("");
                        textLocationWidth.setText("");

                        refreshLocations();
                        add = true;
                    } catch (Exception e){
                        locationAdded.setText("Location could not be added!");
                        e.printStackTrace();
                    }
                }
                // Updating an item
                else if (!add){
                    try {
                        // Get id
                        int index = 0;
                        int theIndex = 0;
                        for (Map.Entry<Integer, Location> entry : locations.entrySet()){
                            index++;
                            if (entry.getValue() == theLocation){
                                theIndex = index;
                            }
                        }
                        // Delete location from lists and schedule
                        schedule.deleteLocation(theLocation.getId());
                        locations.remove(theLocation.getId(), theLocation);

                        // Update lists
                        collumnHeights.getItems().remove(theIndex);
                        collumnWidths.getItems().remove(theIndex);
                        collumnNames.getItems().remove(theIndex);
                        collumnVisitors.getItems().remove(theIndex);
                        collumnUpdate.getItems().remove(theIndex);

                        refreshLocations();

                        // Update combobox
                        locationComboBox.getItems().remove(theLocation);

                        // Add location to lists and schedule
                        Location location = new Location(hiddenId++, Integer.valueOf(textLocationHeight.getText()), Integer.valueOf(textLocationWidth.getText()), textLocationName.getText());
                        // Recheck hiddenId value
                        refreshLocations();
                        schedule.addLocation(location);
                        locationAdded.setText("Location updated!");

                        // Update lists
                        collumnHeights.getItems().add(textLocationHeight.getText());
                        collumnWidths.getItems().add(textLocationWidth.getText());
                        collumnNames.getItems().add(textLocationName.getText());
                        collumnVisitors.getItems().add("False");
                        Button newButton = new Button("Update");
                        newButton.setPrefHeight(3);
                        newButton.setOnAction(event1 -> {
                            refreshLocations();
                            add = false;
                            theLocation = locations.get(location.getId());
                            if (theLocation != null){
                                textLocationName.setText(theLocation.getName());
                                textLocationHeight.setText(String.valueOf(theLocation.getHeight()));
                                textLocationWidth.setText(String.valueOf(theLocation.getWidth()));
                            } else{
                                textLocationName.setText("");
                                textLocationHeight.setText("");
                                textLocationWidth.setText("");
                            }
                        });
                        collumnUpdate.getItems().add(newButton);

                        // Update combobox
                        locationComboBox.getItems().add(location);

                        // Update textboxes
                        textLocationName.setText("");
                        textLocationHeight.setText("");
                        textLocationWidth.setText("");

                        refreshLocations();
                        add = true;
                    } catch (Exception e){
                        locationAdded.setText("Location could not be updated!");
                        e.printStackTrace();
                    }
                }
            } else {
                locationAdded.setText("Please enter valid texts!");
            }
        });
        locationAddBox.getChildren().addAll(boxLocationName, boxLocationHeight, boxLocationWidth, buttonLocationAdd, locationAdded);

        // Delete location
        VBox locationUpdateDeleteBox = new VBox();
        for (int i = 1; i <= locations.size(); i++) {
            locationComboBox.getItems().add(locations.get(i));
        }
        locationComboBox.setConverter(new StringConverter<Location>() {
            @Override
            public String toString(Location location) {
                return location.getName();
            }

            @Override
            public Location fromString(String string) {
                return null;
            }
        });
        Button buttonLocationDelete = new Button("Delete location");
        Label locationDeleted = new Label();
        buttonLocationDelete.setOnAction(event -> {
            if (locationComboBox.getValue() != null){
                try {
                    // Get id
                    int index = 0;
                    int theIndex = 0;
                    for (Map.Entry<Integer, Location> entry : locations.entrySet()){
                        index++;
                        if (entry.getValue() == locationComboBox.getValue()){
                            theIndex = index;
                        }
                    }
                    // Delete location from lists and schedule
                    schedule.deleteLocation(locationComboBox.getValue().getId());
                    locations.remove(locationComboBox.getValue().getId(), locationComboBox.getValue());

                    // Update lists
                    collumnHeights.getItems().remove(theIndex);
                    collumnWidths.getItems().remove(theIndex);
                    collumnNames.getItems().remove(theIndex);
                    collumnVisitors.getItems().remove(theIndex);
                    collumnUpdate.getItems().remove(theIndex);

                    // Update combobox
                    locationComboBox.getItems().remove(locationComboBox.getValue());
                    locationDeleted.setText("Location deleted!");

                    refreshLocations();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                locationDeleted.setText("Location was not deleted!");
            }
        });
        locationUpdateDeleteBox.getChildren().addAll(locationComboBox, buttonLocationDelete, locationDeleted);

        mainBox.getChildren().addAll(listsContainerBox, locationAddBox, locationUpdateDeleteBox);
        return mainBox;
    }

    public static void refreshLocations(){
        // Update locations list & (re)check id values
        locations = schedule.getLocations();
        hiddenId = locations.size()+1;
        for (Map.Entry<Integer, Location> entry : locations.entrySet()){
            if (entry.getKey() == hiddenId){
                hiddenId++;
            }
        }
    }
}
