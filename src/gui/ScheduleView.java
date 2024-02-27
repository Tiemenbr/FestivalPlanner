package gui;

import Objects.Location;
import Objects.ScheduleItem;
import Objects.Schedule;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ScheduleView {
    private static ScheduleItem.day selectedDay;
    private static ArrayList<ScheduleItem.day> days = new ArrayList<>(Arrays.asList(ScheduleItem.day.values()));
    private static HashMap<Integer, Integer> locationRow = new HashMap<>();
    public static Node createScheduleView(Schedule schedule) {
        BorderPane mainBox = new BorderPane();

        //#region Select Day
        HBox selectDayBox = new HBox();
        selectDayBox.setAlignment(Pos.CENTER);
        mainBox.setTop(selectDayBox);

        selectedDay = days.get(0);
        Label selectedDayLabel = new Label(selectedDay.toString());

        Button dayBack = new Button("<");
        dayBack.setOnAction(e -> {
            if(selectedDay != ScheduleItem.day.MONDAY){
                selectedDay = days.get(days.indexOf(selectedDay)-1);
            }
            selectedDayLabel.setText(selectedDay.toString());
        });
        Button dayNext = new Button(">");
        dayNext.setOnAction(e -> {
            if(selectedDay != ScheduleItem.day.SUNDAY){
                selectedDay = days.get(days.indexOf(selectedDay)+1);
            }
            selectedDayLabel.setText(selectedDay.toString());
        });
        selectDayBox.getChildren().addAll(dayBack, selectedDayLabel, dayNext);
        //#endregion


        HashMap<Integer, Location> locations = schedule.getLocations();
        HashMap<Integer, ScheduleItem> scheduleItems = schedule.getScheduleItems();

        GridPane layout = createScheduleBase(locations.size());
        layout = fillScheduleWithLocationData(layout, locations);
        layout.setAlignment(Pos.CENTER);

        //#region aaaaaa



        for (Integer key : scheduleItems.keySet()) {
            Label scheduleItemLabel = new Label(scheduleItems.get(key).toString());
            ScheduleItem scheduleItem = scheduleItems.get(key);
            scheduleItemLabel.setStyle("-fx-background-color: blue");
            //scheduleItemLabel.prefWidth(Integer.MIN_VALUE);
            int startIndex = scheduleItem.getStartTime().getHour()*60/5 + scheduleItem.getStartTime().getMinute()/5;
            int endIndex = scheduleItem.getEndTime().getHour()*60/5 + scheduleItem.getEndTime().getMinute()/5;
//            startIndex = 8*60/5;
//            endIndex = 1*60/5;
            System.out.println(scheduleItem.getStartTime().getHour()+"*60/5 + "+scheduleItem.getStartTime().getMinute()+"/5 = "+ startIndex+" , "+scheduleItem.getEndTime().getHour()+"*60/5 "+scheduleItem.getEndTime().getMinute()+"/5 = "+endIndex);
            System.out.println(locationRow.get(scheduleItem.getLocation(schedule).getId()));
            layout.add(
                    scheduleItemLabel,
                    startIndex,
                    locationRow.get(scheduleItem.getLocation(schedule).getId()),
                    endIndex,
                    1
            );
//            layout.add(
//                    scheduleItemLabel,
//                    0,
//                    0,
//                    1,
//                    1
//            );
        }

        Label testLabel = new Label("Test");
        //testLabel.setPrefWidth();
        testLabel.setStyle("-fx-background-color: blue");
        layout.add(testLabel, (1*60/5)+(30/5) +1, 3,2*60/5,1);

        layout.setPrefWidth(Integer.MAX_VALUE);
        //#endregion
        mainBox.setCenter(layout);

        return mainBox;
    }

    private static GridPane createScheduleBase(int locationRowCount){

        //#region schedule
        GridPane layout = new GridPane();
        //*12 for 5 minute increments making it 288x288 + 1x1 for the time and location row and column
        for (int i = 0; i < (24*12); i++) {
            if(i%12==0){//if 1 of 24
                Label hour = new Label(String.format("%s:00", i/12));
                if ((i/12)%2==0) {
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
