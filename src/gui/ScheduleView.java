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



        borderPane.setTop(hours);
        borderPane.setLeft(scheduleItems);

        StackPane stackPane = new StackPane();
        Canvas background = new Canvas(1000,100);
        GraphicsContext gc = background.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, 100, 100);
        background.widthProperty().addListener((obs, oldVal, newVal) -> {
            gc.clearRect(0, 0, background.getWidth(), background.getHeight());
            for (int i = 0; i < 12; i++) {
                gc.fillRect(hourLabels[2].getLayoutX() * i,0, hourLabels[1].getLayoutX(), background.getHeight());
            }
        });

        borderPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Label hourLabel : hourLabels) {
                hourLabel.setPrefWidth(newVal.intValue()/24);
                System.out.println(hourLabel.getLayoutX());
            }
            background.setWidth(newVal.intValue());
        });

        background.heightProperty().addListener((obs, oldVal, newVal) -> {
            gc.clearRect(0, 0, background.getWidth(), background.getHeight());
            for (int i = 0; i < 12; i++) {
                gc.fillRect(hourLabels[2].getLayoutX() * i,0, hourLabels[1].getLayoutX(), newVal.intValue());
            }
        });

        borderPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            background.setHeight(newVal.intValue());
        });

        stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.getChildren().addAll(background, borderPane);
        return stackPane;
    }
}
