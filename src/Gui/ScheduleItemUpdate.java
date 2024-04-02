package Gui;

import Objects.Attraction;
import Objects.Comparators.ScheduleItemCompareDayLocationTime;
import Objects.Location;
import Objects.Schedule;
import Objects.ScheduleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.awt.ScrollPane;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ScheduleItemUpdate {

    public static VBox getComponent(){
        Schedule schedule = Planner.getSCHEDULE();
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
        HashMap<UUID, ScheduleItem> scheduleItems = schedule.getScheduleItems();
        for (UUID key : scheduleItems.keySet()) {
            scheduleItemOptionsComboBox.getItems().add(scheduleItems.get(key));
        }
        scheduleItemOptionsComboBox.setConverter(new StringConverter<ScheduleItem>() {
            @Override
            public String toString(ScheduleItem scheduleItem) {
                StringBuilder scheduleData = new StringBuilder();
                scheduleData.append(scheduleItem.getDay().toString().substring(0, 1).toUpperCase() + scheduleItem.getDay().toString().substring(1).toLowerCase());
                scheduleData.append(" ");
                scheduleData.append(scheduleItem.getStartTime()+"-"+scheduleItem.getEndTime());
                scheduleData.append(", ");
                scheduleData.append(scheduleItem.getAttraction(Planner.getSCHEDULE()).getName());
                scheduleData.append(" ");
                scheduleData.append(scheduleItem.getLocation(Planner.getSCHEDULE()).getName());
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
        HashMap<String, Location> locations = schedule.getLocations();
        for (String key : locations.keySet()) {
            locationOptionsComboBox.getItems().add(locations.get(key));
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
        HashMap<UUID, Attraction> attractions = schedule.getAttractions();
        for (UUID key : attractions.keySet()) {
            attractionOptionsComboBox.getItems().add(attractions.get(key));
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

        ComboBox<DayOfWeek> dayOptionComboBox = new ComboBox<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            dayOptionComboBox.getItems().add(day);
        }

        inputsColumnBox.getChildren().add(dayOptionComboBox);

        //#endregion
        //#region Time Start
        HBox durationStartInputBox = new HBox(10);

        Label startTimeLabel = new Label("Start Time: ");
        labelColumnBox.getChildren().add(startTimeLabel);

        ComboBox<String> startHourComboBox = new ComboBox<>();
        for (int i = 0; i <= 23; i++) {
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
        for (int i = 0; i <= 23; i++) {
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
        Button updateScheduleItemButton = new Button("Update Schedule Item");
        updateScheduleItemButton.setOnAction(event -> {
            ScheduleItem scheduleItem = scheduleItemOptionsComboBox.getValue();

            System.out.println("updating scheduleItem "+scheduleItem.getId()+":");
            System.out.println("from:");
            System.out.println(scheduleItem);

            Location location = locationOptionsComboBox.getValue();
            Attraction attraction = attractionOptionsComboBox.getValue();
            DayOfWeek day = dayOptionComboBox.getValue();
            String startTime = startHourComboBox.getValue() + ":" + startMinuteComboBox.getValue();
            String endTime = endHourComboBox.getValue() + ":" + endMinuteComboBox.getValue();

            if(!LocalTime.parse(startTime).isBefore(LocalTime.parse(endTime))){
                System.out.println("EndTime must be After StartTime");
                return;
            }

            scheduleItem.setAll(location,attraction,day,startTime,endTime);
            System.out.println("To:");
            System.out.println(scheduleItem);

            //todo doesn't work as it already gets updated......
            ScheduleItemCompareDayLocationTime scheduleItemComparator = new ScheduleItemCompareDayLocationTime();
            for (ScheduleItem scheduleItem1 : schedule.getScheduleItems().values()) {
                if (scheduleItemComparator.compare(scheduleItem, scheduleItem1) > 0) {
                    System.out.println(String.format("Overlap between %s and %s", scheduleItem1, scheduleItem));
                    return;
                }
            }
            scheduleItemOptionsComboBox.getItems().set(scheduleItemOptionsComboBox.getSelectionModel().getSelectedIndex(),scheduleItem);
        });
        inputsColumnBox.getChildren().add(updateScheduleItemButton);
        //#endregion

        mainUpdateScheduleItemBox.getChildren().add(contentRowBox);

        return mainUpdateScheduleItemBox;
    }

    public static String ToString(ScheduleItem scheduleItem){
        Schedule schedule = Planner.getSCHEDULE();
        return scheduleItem.getAttraction(schedule).getName() + ", " + scheduleItem.getStartTime() + "-" + scheduleItem.getEndTime() + ", " + scheduleItem.getLocation(schedule).getName() + ", " + scheduleItem.getId();
    }
}