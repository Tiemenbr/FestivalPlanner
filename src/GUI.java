import Objects.CalendarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class GUI extends Application {

    private static Planner planner;
    private FXMLLoader fxmlLoader;
    private Objects.CalendarController calendarController;
    public static void main(String[] args) {
        // On startup
        planner = new Planner();
        planner.init();
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.fxmlLoader = new FXMLLoader(GUI.class.getResource("/Calendar.fxml"));
        this.calendarController = new Objects.CalendarController();
        //this.calendarController.addCalendarActivity(1,)

        stage.setTitle("Kermis planner");

        TabPane tabpane = new TabPane();

        Tab planner = new Tab("Planner");
        Label plannerLabel = new Label("planner page");
        planner.setContent(plannerLabel);

        Tab schedule = new Tab("Schedule");
        HBox agendaOverviewBox = new HBox();
        agendaOverviewBox.alignmentProperty().set(Pos.CENTER);
        agendaOverviewBox.getChildren().add(fxmlLoader.load());
        schedule.setContent(agendaOverviewBox);

        tabpane.getTabs().addAll(planner, schedule);
        tabpane.setTabClosingPolicy(UNAVAILABLE);

        Scene scene = new Scene(tabpane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }
}
