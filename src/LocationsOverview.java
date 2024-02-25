import Objects.Attraction;
import Objects.Location;
import Objects.Visitor;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;

public class LocationsOverview{
    private static HashMap<Integer, Location> locations;
    private static ComboBox locationComboBox = new ComboBox();
    public static VBox getComponent(){

        VBox mainBox = new VBox();

        HBox listsContainerBox = new HBox();

        // List
        ListView<String> collumnIds = new ListView<>();
        collumnIds.getItems().add("Id");
        ListView<String> collumnHeights = new ListView<>();
        collumnHeights.getItems().add("Height");
        ListView<String> collumnWidths = new ListView<>();
        collumnWidths.getItems().add("Width");
        ListView<String> collumnNames = new ListView<>();
        collumnNames.getItems().add("Name");
        ListView<String> collumnVisitors = new ListView<>();
        collumnVisitors.getItems().add("Visitors");

        listsContainerBox.getChildren().addAll(collumnIds, collumnHeights, collumnWidths, collumnNames, collumnVisitors);

        // Fill list
        locations = Planner.getSchedule().getLocations();
        for (int i = 0; i < locations.size(); i++) {
            collumnIds.getItems().add(String.valueOf(locations.get(i+1).getId()));
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
        HBox boxLocationId = new HBox();
        Label labelLocationId = new Label("ID: ");
        TextField textLocationId = new TextField();
        boxLocationId.getChildren().addAll(labelLocationId, textLocationId);
        HBox boxLocationHeight = new HBox();
        Label labelLocationHeight = new Label("Height: ");
        TextField textLocationHeight = new TextField();
        boxLocationHeight.getChildren().addAll(labelLocationHeight, textLocationHeight);
        HBox boxLocationWidth = new HBox();
        Label labelLocationWidth = new Label("Width: ");
        TextField textLocationWidth = new TextField();
        boxLocationWidth.getChildren().addAll(labelLocationWidth, textLocationWidth);
        HBox boxLocationName = new HBox();
        Label labelLocationName = new Label("Name: ");
        TextField textLocationName = new TextField();
        boxLocationName.getChildren().addAll(labelLocationName, textLocationName);
        Button buttonLocationAdd = new Button("Add location");
        Label locationAdded = new Label();
        buttonLocationAdd.setOnAction(event -> {
            if (!textLocationId.getText().equalsIgnoreCase("") &&
                    !textLocationHeight.getText().equalsIgnoreCase("") &&
                    !textLocationWidth.getText().equalsIgnoreCase("")){
                boolean add = true;
                for (Map.Entry<Integer, Location> entry : locations.entrySet()){
                    if (String.valueOf(entry.getValue().getId()).equalsIgnoreCase(textLocationId.getText())){
                        add = false;
                    }
                }
                if (add){
                    // Add location to lists and schedule
                    Location location = new Location(Integer.valueOf(textLocationId.getText()), Integer.valueOf(textLocationHeight.getText()), Integer.valueOf(textLocationWidth.getText()), textLocationName.getText());
                    locations.put(Integer.valueOf(textLocationId.getText()), location);
                    Planner.schedule.addLocation(location);
                    locationAdded.setText("Location added!");

                    // Update list
                    collumnIds.getItems().add(textLocationId.getText());
                    collumnHeights.getItems().add(textLocationHeight.getText());
                    collumnWidths.getItems().add(textLocationWidth.getText());
                    collumnNames.getItems().add(textLocationName.getText());
                    collumnVisitors.getItems().add("False");

                    // Update combobox
                    locationComboBox.getItems().add(location.getName());
                }
                if (!add){
                    int index = 0;
                    for (Map.Entry<Integer, Location> entry : locations.entrySet()){
                        index++;
                        if (String.valueOf(entry.getValue().getId()).equalsIgnoreCase(textLocationId.getText())){
                            // Delete location from lists and schedule
                            Planner.schedule.deleteLocation(entry.getValue().getId());
                            locations.remove(entry.getKey(), entry.getValue());

                            // Update list
                            collumnIds.getItems().remove(index);
                            collumnHeights.getItems().remove(index);
                            collumnWidths.getItems().remove(index);
                            collumnNames.getItems().remove(index);
                            collumnVisitors.getItems().remove(index);

                            // Update combobox
                            locationComboBox.getItems().remove(entry.getValue().getName());

                            // Add location to lists and schedule
                            Location location = new Location(Integer.valueOf(textLocationId.getText()), Integer.valueOf(textLocationHeight.getText()), Integer.valueOf(textLocationWidth.getText()), textLocationName.getText());
                            locations.put(Integer.valueOf(textLocationId.getText()), location);
                            Planner.schedule.addLocation(location);
                            locationAdded.setText("Location updated!");

                            // Update list
                            collumnIds.getItems().add(textLocationId.getText());
                            collumnHeights.getItems().add(textLocationHeight.getText());
                            collumnWidths.getItems().add(textLocationWidth.getText());
                            collumnNames.getItems().add(textLocationName.getText());
                            collumnVisitors.getItems().add("False");

                            // Update combobox
                            locationComboBox.getItems().add(location.getName());
                        }
                    }
                }
            } else {
                locationAdded.setText("Location was not added!");
            }
        });
        locationAddBox.getChildren().addAll(boxLocationId, boxLocationHeight, boxLocationWidth, boxLocationName, buttonLocationAdd, locationAdded);

        // Delete location
        VBox locationUpdateDeleteBox = new VBox();
        HashMap<Integer, Location> locationMap = Planner.getSchedule().getLocations();
        for(Map.Entry<Integer, Location> e : locationMap.entrySet()){
            String name = String.valueOf(locationComboBox.getItems().add(e.getValue().getName()));
            locationComboBox.setValue(name);
        }
        Button buttonLocationDelete = new Button("Delete location");
        Label locationDeleted = new Label();
        buttonLocationDelete.setOnAction(event -> {
            if (!locationComboBox.getValue().toString().equalsIgnoreCase("true")){
                int index = 0;
                for (Map.Entry<Integer, Location> entry : locationMap.entrySet()){
                    index++;
                    if (entry.getValue().getName() == locationComboBox.getValue()){
                        // Delete location from lists and schedule
                        Planner.schedule.deleteLocation(entry.getValue().getId());
                        locations.remove(entry.getKey(), entry.getValue());

                        // Update list
                        collumnIds.getItems().remove(index);
                        collumnHeights.getItems().remove(index);
                        collumnWidths.getItems().remove(index);
                        collumnNames.getItems().remove(index);
                        collumnVisitors.getItems().remove(index);

                        // Update combobox
                        locationComboBox.getItems().remove(entry.getValue().getName());
                    }
                }
                locationDeleted.setText("Location deleted!");
            } else {
                locationDeleted.setText("Location was not deleted!");
            }
        });
        locationUpdateDeleteBox.getChildren().addAll(locationComboBox, buttonLocationDelete, locationDeleted);

        mainBox.getChildren().addAll(listsContainerBox, locationAddBox, locationUpdateDeleteBox);
        Planner.getSchedule().getLocations();

        return mainBox;
    }
}
