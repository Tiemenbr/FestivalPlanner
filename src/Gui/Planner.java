package Gui;

import Objects.*;

import java.time.DayOfWeek;
import java.util.ArrayList;


public class Planner {
    private static Schedule SCHEDULE = new Schedule();

    public void init(){
        IOController.init();


        if(SCHEDULE.getLocations().isEmpty() && SCHEDULE.getAttractions().isEmpty() && SCHEDULE.getScheduleItems().isEmpty()){
            ArrayList<Object> locations = IOController.getObjectsFromDirectory(IOController.ObjectType.LOCATION);
            for(Object location : locations){
                SCHEDULE.addLocation((Location) location);
            }
            ArrayList<Object> attractions = IOController.getObjectsFromDirectory(IOController.ObjectType.ATTRACTION);
            for(Object attraction : attractions){
                SCHEDULE.addAttraction((Attraction) attraction);
            }
            ArrayList<Object> scheduleItems = IOController.getObjectsFromDirectory(IOController.ObjectType.SCHEDULE_ITEM);
            for(Object scheduleItem : scheduleItems){
                SCHEDULE.addScheduleItem((ScheduleItem) scheduleItem);
            }

        }

//        Location location = new Location(60, 45, "P5");
//        SCHEDULE.addLocation(location);


    }

    public static void seedTestData(){
        //todo only call constructors for test data if aren't already stored
        //todo isn't updating the pages that already have the schedule object
        Location location1 = new Location(10, 10, "P1");
        Location location2 = new Location(170, 130, "P2");
        Location location3 = new Location(60, 45, "P3");
        SCHEDULE.addLocation(location1);
        SCHEDULE.addLocation(location2);
        SCHEDULE.addLocation(location3);
        System.out.println("added "+SCHEDULE.getLocations().size()+" location objects");

        Attraction attraction1 = new Attraction("draaimolen", 4, 5);
        Attraction attraction2 = new Attraction("botsautos", 5, 7);
        SCHEDULE.addAttraction(attraction1);
        SCHEDULE.addAttraction(attraction2);
        System.out.println("added "+SCHEDULE.getAttractions().size()+" attraction objects");
        System.out.println(SCHEDULE.getAttraction(SCHEDULE.getAttractions().keySet().stream().findFirst().get()));

        SCHEDULE.addScheduleItem(new ScheduleItem(location1, attraction1, DayOfWeek.MONDAY,  "10:10", "12:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(location2, attraction2, DayOfWeek.MONDAY, "10:30", "13:00"));
        SCHEDULE.addScheduleItem(new ScheduleItem(location3, attraction2, DayOfWeek.TUESDAY, "13:00", "16:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(location1, attraction1, DayOfWeek.TUESDAY, "10:10", "18:45"));
        SCHEDULE.addScheduleItem(new ScheduleItem(location2, attraction2, DayOfWeek.TUESDAY,"07:00", "16:00"));

        System.out.println("added "+SCHEDULE.getScheduleItems().size()+" scheduleItem objects");
//            schedule.getVisitors().add(new Visitor());
//            schedule.getVisitors().add(new Visitor());
//            schedule.getVisitors().add(new Visitor());
//            schedule.getVisitors().add(new Visitor());
    }

    public static Schedule getSCHEDULE() {
        return SCHEDULE;
    }
}
