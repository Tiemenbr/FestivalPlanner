package Objects;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ScheduleItem implements CRUD, Serializable {
    private int id;
    private int locationId;  //todo replace with locationId
    private int attractionId;  //replace with attractionId
    private LocalTime startTime, endTime;

    public ScheduleItem(int id, Location location, Attraction attraction, String startTime, String endTime) {
        this.id = id; //todo add proper dynamic id assignment
        this.locationId = location.getId();
        this.attractionId = attraction.getId();
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


}