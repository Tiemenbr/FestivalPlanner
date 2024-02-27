package gui;

import Objects.Schedule;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class ScheduleView {
    private static ResizableCanvas background;
    private static Label[] hourLabels;
    public static Node createScheduleView(Schedule schedule) {
        BorderPane borderPane = new BorderPane();
        HBox hours = new HBox();
        VBox locations = new VBox();
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n";
        locations.setStyle(cssLayout);
        hours.setStyle(cssLayout);
        hourLabels = new Label[24];

        for (int i = 0; i < 24; i++) {
            Label label = new Label(String.format("%s:00", i));
            hourLabels[i] = label;
            hours.getChildren().add(label);
        }

        StackPane stackPane = new StackPane();
        background = new ResizableCanvas(g -> drawBackground(g), stackPane);

        hourLabels[hourLabels.length-1].widthProperty().addListener((obs, oldVal, newVal) -> {
            background.resize(background.getWidth(),background.getHeight());
        });

        borderPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (Label hourLabel : hourLabels) {
                hourLabel.setPrefWidth(newVal.intValue()/24);
            }
        });

        stackPane.setAlignment(Pos.TOP_LEFT);
        VBox scheduleItmes = new VBox(hours);
        GridPane gridPane = new GridPane();
        gridPane.add(new Rectangle(100,100),0,0);
        scheduleItmes.getChildren().add(gridPane);
        stackPane.getChildren().addAll(background, scheduleItmes);
        borderPane.setCenter(stackPane);

        Label test = new Label("location1");
        locations.getChildren().add(test);

        hourLabels[hourLabels.length-1].heightProperty().addListener((obs, oldVal, newVal) -> {
            locations.setTranslateY(newVal.doubleValue());
        });
        borderPane.setLeft(locations);
        return borderPane;
    }

    public static void drawScheduleItems(FXGraphics2D graphics2D) {
    }

    public static void drawBackground(FXGraphics2D graphics) {
        graphics.setColor(Color.GRAY);
        for (int i = 0; i < 12; i++) {
            graphics.fillRect((int)hourLabels[i*2].getLayoutX(),0,(int)hourLabels[i*2].getWidth(),(int)background.getHeight());
        }
    }
}
