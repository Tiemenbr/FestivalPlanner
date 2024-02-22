package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import planner.Planner;

import java.awt.geom.Rectangle2D;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private ResizableCanvas canvas;

    public static void main(String[] args) {
        Planner planner = new Planner();
        planner.init();
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kermis planner");

        TabPane tabpane = new TabPane();

        Tab planner = new Tab("planner.Planner");
        Label plannerLabel = new Label("planner page");
        planner.setContent(plannerLabel);

        Tab schedule = new Tab("Schedule");
        BorderPane borderPane = new BorderPane();
        this.canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.getChildren().addAll(this.canvas);
        schedule.setContent(borderPane);

        tabpane.getTabs().addAll(planner, schedule);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(600);
        stage.show();
    }

    private void draw(FXGraphics2D graphics) {
        graphics.draw(new Rectangle2D.Double(0,0,canvas.getWidth(),50));
        graphics.draw(new Rectangle2D.Double(0,50,canvas.getWidth(),25));
        graphics.draw(new Rectangle2D.Double(0,75,75,canvas.getHeight()));
        graphics.draw(new Rectangle2D.Double(75,75,canvas.getWidth(),canvas.getHeight()-75));
    }
}
