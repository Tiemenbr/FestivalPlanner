package Gui.SimulatorView;

import Objects.Attraction;
import Objects.Schedule;
import Objects.ScheduleItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class DrawAttraction {
    private SpriteSheetHelper spriteSheetHelper;
    private BufferedImage[] attraction;
    private Attraction currentAttraction;
    private double scale = 2.0;
    private double x = 0;
    private double y = 0;
    private BufferedImage currentAttractionImage;

    public void draw(Graphics2D g2d) {
        if(currentAttractionImage == null)
            return;
        AffineTransform tx = new AffineTransform();
        tx.translate(x + currentAttractionImage.getWidth() / (2 / scale), y + currentAttractionImage.getHeight() / (2 / scale));
        tx.scale(scale, scale);
        g2d.drawImage(currentAttractionImage, tx, null);
    }


    public void setAttractions(ArrayList<ScheduleItem> currentScheduleItems, Schedule schedule) {
        for (ScheduleItem scheduleItem: currentScheduleItems) {
            currentAttraction = scheduleItem.getAttraction(schedule);
            this.x = scheduleItem.getLocation(schedule).getPosition().getX();
            this.y = scheduleItem.getLocation(schedule).getPosition().getY();
            try {
                currentAttractionImage = ImageIO.read(getClass().getResource(currentAttraction.getImagePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clear() {
    }
}
