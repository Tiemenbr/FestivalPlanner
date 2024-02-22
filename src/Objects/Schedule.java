package Objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {

    private HashMap<Integer, ScheduleItem> scheduleItems = new HashMap<>();
    private HashMap<Integer, Attraction> attractions = new HashMap<>();
    private HashMap<Integer, Location> locations = new HashMap<>();
    //todo schedule doesn't need to have the visitors?
    //private ArrayList<Visitor> visitors = new ArrayList<>();


    public HashMap<Integer, ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public ScheduleItem getScheduleItem(int id) {
        return scheduleItems.get(id);
    }

    public void addScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItems.put(scheduleItem.getId(), scheduleItem);
    }

    public void deleteScheduleItem(int id){
        this.scheduleItems.remove(id);
    }

    public HashMap<Integer, Attraction> getAttractions() {
        return attractions;
    }

    public Attraction getAttraction(int id) {
        return attractions.get(id);
    }

    public void addAttraction(Attraction attraction) {
        this.attractions.put(attraction.getId(), attraction);
    }

    public void deleteAttraction(int id){
        this.attractions.remove(id);
    }

    public HashMap<Integer, Location> getLocations() {
        return locations;
    }

    public Location getLocation(int id) {
        return locations.get(id);
    }

    public void addLocation(Location location) {
        this.locations.put(location.getId(), location);
    }

    public void deleteLocation(int id){
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
