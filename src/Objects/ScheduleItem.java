package Objects;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ScheduleItem {
    Location location;
    Attraction attraction;
    LocalTime startTime, endTime;

    public ScheduleItem(Attraction attraction, Location location, String startTime, String endTime) {
        this.attraction = attraction;
        this.location = location;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public long getDurration(ChronoUnit unit){
        return startTime.until(endTime, unit);

    }
}
