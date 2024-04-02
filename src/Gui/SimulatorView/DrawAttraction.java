package Gui.SimulatorView;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class DrawAttraction {
    private SpriteSheetHelper spriteSheetHelper;
    private BufferedImage[] attraction;
    private BufferedImage currentAttraction;
    private int attractionNumber;
    private double scale = 2.0;
    private double x=15;
    private double y=15;

    public void setAttraction(String attractionName){
        spriteSheetHelper = new SpriteSheetHelper();
        this.attraction = spriteSheetHelper.createSpriteSheet("/.attractions.png",4);
        this.attractionNumber = nameToNumber(attractionName);
        currentAttraction = attraction[attractionNumber];
    }
    public void draw(Graphics2D g2d){
        AffineTransform tx = new AffineTransform();
        tx.translate(x-currentAttraction.getWidth()/(2/scale),y-currentAttraction.getHeight()/(2/scale));
        tx.scale(scale,scale);
        g2d.drawImage(currentAttraction,tx,null);
    }
    public int nameToNumber(String attractionName){
        return switch (attractionName) {
            case "spookhuis" -> 0;
            case "achtbaan" -> 1;
            case "carrousel" -> 2;
            case "reuzenrad" -> 3;
            default -> 0;
        };
    }
    public void setCoördinates(Point2D location){
        setX(location.getX());
        setY(location.getY());
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
