package Objects;

import Objects.Observers.AttractionsObserver;
import Objects.Observers.LocationsObserver;
import Objects.Observers.ScheduleItemsObserver;

import java.util.HashMap;
import java.util.UUID;

public class Schedule {

    private HashMap<UUID, ScheduleItem> scheduleItems;
    private HashMap<UUID, Attraction> attractions;
    private HashMap<UUID, Location> locations;
    //todo schedule doesn't need to have the visitors?
    //private ArrayList<Visitor> visitors = new ArrayList<>();
    private ScheduleItemsObserver scheduleItemsObserver;
    private AttractionsObserver attractionsObserver;
    private LocationsObserver locationsObserver;

    public Schedule() {
        this.scheduleItems = new HashMap<>();
        this.attractions = new HashMap<>();
        this.locations = new HashMap<>();
        this.scheduleItemsObserver = new ScheduleItemsObserver(this);
        this.attractionsObserver = new AttractionsObserver(this);
        this.locationsObserver = new LocationsObserver(this);
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
        this.scheduleItemsObserver.update();
    }

    public void deleteScheduleItem(UUID id){
        this.scheduleItems.remove(id);
        this.scheduleItemsObserver.update();
    }

    public HashMap<UUID, Attraction> getAttractions() {
        return attractions;
    }

    public Attraction getAttraction(UUID id) {
        return attractions.get(id);
    }

    public void addAttraction(Attraction attraction) {
        this.attractions.put(attraction.getId(), attraction);
        this.attractionsObserver.update();
    }

    public void deleteAttraction(UUID id){
        this.attractions.remove(id);
        this.attractionsObserver.update();
    }

    public HashMap<UUID, Location> getLocations() {
        return locations;
    }

    public Location getLocation(UUID id) {
        return locations.get(id);
    }

    public void addLocation(Location location) {
        this.locations.put(location.getId(), location);
        this.locationsObserver.update();
    }

    public void deleteLocation(UUID id){
        this.locations.remove(id);
        this.locationsObserver.update();
    }

    public void setScheduleItemsObserver(ScheduleItemsObserver observer) {
        this.scheduleItemsObserver = observer;
    }

    public void setAttractionsObserver(AttractionsObserver attractionsObserver) {
        this.attractionsObserver = attractionsObserver;
    }

    public void setLocationsObserver(LocationsObserver locationsObserver) {
        this.locationsObserver = locationsObserver;
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
