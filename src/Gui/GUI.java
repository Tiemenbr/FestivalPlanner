package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
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

        Tab plannerTab = new Tab("Dev");
        VBox plannerTabBox = new VBox(10);
        Label plannerLabel = new Label("planner page");
        Label tempSeedDataButtonLabel = new Label("TEMPORARY button for test data:");
        Button tempSeedDataButton = new Button("create test data");
        tempSeedDataButton.setOnAction(e -> {
            Planner.seedTestData();
        });
        plannerTabBox.getChildren().addAll(plannerLabel, tempSeedDataButtonLabel, tempSeedDataButton);
        plannerTab.setContent(plannerTabBox);

        Tab scheduleTab = new Tab("Schedule");
        scheduleTab.setContent(ScheduleView.createScheduleView(Planner.getSCHEDULE()));

        Tab createScheduleItem = new Tab("Schedule Items");
        HBox scheduleItemBox = new HBox();
        scheduleItemBox.getChildren().addAll(ScheduleItemCreate.getComponent(), ScheduleItemUpdate.getComponent(), ScheduleItemsOverview.getComponent());
        createScheduleItem.setContent(scheduleItemBox);

        Tab attractionRead = new Tab("Attractions");
        HBox AttractionBox = new HBox();
        AttractionBox.getChildren().addAll(AttractionCreate.getComponent(), AttractionUpdate.getComponent(), AttractionsOverview.getComponent());
        attractionRead.setContent(AttractionBox);

        Tab simulator = new Tab("Simulator");
        if (planner.getSCHEDULE().getScheduleItems().size() > 0) {
            simulator.setContent(Simulator.getComponent());
        } else {
            simulator.setContent(new Label("No scheduleItems found for the simulation..."));
        }

        tabpane.getTabs().addAll(plannerTab, scheduleTab, createScheduleItem, attractionRead, simulator);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
}
