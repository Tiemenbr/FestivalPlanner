package Gui.SimulatorView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Visitor
{
    private Point2D position;
    private double angle;
    private double speed;
    private BufferedImage image;

    private Point2D targetPosition;
    private double scale = 0.2;

    public Visitor(Point2D position, double angle)
    {
        this.position = position;
        this.angle = angle;
        this.speed = 5 + Math.random()*4;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream("/visitor1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.targetPosition = new Point2D.Double(Math.random()*1000, Math.random()*1000);
    }

    public void update(ArrayList<Visitor> visitors)
    {
        double newAngle = Math.atan2(this.targetPosition.getY() - this.position.getY(), this.targetPosition.getX() - this.position.getX());

        double angleDifference = angle - newAngle;
        while(angleDifference > Math.PI)
            angleDifference -= 2 * Math.PI;
        while(angleDifference < -Math.PI)
            angleDifference += 2 * Math.PI;

        if(angleDifference < -0.1)
            angle += 0.1;
        else if(angleDifference > 0.1)
            angle -= 0.1;
        else
            angle = newAngle;

        Point2D newPosition = new Point2D.Double(
                this.position.getX() + speed * Math.cos(angle),
                this.position.getY() + speed * Math.sin(angle)
        );

        boolean hasCollision = false;

        for (Visitor visitor : visitors) {
            if(visitor != this)
                if(visitor.position.distance(newPosition) <= 64)
                    hasCollision = true;
        }

        if(!hasCollision)
            this.position = newPosition;
        else
            this.angle += 0.2;
    }


    public void draw(Graphics2D g2d)
    {

        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - image.getWidth()/(2/scale), position.getY()- image.getHeight()/(2/scale));
        tx.scale(scale,scale);
        tx.rotate(angle, image.getWidth()/2, image.getHeight()/2);
        g2d.drawImage(image, tx, null);

        g2d.setColor(Color.RED);
        g2d.fill(new Ellipse2D.Double(position.getX()-5, position.getY()-5, 10, 10));

    }

    public void setTargetPosition(Point2D targetPosition)
    {
        this.targetPosition = targetPosition;
    }

    public Point2D getPosition()
    {
        return this.position;
    }
}
