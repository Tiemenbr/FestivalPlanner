package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Location implements CRUD, Serializable {
    private UUID id;
    private int height, width;
    private String name;
    private ArrayList<Visitor> visitors = new ArrayList<>();

    public Location(int height, int width, String name) {
        this.id = UUID.randomUUID();
        this.height = height;
        this.width = width;
        this.name = name;

        this.update(); //to create file
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public ArrayList<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(ArrayList<Visitor> visitors) {
        this.visitors = visitors;
    }


    @Override
    public void update() {
        IOController.update(this.id, this, IOController.ObjectType.LOCATION);
    }

    @Override
    public void delete(Schedule schedule) {
        //todo idk if it's right to pass the schedule... but it needs to access it somewhere I think
        schedule.deleteLocation(this.getId());
        IOController.delete(this.id, IOController.ObjectType.LOCATION);
    }


}
