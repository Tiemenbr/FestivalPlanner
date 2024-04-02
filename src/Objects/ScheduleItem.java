package Objects;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ScheduleItem implements CRUD, Serializable {
    private UUID id;
    private String locationId;
    private UUID attractionId;
    private DayOfWeek day;
    private LocalTime startTime, endTime;

    public ScheduleItem(Location location, Attraction attraction, DayOfWeek day, String startTime, String endTime) {
        this.id = UUID.randomUUID();
        this.locationId = location.getName();
        this.attractionId = attraction.getId();
        this.day = day;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);

        this.update();
    }

    public void setAll(Location location, Attraction attraction, DayOfWeek day, String startTime, String endTime) {
        this.locationId = location.getName();
        this.attractionId = attraction.getId();
        this.day = day;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);

        this.update();
    }

    public UUID getId() {
        return this.id;
    }

    public Location getLocation(Schedule schedule) {
        return schedule.getLocation(this.locationId);
    }

    public Attraction getAttraction(Schedule schedule) {
        return schedule.getAttraction(this.attractionId);
    }

    public DayOfWeek getDay() {
        return day;
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


    public String getLocationId() {
        return locationId;
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

    @Override
    public String toString() {
        return "ScheduleItem{" +
                "id=" + id +
                ", locationId=" + locationId +
                ", attractionId=" + attractionId +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
