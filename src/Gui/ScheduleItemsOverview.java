package gui;

import Objects.Attraction;
import Objects.Schedule;
import Objects.ScheduleItem;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.UUID;

public class ScheduleItemsOverview{
    public static VBox getComponent(){

        Schedule schedule = Planner.getSCHEDULE();

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));

        Label label = new Label("Overview of ScheduleItems:");
        mainBox.getChildren().add(label);

        if(schedule.getScheduleItems().isEmpty()){
            Label labelEmpty = new Label("No ScheduleItems yet");
            mainBox.getChildren().add(labelEmpty);
        }else {

            HBox listsContainerBox = new HBox();

            ListView<String> columnAttraction = new ListView<>();
            columnAttraction.getItems().add("Attraction");

            ListView<String> columnLocation = new ListView<>();
            columnLocation.getItems().add("Location");

            ListView<String> columnDay = new ListView<>();
            columnDay.getItems().add("Day");

            ListView<String> columnStartTime = new ListView<>();
            columnStartTime.getItems().add("StartTime");

            ListView<String> columnEndTime = new ListView<>();
            columnEndTime.getItems().add("EndTime");

            ListView<Button> columnDelete = new ListView<>();
            Button deleteButtonLabel = new Button("Delete");
            deleteButtonLabel.setPadding(new Insets(0));
            deleteButtonLabel.setStyle("-fx-border: none; -fx-background-color: none;");
            columnDelete.getItems().add(deleteButtonLabel);

            listsContainerBox.getChildren().addAll(columnAttraction, columnLocation, columnDay, columnStartTime, columnEndTime, columnDelete);

            HashMap<UUID, ScheduleItem> scheduleItems = Planner.getSCHEDULE().getScheduleItems();
            for (UUID key : scheduleItems.keySet()) {
                columnAttraction.getItems().add(scheduleItems.get(key).getAttraction(schedule).getName());
                columnLocation.getItems().add(scheduleItems.get(key).getLocation(schedule).getName());
                columnDay.getItems().add(scheduleItems.get(key).getDay().toString());
                columnStartTime.getItems().add(scheduleItems.get(key).getStartTime().toString());
                columnEndTime.getItems().add(scheduleItems.get(key).getEndTime().toString());
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> {
                    scheduleItems.get(key).delete(schedule);
                });
                deleteButton.setPadding(new Insets(0));
                columnDelete.getItems().add(deleteButton);
            }


            columnAttraction.setPrefWidth(120);
            columnLocation.setPrefWidth(100);
            columnDay.setPrefWidth(100);
            columnStartTime.setPrefWidth(100);
            columnEndTime.setPrefWidth(100);
            columnDelete.setPrefWidth(80);

            mainBox.getChildren().add(listsContainerBox);
        }


        return mainBox;
    }
}

