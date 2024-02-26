package gui;

import Objects.*;


public class Planner {
    public enum day {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    private static Schedule SCHEDULE = new Schedule();

    public void init() {
        SCHEDULE.addLocation(new Location(1,10, 10, "P1"));
        SCHEDULE.addLocation(new Location(2,170, 130, "P2"));
        SCHEDULE.addLocation(new Location(3,60, 45, "P3"));

        SCHEDULE.addAttraction(new Attraction(1,"draaimolen", 4, 5));
        SCHEDULE.addAttraction(new Attraction(2,"botsautos", 5, 7));

        SCHEDULE.addScheduleItem(new ScheduleItem(1, SCHEDULE.getLocation(1), SCHEDULE.getAttraction(1), ScheduleItem.day.MONDAY, "10:10", "10:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(2, SCHEDULE.getLocation(2), SCHEDULE.getAttraction(2), ScheduleItem.day.MONDAY,"10:30", "13:00"));

//        SCHEDULE.getVisitors().add(new Visitor());
//        SCHEDULE.getVisitors().add(new Visitor());
//        SCHEDULE.getVisitors().add(new Visitor());
//        SCHEDULE.getVisitors().add(new Visitor());
    }

    public static Schedule getSCHEDULE() {
        return SCHEDULE;
    }
}
