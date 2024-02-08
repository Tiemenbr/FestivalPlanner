import Objects.Attraction;
import Objects.Location;
import Objects.Schedule;
import Objects.Visitor;


public class Planner {
    private Schedule schedule = new Schedule();

    public void init() {
        schedule.getLocations().add(new Location(10, 10, "P1"));
        schedule.getLocations().add(new Location(170, 130, "P2"));
        schedule.getLocations().add(new Location(60, 45, "P3"));

        schedule.getAttractions().add(new Attraction("draaimolen", "10:10", "10:30", schedule.getLocations().get(1), 10));
        schedule.getAttractions().add(new Attraction("botsautos", "10:30", "11:00", schedule.getLocations().get(0), 20));

        schedule.getVisitors().add(new Visitor());
        schedule.getVisitors().add(new Visitor());
        schedule.getVisitors().add(new Visitor());
        schedule.getVisitors().add(new Visitor());
    }
}
