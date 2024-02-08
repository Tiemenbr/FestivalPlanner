package Objects;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Attraction {
    private LocalTime startTime, endTime;
    private String name;
    private Location location;
    private int popularity = 1;
    private int prijs;


    public Attraction(String name, LocalTime startTime, LocalTime endTime, Location location, int popularity) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.popularity = popularity;
    }

    public Attraction(String name, LocalTime startTime, int duration, Location location, int popularity) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(duration);
        this.location = location;
        this.popularity = popularity;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public LocalTime getDurration() {
        LocalTime returnTime = LocalTime.MIDNIGHT;
        return returnTime.plusMinutes(this.startTime.until(this.endTime, ChronoUnit.MINUTES));
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", name='" + name + '\'' +
                '}';
    }
}
