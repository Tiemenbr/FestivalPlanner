package gui;

import Objects.Schedule;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScheduleView {
    public static Node createScheduleView(Schedule schedule) {
        BorderPane borderPane = new BorderPane();
        HBox hours = new HBox();
        VBox scheduleItems = new VBox();
        Label[] hourLabels = new Label[24];

        for (int i = 0; i < 24; i++) {
            Label label = new Label(String.format("%s:00", i));
            hourLabels[i] = label;
            hours.getChildren().add(label);
        }

        StackPane stackPane = new StackPane();
        Canvas background = new Canvas(1200, 600);
        GraphicsContext gc = background.getGraphicsContext2D();
        gc.setFill(Color.GRAY);

        hourLabels[hourLabels.length-1].layoutXProperty().addListener((obs, oldVal, newVal) -> {
            gc.clearRect(0, 0, background.getWidth(), background.getHeight());
            for (int i = 0; i < 12; i++) {
                gc.fillRect(hourLabels[0].getWidth() * 2 * i,0, hourLabels[0].getWidth(), background.getHeight());
            }
        });

        borderPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Label hourLabel : hourLabels) {
                hourLabel.setPrefWidth(newVal.intValue()/24);
            }
            background.setWidth(newVal.intValue());
        });

        background.heightProperty().addListener((obs, oldVal, newVal) -> {
            gc.clearRect(0, 0, background.getWidth(), background.getHeight());
            for (int i = 0; i < 12; i++) {
                gc.fillRect(hourLabels[0].getWidth() * 2 * i,0, hourLabels[0].getWidth(), background.getHeight());
            }
        });

        borderPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            background.setHeight(newVal.intValue());
        });

        stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.getChildren().addAll(background, hours);
        borderPane.setCenter(stackPane);
        borderPane.setLeft(scheduleItems);
        return borderPane;
    }
}
