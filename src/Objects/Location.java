package Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements CRUD, Serializable {
    private int id;
    private int height, width;
    private String name;
    private ArrayList<Visitor> visitors = new ArrayList<>();

    public Location(int id, int height, int width, String name) {
        this.id = id; //todo add proper dynamic id assignment
        this.height = height;
        this.width = width;
        this.name = name;

        this.update(); //to create file
    }

    public int getId() {
        return this.id;
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
