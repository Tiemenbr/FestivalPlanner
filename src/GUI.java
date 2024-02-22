import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {
    private static Planner planner;
    public static void main(String[] args) {
        planner = new Planner();
        planner.init();
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kermis planner");

        TabPane tabpane = new TabPane();

        Tab plannerTab = new Tab("Planner");
        Label plannerLabel = new Label("planner page");
        plannerTab.setContent(plannerLabel);

        Tab scheduleTab = new Tab("Schedule");
        Label scheduleLabel = new Label("page with visualization of the schedule");
        scheduleTab.setContent(scheduleLabel);

        Tab createScheduleItem = new Tab("Create Schedule Item");
        createScheduleItem.setContent(CreateScheduleItem.getComponent());

        Tab attractionRead = new Tab("Attractions");
        attractionRead.setContent(AttractionsOverview.getComponent());

        Tab updateScheduleItem = new Tab("Update Schedule Item");
        updateScheduleItem.setContent(UpdateScheduleItem.getComponent());

        tabpane.getTabs().addAll(plannerTab, scheduleTab, createScheduleItem, attractionRead,updateScheduleItem);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
}
