import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    public static void main(String[] args) {
        Planner planner = new Planner();
        planner.init();
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kermis planner");

        TabPane tabpane = new TabPane();

        Tab planner = new Tab("Planner");
        Label plannerLabel = new Label("planner page");
        planner.setContent(plannerLabel);

        Tab schedule = new Tab("Schedule");
        BorderPane borderPane = new BorderPane();
        ResizableCanvas canvas = new ResizableCanvas(g -> draw(g), borderPane);
        borderPane.getChildren().addAll(canvas);
        schedule.setContent(borderPane);

        tabpane.getTabs().addAll(planner, schedule);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void draw(FXGraphics2D graphics) {

    }
}
