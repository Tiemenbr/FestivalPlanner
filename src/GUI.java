import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

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
        Label scheduleLabel = new Label("Visualization of the schedule page");
        schedule.setContent(scheduleLabel);

        tabpane.getTabs().addAll(planner,schedule);

        Scene scene = new Scene(tabpane,1200,600);
        stage.setScene(scene);
        stage.show();
    }
}
