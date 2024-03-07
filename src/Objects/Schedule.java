package Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Schedule {

    private HashMap<UUID, ScheduleItem> scheduleItems = new HashMap<>();
    private HashMap<UUID, Attraction> attractions = new HashMap<>();
    private HashMap<UUID, Location> locations = new HashMap<>();
    //todo schedule doesn't need to have the visitors?
    //private ArrayList<Visitor> visitors = new ArrayList<>();


    public Schedule() {
        this.scheduleItems = new HashMap<>();
        this.attractions = new HashMap<>();
        this.locations = new HashMap<>();
    }

    public HashMap<UUID, ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public ScheduleItem getScheduleItem(UUID id) {
        return scheduleItems.get(id);
    }

    public void addScheduleItem(ScheduleItem scheduleItem) {
        System.out.println(scheduleItem);
        this.scheduleItems.put(scheduleItem.getId(), scheduleItem);
    }

    public void deleteScheduleItem(UUID id){
        this.scheduleItems.remove(id);
    }

    public HashMap<UUID, Attraction> getAttractions() {
        return attractions;
    }

    public Attraction getAttraction(UUID id) {
        return attractions.get(id);
    }

    public void addAttraction(Attraction attraction) {
        this.attractions.put(attraction.getId(), attraction);
    }

    public void deleteAttraction(UUID id){
        this.attractions.remove(id);
    }

    public HashMap<UUID, Location> getLocations() {
        return locations;
    }

    public Location getLocation(UUID id) {
        return locations.get(id);
    }

    public void addLocation(Location location) {
        this.locations.put(location.getId(), location);
    }

    public void deleteLocation(UUID id){
        this.locations.remove(id);
    }


    //todo schedule doesn't need to have the visitors does it?
//    public ArrayList<Visitor> getVisitors() {
//        return visitors;
//    }
//
//    public void setVisitors(ArrayList<Visitor> visitors) {
//        this.visitors = visitors;
//    }


}
