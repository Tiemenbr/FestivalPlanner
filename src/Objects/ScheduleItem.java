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

    // Constructor voor het maken van een ScheduleItem met de verchillende parameters
    public ScheduleItem(Location location, Attraction attraction, DayOfWeek day, String startTime, String endTime) {
        this.id = UUID.randomUUID();
        this.locationId = location.getName();
        this.attractionId = attraction.getId();
        this.day = day;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);

        this.update();
    }

    // Methode om alle velden van het ScheduleItem in één keer te updaten
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
        // Roep de IOController aan om dit ScheduleItem te updaten in de database
        IOController.update(this.id, this, IOController.ObjectType.SCHEDULE_ITEM);
    }

    @Override
    public void delete(Schedule schedule) {
        //todo idk if it's right to pass the schedule... but it needs to access it somewhere I think
        schedule.deleteScheduleItem(this.getId());
        // Verwijder dit ScheduleItem uit de database via de IOController
        IOController.delete(this.id, IOController.ObjectType.SCHEDULE_ITEM);
    }

    @Override
    public String toString() // Methode voor het printen van informatie over dit ScheduleItem
    {
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
