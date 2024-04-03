package Gui;

import Objects.Attraction;
import Objects.IOController;
import Objects.Schedule;
import Objects.ScheduleItem;

import java.time.DayOfWeek;
import java.util.ArrayList;


public class Planner {
    private static Schedule SCHEDULE = new Schedule();

    public static void seedTestData() {
        //todo only call constructors for test data if aren't already stored
//
        Attraction attraction1 = new Attraction("merry-go-round", 4, 5, "/attractions/merry-go-round-animated.png");
        Attraction attraction2 = new Attraction("ferriswheel", 5, 7, "/attractions/ferris-wheel-animated.png");
        Attraction attraction3 = new Attraction("haunted house", 4, 5, "/attractions/spookhuis-animated.png");
        Attraction attraction4 = new Attraction("rollercoaster", 5, 7, "/attractions/rollercoaster-animated.png");
        SCHEDULE.addAttraction(attraction1);
        SCHEDULE.addAttraction(attraction2);
        SCHEDULE.addAttraction(attraction3);
        SCHEDULE.addAttraction(attraction4);
        System.out.println("added " + SCHEDULE.getAttractions().size() + " attraction objects");
        System.out.println(SCHEDULE.getAttraction(SCHEDULE.getAttractions().keySet().stream().findFirst().get()));
//
        SCHEDULE.addScheduleItem(new ScheduleItem(SCHEDULE.getLocations().get("location1"), attraction1, DayOfWeek.MONDAY, "10:10", "12:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(SCHEDULE.getLocations().get("location2"), attraction4, DayOfWeek.MONDAY, "10:30", "13:00"));
        SCHEDULE.addScheduleItem(new ScheduleItem(SCHEDULE.getLocations().get("location3"), attraction2, DayOfWeek.TUESDAY, "13:00", "16:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(SCHEDULE.getLocations().get("location1"), attraction1, DayOfWeek.TUESDAY, "10:10", "18:45"));
        SCHEDULE.addScheduleItem(new ScheduleItem(SCHEDULE.getLocations().get("location2"), attraction3, DayOfWeek.TUESDAY, "07:00", "16:00"));

        System.out.println("added " + SCHEDULE.getScheduleItems().size() + " scheduleItem objects");
    }

    public static Schedule getSCHEDULE() {
        return SCHEDULE;
    }

    public void init() {
        IOController.init();
        if (SCHEDULE.getAttractions().isEmpty() && SCHEDULE.getScheduleItems().isEmpty()) {
            ArrayList<Object> attractions = IOController.getObjectsFromDirectory(IOController.ObjectType.ATTRACTION);
            for (Object attraction : attractions) {
                SCHEDULE.addAttraction((Attraction) attraction);
            }
            ArrayList<Object> scheduleItems = IOController.getObjectsFromDirectory(IOController.ObjectType.SCHEDULE_ITEM);
            for (Object scheduleItem : scheduleItems) {
                SCHEDULE.addScheduleItem((ScheduleItem) scheduleItem);
            }
        }
    }
}
