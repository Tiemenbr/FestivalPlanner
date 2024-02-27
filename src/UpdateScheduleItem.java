import Objects.Attraction;
import Objects.Location;
import Objects.Schedule;
import Objects.ScheduleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateScheduleItem {

    public static VBox getComponent(){
        Schedule schedule = Planner.getSchedule();
        VBox mainUpdateScheduleItemBox = new VBox(20);
        mainUpdateScheduleItemBox.setPadding(new Insets(20));

        Label forumTypeLabel = new Label("Update Schedule Item data:");
        mainUpdateScheduleItemBox.getChildren().add(forumTypeLabel);

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);
        VBox layout = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox, layout);

        //#region ScheduleItem
        Label scheduleItemInputLabel = new Label("Schedule Item: ");
        labelColumnBox.getChildren().add(scheduleItemInputLabel);

        ComboBox<ScheduleItem> scheduleItemOptionsComboBox = new ComboBox<>();
        HashMap<Integer, ScheduleItem> scheduleItems = schedule.getScheduleItems();
        for (int i = 1; i <= scheduleItems.size(); i++) {
            scheduleItemOptionsComboBox.getItems().add(scheduleItems.get(i));
        }
        scheduleItemOptionsComboBox.setConverter(new StringConverter<ScheduleItem>() {
            @Override
            public String toString(ScheduleItem scheduleItem) {
                StringBuilder scheduleData = new StringBuilder();
                scheduleData.append(scheduleItem.getDay().toString().substring(0, 1).toUpperCase() + scheduleItem.getDay().toString().substring(1).toLowerCase());
                scheduleData.append(" ");
                scheduleData.append(scheduleItem.getStartTime()+"-"+scheduleItem.getEndTime());
                scheduleData.append(", ");
                scheduleData.append(scheduleItem.getAttraction(Planner.getSchedule()).getName());
                scheduleData.append(" ");
                scheduleData.append(scheduleItem.getLocation(Planner.getSchedule()).getName());
                return scheduleData.toString();
            }

            @Override
            public ScheduleItem fromString(String string) {
                return null;
            }
        });

        inputsColumnBox.getChildren().add(scheduleItemOptionsComboBox);
        //#endregion

        //#region Location combobox
        Label locationInputLabel = new Label("Location: ");
        labelColumnBox.getChildren().add(locationInputLabel);

        ComboBox<Location> locationOptionsComboBox = new ComboBox<>();
        HashMap<Integer, Location> locations = schedule.getLocations();
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
        Label attractionInputLabel = new Label("Attraction: ");
        labelColumnBox.getChildren().add(attractionInputLabel);

        ComboBox<Attraction> attractionOptionsComboBox = new ComboBox<>();
        HashMap<Integer, Attraction> attractions = schedule.getAttractions();
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
        //#region Day
        Label dayLabel = new Label("Day: ");
        labelColumnBox.getChildren().add(dayLabel);

        ComboBox<ScheduleItem.day> dayOptionComboBox = new ComboBox<>();
        for (ScheduleItem.day day : ScheduleItem.day.values()) {
            dayOptionComboBox.getItems().add(day);
        }

        inputsColumnBox.getChildren().add(dayOptionComboBox);
        dayOptionComboBox.setConverter(new StringConverter<ScheduleItem.day>() {
            @Override
            public String toString(ScheduleItem.day day) {
                String dayString = day.toString();
                return dayString.substring(0, 1).toUpperCase() + dayString.substring(1).toLowerCase();
            }

            @Override
            public ScheduleItem.day fromString(String string) {
                return null;
            }
        });

        //#endregion
        //#region Time Start
        HBox durationStartInputBox = new HBox(10);

        Label startTimeLabel = new Label("Start Time: ");
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

        Label endTimeLabel = new Label("End Time: ");
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

        //on selection of the Schedule Item ComboBox set all the other ComboBoxes to the matching values
        scheduleItemOptionsComboBox.setOnAction(e ->{
            //get the scheduleItem that was selected
            ScheduleItem selectedScheduleItem = scheduleItemOptionsComboBox.getValue();
            //clear and select the location
            locationOptionsComboBox.getSelectionModel().clearAndSelect(locationOptionsComboBox.getItems().indexOf(selectedScheduleItem.getLocation(schedule)));
            //clear and select the attraction
            attractionOptionsComboBox.getSelectionModel().clearAndSelect(attractionOptionsComboBox.getItems().indexOf(selectedScheduleItem.getAttraction(schedule)));
            //clear and select the day
            dayOptionComboBox.getSelectionModel().clearAndSelect(dayOptionComboBox.getItems().indexOf(selectedScheduleItem.getDay()));
            //clear and select the start time hour
            String startHour = selectedScheduleItem.getStartTime().toString().substring(0,2);
            startHourComboBox.getSelectionModel().clearAndSelect(startHourComboBox.getItems().indexOf(startHour));
            //clear and select the start time minute
            String startMinute = selectedScheduleItem.getStartTime().toString().substring(3);
            startMinuteComboBox.getSelectionModel().clearAndSelect(startMinuteComboBox.getItems().indexOf(startMinute));
            //clear and select the end time hour
            String endHour = selectedScheduleItem.getEndTime().toString().substring(0,2);
            endHourComboBox.getSelectionModel().clearAndSelect(endHourComboBox.getItems().indexOf(endHour));
            //clear and select the end time minute
            String endMinute = selectedScheduleItem.getEndTime().toString().substring(3);
            endMinuteComboBox.getSelectionModel().clearAndSelect(endMinuteComboBox.getItems().indexOf(endMinute));
        });

        //#region update button
        Button updateAttractionButton = new Button("Update Schedule Item");
        updateAttractionButton.setOnAction(event -> {
            ScheduleItem scheduleItem = scheduleItemOptionsComboBox.getValue();

            System.out.println("updating scheduleItem "+scheduleItem.getId()+":");
            System.out.println("from:");
            System.out.println(scheduleItem);

            Location location = locationOptionsComboBox.getValue();
            Attraction attraction = attractionOptionsComboBox.getValue();
            ScheduleItem.day day = dayOptionComboBox.getValue();
            String startTime = startHourComboBox.getValue() + ":" + startMinuteComboBox.getValue();
            String endTime = endHourComboBox.getValue() + ":" + endMinuteComboBox.getValue();
            scheduleItem.setAll(location,attraction,day,startTime,endTime);
            System.out.println("To:");
            System.out.println(scheduleItem
            );
            scheduleItemOptionsComboBox.getItems().set(scheduleItemOptionsComboBox.getSelectionModel().getSelectedIndex(),scheduleItem);
        });
        inputsColumnBox.getChildren().add(updateAttractionButton);
        //#endregion

        ScrollPane pane = new ScrollPane();
        ArrayList<String> name = new ArrayList<>();
        name.add(" ");
        if (scheduleItems.isEmpty()){name.clear();name.add("empty");}
        else {
            name.clear();
            for (int key : schedule.getScheduleItems().keySet()){
                ScheduleItem scheduleItem = Planner.getSchedule().getScheduleItem(key);
                String scheduleItemString = scheduleItem.getAttraction(schedule).getName() + ", " + scheduleItem.getStartTime() + "-" + scheduleItem.getEndTime() + ", " + scheduleItem.getLocation(schedule).getName();
                name.add(scheduleItemString);
            }
        }
        ObservableList<String> items = FXCollections.observableArrayList(name);
        ListView<String> listView = new ListView<String>(items);
        listView.setMaxSize(300, 500);

        layout.setPadding(new Insets(5,5,5,100));
        layout.getChildren().addAll(listView);



        mainUpdateScheduleItemBox.getChildren().add(contentRowBox);

        return mainUpdateScheduleItemBox;
    }



}