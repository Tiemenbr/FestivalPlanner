package Gui.SimulatorView;

import Objects.Location;
import Objects.Schedule;
import Objects.ScheduleItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DrawAttraction {
    private SpriteSheetHelper spriteSheetHelper;
    private BufferedImage[] attraction;
    private double scale = 1.0;
    private double x = 0;
    private double y = 0;
    private ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
    private Schedule schedule;
    private HashMap<Location, BufferedImage> attractionImages = new HashMap<>();

    public void init(HashMap<UUID, ScheduleItem> scheduleItems, Schedule schedule) {
        this.schedule = schedule;
        for (UUID key : scheduleItems.keySet()) {
            try {
                attractionImages.put(scheduleItems.get(key).getLocation(schedule), ImageIO.read(getClass().getResource(scheduleItems.get(key).getAttraction(schedule).getImagePath())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void draw(Graphics2D g2d) {
        if (scheduleItems.isEmpty())
            return;
        BufferedImage currentAttractionImage = null;
        for (ScheduleItem scheduleItem : this.scheduleItems) {
            this.x = scheduleItem.getLocation(schedule).getPosition().getX();
            this.y = scheduleItem.getLocation(schedule).getPosition().getY();
            currentAttractionImage = attractionImages.get(scheduleItem.getLocation(schedule));

            if (currentAttractionImage == null)
                return;
            AffineTransform tx = new AffineTransform();
            tx.translate(x, y - scheduleItem.getLocation(schedule).getHeight()-32);
            tx.scale(scale, scale);
            g2d.drawImage(currentAttractionImage, tx, null);
        }

    }


    public void setScheduleItem(ArrayList<ScheduleItem> currentScheduleItems, Schedule schedule) {
        this.scheduleItems = currentScheduleItems;
        this.schedule = schedule;
    }
}
