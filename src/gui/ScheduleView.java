package gui;

import Objects.Location;
import Objects.ScheduleItem;
import Objects.Schedule;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ScheduleView {
    private static DayOfWeek selectedDay;
    private static ArrayList<DayOfWeek> days = new ArrayList<>(Arrays.asList(DayOfWeek.values()));
    private static HashMap<Integer, Integer> locationRow = new HashMap<>();
    public static Node createScheduleView(Schedule schedule) {
        BorderPane mainBox = new BorderPane();

        HashMap<Integer, Location> locations = schedule.getLocations();
        HashMap<Integer, ScheduleItem> scheduleItems = schedule.getScheduleItems();

        int minuteInterval = 5;

        //#region Select Day
        HBox selectDayBox = new HBox();
        selectDayBox.setAlignment(Pos.CENTER);
        mainBox.setTop(selectDayBox);

        selectedDay = days.get(0);
        Label selectedDayLabel = new Label(selectedDay.toString());

        Button dayBack = new Button("<");
        dayBack.setOnAction(e -> {
            if(selectedDay != DayOfWeek.MONDAY){
                selectedDay = days.get(days.indexOf(selectedDay)-1);
                createSchedule(mainBox,scheduleItems, locations, schedule, minuteInterval);
            }
            selectedDayLabel.setText(selectedDay.toString());


        });
        Button dayNext = new Button(">");
        dayNext.setOnAction(e -> {
            if(selectedDay != DayOfWeek.SUNDAY){
                selectedDay = days.get(days.indexOf(selectedDay)+1);
                createSchedule(mainBox,scheduleItems, locations, schedule, minuteInterval);
            }
            selectedDayLabel.setText(selectedDay.toString());


        });
        selectDayBox.getChildren().addAll(dayBack, selectedDayLabel, dayNext);
        //#endregion


        createSchedule(mainBox,scheduleItems, locations, schedule, minuteInterval);


        return mainBox;
    }

    private static void createSchedule(BorderPane pane,HashMap<Integer, ScheduleItem> scheduleItems, HashMap<Integer, Location> locations, Schedule schedule, int minuteInterval){
        GridPane gridPane = createScheduleBase(locations.size(),minuteInterval);
        gridPane = fillScheduleWithLocationData(gridPane, locations);
        gridPane = fillScheduleWithScheduleItems(gridPane, scheduleItems, schedule, minuteInterval);
        gridPane.setAlignment(Pos.CENTER);
        pane.setCenter(gridPane);
    }
    private static GridPane fillScheduleWithScheduleItems(GridPane gridPane, HashMap<Integer, ScheduleItem> scheduleItems, Schedule schedule, int minuteInterval){
        for (Integer key : scheduleItems.keySet()) {

            ScheduleItem scheduleItem = scheduleItems.get(key);
            if(scheduleItem.getDay() == selectedDay) {
                VBox scheduleItemLabel = new VBox();

                HBox dataRow1 = new HBox();
                String text1 = "\'" + scheduleItem.getAttraction(schedule).getName() + "\' at '" + scheduleItem.getLocation(schedule).getName() + "\'";
                Label data1 = new Label(text1);
                data1.setStyle("-fx-text-fill: white;");
                dataRow1.getChildren().add(data1);

                HBox dataRow2 = new HBox();
                String text2 = scheduleItem.getStartTime().toString() + " - " + scheduleItem.getEndTime().toString();
                Label data2 = new Label(text2);
                data2.setStyle("-fx-text-fill: white;");
                dataRow2.getChildren().add(data2);

                Button delete = new Button("delete");
                delete.setOnAction(e ->{
                    scheduleItem.delete(schedule);
                    //todo refresh view
                });

                scheduleItemLabel.getChildren().addAll(dataRow1, dataRow2, delete);
                scheduleItemLabel.setPrefWidth(Integer.MAX_VALUE);
                scheduleItemLabel.setPadding(new Insets(5));
                scheduleItemLabel.setStyle("-fx-background-color: #8d9196; -fx-border-color: black");
                Tooltip tooltip = new Tooltip(text1 + " " + text2);
                Tooltip.install(scheduleItemLabel, tooltip);

                int startIndex = scheduleItem.getStartTime().getHour() * 60 / minuteInterval + scheduleItem.getStartTime().getMinute() / minuteInterval;
                int endIndex = scheduleItem.getEndTime().getHour() * 60 / minuteInterval + scheduleItem.getEndTime().getMinute() / minuteInterval;
//            startIndex = 8*60/minuteInterval;
//            endIndex = 1*60/minuteInterval;
                System.out.println(scheduleItem.getStartTime().getHour() + "*60/" + minuteInterval + " + " + scheduleItem.getStartTime().getMinute() + "/" + minuteInterval + " = " + startIndex + " , " + scheduleItem.getEndTime().getHour() + "*60/" + minuteInterval + " " + scheduleItem.getEndTime().getMinute() + "/" + minuteInterval + " = " + endIndex);
                System.out.println(locationRow.get(scheduleItem.getLocation(schedule).getId()));
                gridPane.add(
                        scheduleItemLabel,
                        startIndex,
                        locationRow.get(scheduleItem.getLocation(schedule).getId()),
                        endIndex - startIndex,
                        1
                );
            }
        }
        return gridPane;
    }
    private static GridPane createScheduleBase(int locationRowCount, int minuteInterval){

        //#region schedule
        GridPane layout = new GridPane();
        //*12 for 5 minute increments making it 288x288 + 1x1 for the time and location row and column
        int increment = (60/minuteInterval);
        for (int i = 0; i < (24*increment); i++) {
            if(i != 0){
                layout.getColumnConstraints().add(new ColumnConstraints(3.1));} //todo proper dynamic value with increment
            else{layout.getColumnConstraints().add(new ColumnConstraints(50));}
            if(i%increment==0){//if 1 of 24
                Label hour = new Label(String.format("%s:00", i/12));
                if ((i/increment)%2==0) {//every other i
                    Pane grayPane = new Pane();
                    grayPane.setBackground(new Background(
                            new BackgroundFill(Color.valueOf("#dedede"),
                                    new CornerRadii(0),
                                    new Insets(0))));

                    layout.add(grayPane,i+1,0, 12,locationRowCount+1);
                    GridPane.setVgrow(grayPane, Priority.ALWAYS);
                }
                hour.setPadding(new Insets(5));
                layout.add(hour,i+1,0, 12,1);
                GridPane.setHgrow(hour,Priority.ALWAYS);
            }
        }


        return layout;
    }
    private static GridPane fillScheduleWithLocationData(GridPane gridPane, HashMap<Integer, Location> locations){

        int keyCount = 0;

        locationRow = new HashMap<>();

        for (Integer key : locations.keySet()) {
            Label location = new Label(locations.get(key).getName());
            keyCount++;
            gridPane.add(location,0, keyCount);
            GridPane.setVgrow(location, Priority.ALWAYS);

            locationRow.put(locations.get(key).getId(),keyCount);
        }

        return gridPane;
    }
}
