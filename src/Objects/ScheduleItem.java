package Objects;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ScheduleItem implements CRUD, Serializable {
    private int id;
    private int locationId;  //todo replace with locationId
    private int attractionId;  //replace with attractionId
    private DayOfWeek day;
    private LocalTime startTime, endTime;

    public ScheduleItem(int id, Location location, Attraction attraction, DayOfWeek day, String startTime, String endTime) {
        this.id = id; //todo add proper dynamic id assignment
        this.locationId = location.getId();
        this.attractionId = attraction.getId();
        this.day = day;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);

        this.update();
    }

    public Integer getId() {
        return this.id;
    }

    public Location getLocation(Schedule schedule) {
        return schedule.getLocation(this.locationId);
    }

    public Attraction getAttraction(Schedule schedule) {
        return schedule.getAttraction(this.attractionId);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }


    public LocalTime getDuration() {
        LocalTime returnTime = LocalTime.MIDNIGHT;
        return returnTime.plusMinutes(this.startTime.until(this.endTime, ChronoUnit.MINUTES));
    }

    public DayOfWeek getDay() {
        return day;
    }

    public int getLocationId() {
        return locationId;
    }

    public ScheduleItem() {
    }

    @Override
    public void update() {
        IOController.update(this.id, this, IOController.ObjectType.SCHEDULE_ITEM);
    }

    @Override
    public void delete(Schedule schedule) {
        //todo idk if it's right to pass the schedule... but it needs to access it somewhere I think
        schedule.deleteScheduleItem(this.getId());
        IOController.delete(this.id, IOController.ObjectType.SCHEDULE_ITEM);
    }


}
