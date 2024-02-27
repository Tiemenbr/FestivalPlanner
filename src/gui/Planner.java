package gui;

import Objects.*;

import java.time.DayOfWeek;


public class Planner {
    private static Schedule SCHEDULE = new Schedule();

    public void init() {
        SCHEDULE.addLocation(new Location(1,10, 10, "P1"));
        SCHEDULE.addLocation(new Location(2,170, 130, "P2"));
        SCHEDULE.addLocation(new Location(3,60, 45, "P3"));

        SCHEDULE.addAttraction(new Attraction(1,"draaimolen", 4, 5));
        SCHEDULE.addAttraction(new Attraction(2,"botsautos", 5, 7));

        SCHEDULE.addScheduleItem(new ScheduleItem(1, SCHEDULE.getLocation(1), SCHEDULE.getAttraction(1), DayOfWeek.MONDAY, "10:10", "12:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(2, SCHEDULE.getLocation(2), SCHEDULE.getAttraction(2), DayOfWeek.MONDAY,"10:30", "13:00"));
        SCHEDULE.addScheduleItem(new ScheduleItem(3, SCHEDULE.getLocation(1), SCHEDULE.getAttraction(2), DayOfWeek.MONDAY, "13:00", "16:30"));
        SCHEDULE.addScheduleItem(new ScheduleItem(4, SCHEDULE.getLocation(3), SCHEDULE.getAttraction(2), DayOfWeek.TUESDAY,"02:30", "13:00"));
        SCHEDULE.addScheduleItem(new ScheduleItem(5, SCHEDULE.getLocation(1), SCHEDULE.getAttraction(1), DayOfWeek.TUESDAY, "10:10", "18:45"));
        SCHEDULE.addScheduleItem(new ScheduleItem(6, SCHEDULE.getLocation(2), SCHEDULE.getAttraction(2), DayOfWeek.TUESDAY,"07:00", "16:00"));

//        SCHEDULE.getVisitors().add(new Visitor());
//        SCHEDULE.getVisitors().add(new Visitor());
//        SCHEDULE.getVisitors().add(new Visitor());
//        SCHEDULE.getVisitors().add(new Visitor());
    }

    public static Schedule getSCHEDULE() {
        return SCHEDULE;
    }
}
