package Objects;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Location {
    private Point2D positionScreen;
    private int height, width;
    private String name;

    public Location(Point2D positionScreen, int height, int width, String name) {
        this.positionScreen = positionScreen;
        this.height = height;
        this.width = width;
        this.name = name;
    }

    public Point2D getPositionScreen() {
        return positionScreen;
    }

    public void setPositionScreen(Point2D positionScreen) {
        this.positionScreen = positionScreen;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
