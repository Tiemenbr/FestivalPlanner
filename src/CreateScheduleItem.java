import Objects.*;
import Objects.Comparators.ScheduleItemCompareDayLocationTime;
import Objects.Comparators.ScheduleItemCompareLocation;
import Objects.Comparators.ScheduleItemCompareTimeOverlap;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

public class CreateScheduleItem{

    public static VBox getComponent(){
        Schedule schedule = Planner.getSchedule();
        VBox mainCreateScheduleItemBox = new VBox(20);
        mainCreateScheduleItemBox.setPadding(new Insets(20));

        Label forumTypeLabel = new Label("Create new Schedule Item:");
        mainCreateScheduleItemBox.getChildren().add(forumTypeLabel);

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox);

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

        ComboBox<DayOfWeek> dayOptionComboBox = new ComboBox<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            dayOptionComboBox.getItems().add(day);
        }

        inputsColumnBox.getChildren().add(dayOptionComboBox);
        dayOptionComboBox.setConverter(new StringConverter<DayOfWeek>() {
            @Override
            public String toString(DayOfWeek day) {
                String dayString = day.toString();
                return dayString.substring(0, 1).toUpperCase() + dayString.substring(1).toLowerCase();
            }

            @Override
            public DayOfWeek fromString(String string) {
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

        //#region Submit Button
        Button createAttractionButton = new Button("Create Schedule Item");
        createAttractionButton.setOnAction(e ->{
            int newId = schedule.getScheduleItems().size()+1; //todo proper id assignment in scheduleItem Class

            String startTime = startHourComboBox.getValue() + ":" + startMinuteComboBox.getValue();
            String endTime = endHourComboBox.getValue() + ":" + endMinuteComboBox.getValue();
            System.out.println(startTime+" "+endTime);
            if(!LocalTime.parse(startTime).isBefore(LocalTime.parse(endTime))){
                System.out.println("EndTime must be After StartTime");
                return;
            }
            System.out.println(locationOptionsComboBox.getValue().getId()+" "+ attractionOptionsComboBox.getValue().getId());
            ScheduleItem newItem = new ScheduleItem(newId,locationOptionsComboBox.getValue(), attractionOptionsComboBox.getValue(),dayOptionComboBox.getValue(), startTime, endTime);

            //todo doesn't work as it already gets created......
            ScheduleItemCompareDayLocationTime scheduleItemComparator = new ScheduleItemCompareDayLocationTime();
            for (ScheduleItem scheduleItem : schedule.getScheduleItems().values()) {
                if (scheduleItemComparator.compare(newItem, scheduleItem) > 0) {
                    System.out.println(String.format("Overlap between %s and %s", scheduleItem, newItem));
                    return;
                }
            }

            //schedule.addScheduleItem(newItem);
            System.out.println("created new scheduleItem: " + newItem);
        });

        inputsColumnBox.getChildren().add(createAttractionButton);
        //#endregion

        mainCreateScheduleItemBox.getChildren().add(contentRowBox);

        return mainCreateScheduleItemBox;
    }

}
