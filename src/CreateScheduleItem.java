import Objects.Attraction;
import Objects.Location;
import Objects.ScheduleItem;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.HashMap;

public class CreateScheduleItem{

    public static VBox getComponent(Planner planner){
        VBox mainCreateScheduleItemBox = new VBox(20);
        mainCreateScheduleItemBox.setPadding(new Insets(20));

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox);

        //#region Location combobox
        Label locationInputLabel = new Label("Locatie: ");
        labelColumnBox.getChildren().add(locationInputLabel);

        ComboBox<Location> locationOptionsComboBox = new ComboBox<>();
        HashMap<Integer, Location> locations = planner.getSchedule().getLocations();
        for (int i = 1; i <= locations.size(); i++) {
            locationOptionsComboBox.getItems().add(locations.get(i));
        }
        locationOptionsComboBox.setConverter(new StringConverter<Location>() {
            @Override
            public String toString(Location location) {
                return location.getName();
            }

            @Override
            public Location fromString(String string) {
                return null;
            }
        });

        inputsColumnBox.getChildren().add(locationOptionsComboBox);
        //#endregion

        //#region Attraction combobox
        Label attractionInputLabel = new Label("Attractie: ");
        labelColumnBox.getChildren().add(attractionInputLabel);

        ComboBox<Attraction> attractionOptionsComboBox = new ComboBox<>();
        HashMap<Integer, Attraction> attractions = planner.getSchedule().getAttractions();
        for (int i = 1; i <= attractions.size(); i++) {
            attractionOptionsComboBox.getItems().add(attractions.get(i));
        }
        attractionOptionsComboBox.setConverter(new StringConverter<Attraction>() {
            @Override
            public String toString(Attraction attraction) {
                return attraction.getName();
            }

            @Override
            public Attraction fromString(String string) {
                return null;
            }
        });

        inputsColumnBox.getChildren().add(attractionOptionsComboBox);
        //#endregion

        //#region Duration/Time
        //#region Time Start
        HBox durationStartInputBox = new HBox(10);

        Label startTimeLabel = new Label("Van: ");
        labelColumnBox.getChildren().add(startTimeLabel);

        ComboBox<String> startHourComboBox = new ComboBox<>();
        for (int i = 1; i <= 24; i++) {
            String j = Integer.toString(i);
            j = String.format("%02d", i);;
            startHourComboBox.getItems().add(j);
        }
        //startHourComboBox.getSelectionModel().selectFirst();

        Label inbetweenStartStyling = new Label(":");

        // Minute ComboBox
        ComboBox<String> startMinuteComboBox = new ComboBox<>();
        for (int i = 0; i < 60; i+=5) {
            String j = Integer.toString(i);
            j = String.format("%02d", i);;
            startMinuteComboBox.getItems().add(j);
        }
        //startMinuteComboBox.getSelectionModel().selectFirst();

        durationStartInputBox.getChildren().addAll(startHourComboBox,inbetweenStartStyling, startMinuteComboBox);

        inputsColumnBox.getChildren().add(durationStartInputBox);
        //#endregion
        //#region Time End
        HBox durationEndInputBox = new HBox(10);

        Label endTimeLabel = new Label("Tot: ");
        labelColumnBox.getChildren().add(endTimeLabel);

        ComboBox<String> endHourComboBox = new ComboBox<>();
        for (int i = 1; i <= 24; i++) {
            String j = Integer.toString(i);
            j = String.format("%02d", i);;
            endHourComboBox.getItems().add(j);
        }
        //endHourComboBox.getSelectionModel().selectFirst();

        Label inbetweenEndStyling = new Label(":");

        // Minute ComboBox
        ComboBox<String> endMinuteComboBox = new ComboBox<>();
        for (int i = 0; i < 60; i+=5) {
            String j = Integer.toString(i);
            j = String.format("%02d", i);;
            endMinuteComboBox.getItems().add(j);
        }
        //endMinuteComboBox.getSelectionModel().selectFirst();

        durationEndInputBox.getChildren().addAll(endHourComboBox,inbetweenEndStyling, endMinuteComboBox);

        inputsColumnBox.getChildren().add(durationEndInputBox);
        //#endregion
        //#endregion

        //#region Submit Button
        Button createAttractionButton = new Button("Create Schedule Item");
        createAttractionButton.setOnAction(e ->{
            int newId = planner.getSchedule().getScheduleItems().size()+1; //todo proper id assignment in scheduleItem Class
            String startTime = startHourComboBox.getValue() + ":" + startMinuteComboBox.getValue();
            String endTime = endHourComboBox.getValue() + ":" + endMinuteComboBox.getValue();
            System.out.println(startTime+" "+endTime);
            System.out.println(locationOptionsComboBox.getValue().getId()+" "+ attractionOptionsComboBox.getValue().getId());
            ScheduleItem newItem = new ScheduleItem(newId,locationOptionsComboBox.getValue(), attractionOptionsComboBox.getValue(), startTime, endTime);
            planner.getSchedule().addScheduleItem(newItem);
            System.out.println("created new scheduleItem: " + newItem);
        });

        inputsColumnBox.getChildren().add(createAttractionButton);
        //#endregion

        mainCreateScheduleItemBox.getChildren().add(contentRowBox);

        return mainCreateScheduleItemBox;
    }

}
