package Objects;

import java.util.ArrayList;

public class Location {
    private int height, width;
    private String name;
    private ArrayList<Visitor> visitors = new ArrayList<>();

    public Location(int height, int width, String name) {
        this.height = height;
        this.width = width;
        this.name = name;
    }

    public ArrayList<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(ArrayList<Visitor> visitors) {
        this.visitors = visitors;
    }
}
