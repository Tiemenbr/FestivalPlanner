package Gui.SimulatorView;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Visitor {

    private final double baseSpeed = 1;
//    private enum walkDir {NORTH,EAST,SOUTH,WEST};

    private Point2D position;
    private double angle;

//    private walkDir walkDirection;
    private double speed;
    private BufferedImage[] sprites;
    private BufferedImage currentImage;
    private double imageIndex;

    private Point2D target;
    private double scale = 1.0; //2.0
    private double hitboxSize;

    public Visitor(Point2D pos, double direction) {
        this.position = pos;
        this.speed = baseSpeed + Math.random()/**4*/;
        this.angle = Math.toRadians(direction);

        this.target = new Point2D.Double(Math.random()*1000, Math.random()*1000);

        this.imageIndex = 0;
        SpriteSheetHelper ssh = new SpriteSheetHelper();
        this.sprites = ssh.createSpriteSheet("/walk template 2.png",4);

        currentImage = sprites[8];
        this.hitboxSize = sprites[8].getHeight()*scale;
    }


    public void draw(Graphics2D g2d)
    {

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - currentImage.getWidth()/(2/scale), position.getY()- currentImage.getHeight()/(2/scale));
        tx.scale(scale,scale);
//        tx.rotate(angle, currentImage.getWidth()/2, currentImage.getHeight()/2);
        g2d.drawImage(currentImage, tx, null);

//        g2d.setColor(Color.RED);
//        g2d.fill(new Ellipse2D.Double(position.getX()-3, position.getY()-3, 6, 6));
//        g2d.draw(new Ellipse2D.Double(position.getX()-(hitboxSize/2),position.getY()-(hitboxSize/2),hitboxSize,hitboxSize));
    }

    public void update(ArrayList<Visitor> visitors, TileLayer collision, double time){
        double newAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());

        double angleDifference = angle - newAngle;
        while(angleDifference > Math.PI)
            angleDifference -= 2 * Math.PI;
        while(angleDifference < -Math.PI)
            angleDifference += 2 * Math.PI;

        if(angleDifference < -time)
            angle += time;
        else if(angleDifference > time)
            angle -= time;
        else
            angle = newAngle;

        int imageOffset = 0;
        double testAngle = Math.toDegrees(angle);
        //thought the Math.PI version of this handled overflow but I guess not?
        while(testAngle < -180){
            testAngle += 360;
        }
        while(testAngle > 180){
            testAngle -= 360;
        }

        if(testAngle > -135 && testAngle < -45){
//            walkDirection = walkDir.NORTH;
            imageOffset = 3*4;
        } else if(testAngle < -135 && testAngle > -180 || testAngle < 180 && testAngle > 135){
//            walkDirection = walkDir.WEST;
            imageOffset = 1*4;
        }else if(testAngle < 135 && testAngle > 45){
//            walkDirection = walkDir.SOUTH;
            imageOffset = 2*4;
        }else if(testAngle > -45 && testAngle < 45){
//            walkDirection = walkDir.EAST;
            imageOffset = 0;
        }
//        System.out.println(walkDirection);

        imageIndex += time*(speed*2);

        if(imageIndex >= 4){
            imageIndex = 0;
        }

        currentImage = sprites[(int)imageIndex+imageOffset];
        Point2D newPosition = new Point2D.Double(
                this.position.getX() + speed * Math.cos(angle),
                this.position.getY() + speed * Math.sin(angle)
        );
        boolean hasCollision = false;
        for (Visitor visitor : visitors) {
            if(visitor != this)
                if(visitor.getPosition().distance(newPosition) <= hitboxSize)
                    hasCollision = true;
        }

        if(!hasCollision)
            this.position = newPosition;
        else
            this.angle += 0.2;
    }

    public void setTargetPosition(Point2D targetPosition)
    {
        this.target = targetPosition;
    }

    public Point2D getPosition()
    {
        return this.position;
    }
    public double getHitBoxSize(){
        return this.hitboxSize;
    }
}
