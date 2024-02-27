package gui;

import Objects.Schedule;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ScheduleView {
    public static Node createScheduleView(Schedule schedule) {
        GridPane layout = new GridPane();
        for (int i = 0; i < 24; i++) {
            Label hour = new Label(String.format("%s:00", i));
            if (i%2==0) {
                Pane grayPane = new Pane();
                grayPane.setBackground(new Background(
                        new BackgroundFill(Color.valueOf("#808080"),
                        new CornerRadii(0),
                        new Insets(0))));
                // value 10 rowspan should be replaced with amount of locations
                layout.add(grayPane,i+1,0, 1,10);
                GridPane.setVgrow(grayPane, Priority.ALWAYS);
            }
            layout.add(hour,i+1,0);
            GridPane.setHgrow(hour,Priority.ALWAYS);
        }
        Label testLocation = new Label("Location 1");
        layout.add(testLocation,0,1);
        GridPane.setVgrow(testLocation, Priority.ALWAYS);
        GridPane.setValignment(testLocation, VPos.TOP);
        return layout;
    }
}
