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
    private static HashMap<Integer, Location> locations;
    private static ComboBox<Location> locationComboBox = new ComboBox();
    private static int hiddenId;
    public static VBox getComponent(){
        Schedule schedule = Planner.getSchedule();

        VBox mainBox = new VBox();

        HBox listsContainerBox = new HBox();

        // List
//        ListView<String> collumnIds = new ListView<>();
//        collumnIds.getItems().add("Id");
        ListView<String> collumnNames = new ListView<>();
        collumnNames.getItems().add("Name");
        ListView<String> collumnHeights = new ListView<>();
        collumnHeights.getItems().add("Height");
        ListView<String> collumnWidths = new ListView<>();
        collumnWidths.getItems().add("Width");
        ListView<String> collumnVisitors = new ListView<>();
        collumnVisitors.getItems().add("Visitors");

        listsContainerBox.getChildren().addAll(collumnNames, collumnHeights, collumnWidths, collumnVisitors);

        // Fill list
        locations = schedule.getLocations();
        hiddenId = locations.size()+1;
        for (int i = 0; i < locations.size(); i++) {
            //collumnIds.getItems().add(String.valueOf(locations.get(i+1).getId()));
            collumnHeights.getItems().add(String.valueOf(locations.get(i+1).getHeight()));
            collumnWidths.getItems().add(String.valueOf(locations.get(i+1).getWidth()));
            collumnNames.getItems().add(locations.get(i+1).getName());
            if (locations.get(i+1).getVisitors().size() > 1){
                collumnVisitors.getItems().add("True");
            } else {
                collumnVisitors.getItems().add("False");
            }
        }

        // Add location
        VBox locationAddBox = new VBox();
//        HBox boxLocationId = new HBox();
//        Label labelLocationId = new Label("ID: ");
//        TextField textLocationId = new TextField();
//        boxLocationId.getChildren().addAll(labelLocationId, textLocationId);
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
        buttonLocationAdd.setOnAction(event -> {
            if (!textLocationName.getText().equalsIgnoreCase("") &&
                    !textLocationHeight.getText().equalsIgnoreCase("") &&
                    !textLocationWidth.getText().equalsIgnoreCase("")){
                boolean add = true;
                for (Map.Entry<Integer, Location> entry : locations.entrySet()){
                    if (String.valueOf(entry.getValue().getName()).equalsIgnoreCase(textLocationName.getText())){
                        add = false;
                    }
                }
                // Adding an item
                if (add){
                    // Add location to lists and schedule
                    Location location = new Location(hiddenId++, Integer.valueOf(textLocationHeight.getText()), Integer.valueOf(textLocationWidth.getText()), textLocationName.getText());
                    locations.put(hiddenId++, location);
                    schedule.addLocation(location);
                    locationAdded.setText("Location added!");

                    // Update list
                    //collumnIds.getItems().add(textLocationId.getText());
                    collumnHeights.getItems().add(textLocationHeight.getText());
                    collumnWidths.getItems().add(textLocationWidth.getText());
                    collumnNames.getItems().add(textLocationName.getText());
                    collumnVisitors.getItems().add("False");

                    // Update combobox
                    locationComboBox.getItems().add(location);
                }
                // Updating an item
                if (!add){
                    int index = 0;
                    int theIndex = 0;
                    for (String str : collumnNames.getItems()){
                        if (str.equalsIgnoreCase(textLocationName.getText())){
                            theIndex = index;
                        }
                        index++;
                    }
                    int id = 0;
                    int key = 0;
                    Location value = null;
                    for (Map.Entry<Integer, Location> entry : locations.entrySet()){
                        if (String.valueOf(entry.getValue().getName()).equalsIgnoreCase(textLocationName.getText())){
                            id = entry.getValue().getId();
                            key = entry.getKey();
                            value = entry.getValue();
                        }
                    }
                    if (id != 0 & key != 0 && value != null){
                        // Delete location from lists and schedule
                        schedule.deleteLocation(id);
                        locations.remove(key, value);

                        // Update list
                        //collumnIds.getItems().remove(theIndex);
                        collumnHeights.getItems().remove(theIndex);
                        collumnWidths.getItems().remove(theIndex);
                        collumnNames.getItems().remove(theIndex);
                        collumnVisitors.getItems().remove(theIndex);

                        // Update combobox
                        locationComboBox.getItems().remove(value);

                        // Add location to lists and schedule
                        Location location = new Location(locations.size()+1, Integer.valueOf(textLocationHeight.getText()), Integer.valueOf(textLocationWidth.getText()), textLocationName.getText());
                        locations.put(locations.size()+1, location);
                        schedule.addLocation(location);
                        locationAdded.setText("Location updated!");

                        // Update list
                        //collumnIds.getItems().add(textLocationId.getText());
                        collumnHeights.getItems().add(textLocationHeight.getText());
                        collumnWidths.getItems().add(textLocationWidth.getText());
                        collumnNames.getItems().add(textLocationName.getText());
                        collumnVisitors.getItems().add("False");

                        // Update combobox
                        locationComboBox.getItems().add(location);
                    }
                }
            } else {
                locationAdded.setText("Location was not added!");
            }
        });
        locationAddBox.getChildren().addAll(boxLocationName, boxLocationHeight, boxLocationWidth, buttonLocationAdd, locationAdded);

        // Delete location
        VBox locationUpdateDeleteBox = new VBox();
        HashMap<Integer, Location> locationMap = schedule.getLocations();
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
                int index = 0;
                for (Map.Entry<Integer, Location> entry : locationMap.entrySet()){
                    if (entry.getValue() == locationComboBox.getValue()){
                        // Delete location from lists and schedule
                        schedule.deleteLocation(entry.getValue().getId());
                        locations.remove(entry.getKey(), entry.getValue());

                        // Update list
                        collumnHeights.getItems().remove(index+1);
                        collumnWidths.getItems().remove(index+1);
                        collumnNames.getItems().remove(index+1);
                        collumnVisitors.getItems().remove(index+1);

                        // Update combobox
                        locationComboBox.getItems().remove(entry.getValue());
                    }
                    index++;
                }
                locationDeleted.setText("Location deleted!");
            } else {
                locationDeleted.setText("Location was not deleted!");
            }
        });
        locationUpdateDeleteBox.getChildren().addAll(locationComboBox, buttonLocationDelete, locationDeleted);

        mainBox.getChildren().addAll(listsContainerBox, locationAddBox, locationUpdateDeleteBox);
        //schedule.getLocations();

        return mainBox;
    }
}
