import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
        VBox plannerTabBox = new VBox(10);
        Label plannerLabel = new Label("planner page");
        Label tempSeedDataButtonLabel = new Label("TEMPORARY button for test data:");
        Button tempSeedDataButton = new Button("create test data");
        tempSeedDataButton.setOnAction(e ->{
            Planner.seedTestData();
        });
        plannerTabBox.getChildren().addAll(plannerLabel,tempSeedDataButtonLabel,tempSeedDataButton);
        plannerTab.setContent(plannerTabBox);

        Tab scheduleTab = new Tab("Schedule");
        Label scheduleLabel = new Label("page with visualization of the schedule");
        scheduleTab.setContent(scheduleLabel);

        Tab createScheduleItem = new Tab("Create ScheduleItem");
        createScheduleItem.setContent(CreateScheduleItem.getComponent());

        Tab attractionRead = new Tab("Attractions");
        attractionRead.setContent(AttractionsOverview.getComponent());


        tabpane.getTabs().addAll(plannerTab, scheduleTab, createScheduleItem, attractionRead);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
}
