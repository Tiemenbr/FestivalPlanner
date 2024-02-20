import Objects.Attraction;
import Objects.Location;
import Objects.ScheduleItem;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.HashMap;

public class CreateScheduleItem{

    public static VBox getComponent(Planner planner){
        VBox mainCreateScheduleBox = new VBox(20);

        //#region Location combobox
        HBox locationInputBox = new HBox(10);
        Label locationInputLabel = new Label("Locatie: ");
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

        locationInputBox.getChildren().addAll(locationInputLabel,locationOptionsComboBox);
        //#endregion

        //#region Attraction combobox
        HBox attractionInputBox = new HBox(10);
        Label attractionInputLabel = new Label("Attractie: ");
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

        attractionInputBox.getChildren().addAll(attractionInputLabel,attractionOptionsComboBox);
        //#endregion

        //#region Duration/Time
        HBox durationInputBox = new HBox(10);
        //#region Time Start
        HBox durationStartInputBox = new HBox(10);
        Label startTimeLabel = new Label("Van: ");
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
        durationStartInputBox.getChildren().addAll(startTimeLabel,startHourComboBox,inbetweenStartStyling, startMinuteComboBox);
        //#endregion
        //#region Time End
        HBox durationEndInputBox = new HBox(10);
        Label endTimeLabel = new Label("Tot: ");
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
        durationEndInputBox.getChildren().addAll(endTimeLabel,endHourComboBox,inbetweenEndStyling, endMinuteComboBox);
        //#endregion
        durationInputBox.getChildren().addAll(durationStartInputBox, durationEndInputBox);
        //#endregion

        //#region Submit Button
        HBox createButtonBox = new HBox();
        Button createAttractionButton = new Button("Create Attraction");
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
        createButtonBox.getChildren().add(createAttractionButton);
        //#endregion

        mainCreateScheduleBox.getChildren().addAll(locationInputBox,attractionInputBox,durationInputBox,createButtonBox);

        return mainCreateScheduleBox;
    }

}
