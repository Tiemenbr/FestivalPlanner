import Objects.*;

import java.util.ArrayList;


public class Planner {
    public enum day {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    private static Schedule schedule = new Schedule();

    public void init(){
        IOController.init();


        if(schedule.getLocations().isEmpty() && schedule.getAttractions().isEmpty() && schedule.getScheduleItems().isEmpty()){
            ArrayList<Object> locations = IOController.getObjectsFromDirectory(IOController.ObjectType.LOCATION);
            for(Object location : locations){
                schedule.addLocation((Location) location);
            }
            ArrayList<Object> attractions = IOController.getObjectsFromDirectory(IOController.ObjectType.ATTRACTION);
            for(Object attraction : attractions){
                schedule.addAttraction((Attraction) attraction);
            }
            ArrayList<Object> scheduleItems = IOController.getObjectsFromDirectory(IOController.ObjectType.SCHEDULE_ITEM);
            for(Object scheduleItem : scheduleItems){
                schedule.addScheduleItem((ScheduleItem) scheduleItem);
            }

        }

        Location location = new Location(60, 45, "P5");
        schedule.addLocation(location);


    }

    public static Schedule getSchedule() {
        return schedule;
    }

    public static void seedTestData(){
        //todo only call constructors for test data if aren't already stored
        //todo isn't updating the pages that already have the schedule object
        Location location1 = new Location(10, 10, "P1");
        Location location2 = new Location(170, 130, "P2");
        Location location3 = new Location(60, 45, "P3");
        schedule.addLocation(location1);
        schedule.addLocation(location2);
        schedule.addLocation(location3);
        System.out.println("added "+schedule.getLocations().size()+" location objects");

        Attraction attraction1 = new Attraction("draaimolen", 4, 5);
        Attraction attraction2 = new Attraction("botsautos", 5, 7);
        schedule.addAttraction(attraction1);
        schedule.addAttraction(attraction2);
        System.out.println("added "+schedule.getAttractions().size()+" attraction objects");
        System.out.println(schedule.getAttraction(schedule.getAttractions().keySet().stream().findFirst().get()));

        schedule.addScheduleItem(new ScheduleItem(location1, attraction1, ScheduleItem.day.MONDAY, "10:10", "10:30"));
        schedule.addScheduleItem(new ScheduleItem(location2, attraction2, ScheduleItem.day.MONDAY, "10:30", "13:00"));
        System.out.println("added "+schedule.getScheduleItems().size()+" scheduleItem objects");
//            schedule.getVisitors().add(new Visitor());
//            schedule.getVisitors().add(new Visitor());
//            schedule.getVisitors().add(new Visitor());
//            schedule.getVisitors().add(new Visitor());
    }
}
