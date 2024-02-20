import Objects.Attraction;
import Objects.ScheduleItem;
import Objects.Location;
import Objects.Schedule;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class GUI extends Application {
    private static Schedule schedule = new Schedule();

    public static void main(String[] args) {
        Location P1, P2, P3;
        Location[] locations = {
                P1 = new Location(new Point2D.Double(1, 1), 10, 10, "P1"),
                P2 = new Location(new Point2D.Double(1, 1), 170, 130, "P2"),
                P3 = new Location(new Point2D.Double(1, 1), 60, 45, "P3")
        };
        Attraction botsautos, draaimolen;
        Attraction[] attractions = {
                draaimolen = new Attraction("draaimolen", 10, 5),
                botsautos = new Attraction("botsautos", 20, 10)
        };
        schedule.getAttractions().addAll(Arrays.asList(attractions));

        schedule.getItems().add(new ScheduleItem(draaimolen, P1, "10:15", "11:45"));
        schedule.getItems().add(new ScheduleItem(draaimolen, P2, "11:45", "12:00"));
        schedule.getItems().add(new ScheduleItem(draaimolen, P1, "07:00", "10:10"));

        for (ScheduleItem item : schedule.getItems()) {
            System.out.println(item.getDurration(ChronoUnit.MINUTES));
            System.out.println(item.getAttraction().getName());
            System.out.println(item.getLocation().getName());
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
