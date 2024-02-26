package Objects;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ScheduleItem implements CRUD, Serializable {
    private UUID id;
    private UUID locationId;
    private UUID attractionId;
    public enum day {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};
    private day day;
    private LocalTime startTime, endTime;

    public ScheduleItem(Location location, Attraction attraction, day day, String startTime, String endTime) {
        this.id = UUID.randomUUID();
        this.locationId = location.getId();
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }


    public LocalTime getDurration() {
        LocalTime returnTime = LocalTime.MIDNIGHT;
        return returnTime.plusMinutes(this.startTime.until(this.endTime, ChronoUnit.MINUTES));
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
