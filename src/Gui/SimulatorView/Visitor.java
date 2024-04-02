package Gui.SimulatorView;

import Gui.Pathfinding.Tile;
import Objects.Schedule;
import Objects.ScheduleItem;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Visitor {

    private final double baseSpeed = 1;
    private Point2D position;
    private double angle;
    private double speed;
    private BufferedImage[] sprites;
    private BufferedImage currentImage;
    private double imageIndex;
    private double targetTimer;
    private boolean readyForNewTarget;
    private Point2D target;
    private Stack<Tile> targetTiles;
    private String targetLocationName;

    private double scale = 1.0; //2.0
    private double hitboxSize;
    private HashMap<String, HashMap<Tile,Integer>> distanceMaps;
    private Tile[][] pathfindingTiles;

    public Visitor(Point2D pos, double direction, HashMap<String,HashMap<Tile,Integer>> distanceMaps, Tile[][] pathfindingTiles) {
        this.position = pos;
        this.speed = baseSpeed + Math.random()/**4*/;
        this.angle = Math.toRadians(direction);

        this.target = new Point2D.Double(Math.random() * 1000, Math.random() * 1000);
        this.readyForNewTarget = true;
        this.targetTiles = new Stack<>();
        this.targetLocationName = "";
        this.targetTimer = 0;

        this.imageIndex = 0;
        SpriteSheetHelper ssh = new SpriteSheetHelper();
        this.sprites = ssh.createSpriteSheet("/walk template 2.png", 4);

        currentImage = sprites[8];
        this.hitboxSize = sprites[8].getHeight()*scale;
        this.distanceMaps = distanceMaps;
        this.pathfindingTiles = pathfindingTiles;
    }


    public void draw(Graphics2D g2d) {

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - currentImage.getWidth() / (2 / scale), position.getY() - currentImage.getHeight() / (2 / scale));
        tx.scale(scale, scale);
        g2d.drawImage(currentImage, tx, null);

        if (drawHitbox) {
            g2d.setColor(Color.RED);
            g2d.fill(new Ellipse2D.Double(position.getX() - 3, position.getY() - 3, 6, 6));
            g2d.draw(new Ellipse2D.Double(position.getX() - (hitboxSize / 2), position.getY() - (hitboxSize / 2), hitboxSize, hitboxSize));
        }
    }

    public void update(ArrayList<Visitor> visitors, double time) {
        //#region rotation
        double newAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());

        double angleDifference = angle - newAngle;
        while (angleDifference > Math.PI)
            angleDifference -= 2 * Math.PI;
        while (angleDifference < -Math.PI)
            angleDifference += 2 * Math.PI;

        if(angleDifference < -time)
            angle += (time*speed*3);
        else if(angleDifference > time)
            angle -= (time*speed*3);
        else
            angle = newAngle;
        //#endregion
        //#region sprite direction
        int imageOffset = 0;
        double testAngle = Math.toDegrees(angle);
        //thought the Math.PI version of this handled overflow but I guess not?
        while (testAngle < -180) {
            testAngle += 360;
        }
        while (testAngle > 180) {
            testAngle -= 360;
        }

        if (testAngle > -135 && testAngle < -45) {
//            walkDirection = NORTH;
            imageOffset = 3 * 4;
        } else if (testAngle < -135 && testAngle > -180 || testAngle < 180 && testAngle > 135) {
//            walkDirection = WEST;
            imageOffset = 1 * 4;
        } else if (testAngle < 135 && testAngle > 45) {
//            walkDirection = SOUTH;
            imageOffset = 2 * 4;
        } else if (testAngle > -45 && testAngle < 45) {
//            walkDirection = EAST;
            imageOffset = 0;
        }
        //#endregion
        //#region sprite animation
        imageIndex += time * (speed * 2);

        if (imageIndex >= 4) {
            imageIndex = 0;
        }
        //#endregion
        //#region movement
        currentImage = sprites[(int) imageIndex + imageOffset];
        Point2D newPosition = new Point2D.Double(
                this.position.getX() + speed * Math.cos(angle),
                this.position.getY() + speed * Math.sin(angle)
        );
        //#endregion
        //#region collision
        boolean hasCollision = false;
        for (Visitor visitor : visitors) {
            if (visitor != this)
                if (visitor.getPosition().distance(newPosition) <= hitboxSize)
                    hasCollision = true;
        }

        if (collision.getMap()[(int) newPosition.getY()/32][(int) newPosition.getX()/32] == 50)
            hasCollision = true;
        if(!hasCollision)

            this.position = newPosition;
        else
            this.angle += 0.2;
        //#endregion

        //#region target timer
        if (!readyForNewTarget && this.position.distance(target) < 100) {
//            System.out.println("target timer +1! (" + targetTimer);
            targetTimer += time;
        }

        if(targetTimer > 0){
            readyForNewTarget = true;
        }
        //#endregion
    }

    public void setTargetPosition(ArrayList<ScheduleItem> targetOption, Schedule schedule)
    {
        if(readyForNewTarget && !targetOption.isEmpty() && targetTiles.empty()){
//            System.out.println("Visitor target options:  0-"+(targetOption.size()-1));
            double rand = (Math.random()*targetOption.size());
            ScheduleItem item = targetOption.get((int) rand);
            this.targetLocationName = item.getLocation(schedule).getName();
//            System.out.println("new target!: " + item.getAttraction(schedule).getName());

            //set target to center of location
            int x = (int) ((this.position.getX())/32);
            int y = (int) ((this.position.getY())/32);
            createPath(this.pathfindingTiles[x][y]);
            if (!this.targetTiles.empty()) {
                Tile selected = this.targetTiles.pop();
                this.target = new Point2D.Double(selected.getX(), selected.getY());
            }
            readyForNewTarget = false;
            targetTimer = 0;
        } else if (readyForNewTarget && !targetTiles.empty() && this.position.distance(target) < 8) {
            Tile selected = this.targetTiles.pop();
            this.target = new Point2D.Double(selected.getX(), selected.getY());
            targetTimer = 0;
            readyForNewTarget = false;
        }
    }

    private void createPath(Tile tile) {
        Tile tileToAdd = new Tile();
        for (Tile neighborTile : tile.getNeighborTiles()) {
            if (this.distanceMaps.get(this.targetLocationName).get(neighborTile) < this.distanceMaps.get(this.targetLocationName).get(tile))
                tileToAdd = neighborTile;
        }
        if (tileToAdd.isSet()) {
            createPath(tileToAdd);
            this.targetTiles.push(tileToAdd);
        }
    }

    public Point2D getPosition()
    {
        return this.position;
    }

    public double getHitBoxSize() {
        return this.hitboxSize;
    }
}
