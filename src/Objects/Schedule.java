package Objects;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Attraction> attractions = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<ScheduleItem> items = new ArrayList<>();

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<ScheduleItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ScheduleItem> items) {
        this.items = items;
    }
}
