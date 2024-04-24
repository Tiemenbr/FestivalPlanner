package Objects;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.UUID;

public class Location {
    private UUID id;
    private java.awt.geom.Point2D position;
    private int height, width;
    private String name;

    // Constructor om een nieuwe locatie te maken met de verschillende parameters.
    public Location(int height, int width, String name, Point2D.Double position) {
        this.id = UUID.randomUUID();
        this.height = height;
        this.width = width;
        this.name = name;
        this.position = position;

        // this.update(); //to create file
    }

    public UUID getId() {
        return this.id;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

//    @Override
//    public void update() {
//        IOController.update(this.id, this, IOController.ObjectType.LOCATION);
//    }

//    @Override
//    public void delete(Schedule schedule) {
//        //todo idk if it's right to pass the schedule... but it needs to access it somewhere I think
//        schedule.deleteLocation(this.getId());
//        IOController.delete(this.id, IOController.ObjectType.LOCATION);
//    }

    // Methode om de locatie te tekenen
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.white); // Stel de kleur in op wit.
        if (position != null) {
            // Teken de naam van de locatie.
            g2d.drawString(name, (int) position.getX() + width, (int) position.getY() + (height / 2));
            // Teken een rechthoek op de positie met opgegeven breedte en hoogte.
            g2d.drawRect((int) position.getX(), (int) position.getY(), width, height);
        }
    }

}
