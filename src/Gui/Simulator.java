package Gui;

import Gui.SimulatorView.MapGenerator;
import Gui.SimulatorView.SpriteSheetHelper;
import Gui.SimulatorView.Visitor;
import Objects.Attraction;
import Objects.Location;
import Objects.Schedule;
import Objects.ScheduleItem;
import javafx.animation.AnimationTimer;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import javafx.scene.canvas.Canvas;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulator{
    // TODO: fix zooming bug (not centered in the middle of the screen)
    private static Schedule schedule = Planner.getSCHEDULE();
    private static final MapGenerator mapGenerator = Schedule.getMapGenerator();
    private static Canvas canvas;
    private static VBox vBox;
    private static Camera camera;
    static ArrayList<Visitor> visitors = new ArrayList<>();
    private static SpriteSheetHelper spriteSheetHelper;
    private static int visitorAmount = 100;
    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<Attraction> attractions = new ArrayList<>();

    public static VBox getComponent() {
        vBox = new VBox();
        canvas = new Canvas();
        //canvas = new ResizableCanvas(g -> draw(g), vBox);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d); // Draw your content initially

        camera = new Camera(canvas);
        vBox.getChildren().add(canvas);

        // Handle mouse events for panning
        vBox.setOnMousePressed(event -> camera.handleMousePressed(event));
        vBox.setOnMouseDragged(event -> camera.handleMouseDragged(event));

        // Handle scroll event for zooming
        vBox.setOnScroll(event -> camera.handleScroll(event));
        new AnimationTimer() {
            long last = -1;
            int frameCount = 0;
            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                if (frameCount%25 == 1){ //%100
                    addVisitor();
                }
                last = now;
                draw(g2d);
                frameCount++;
            }
        }.start();
        init();
        return vBox;
    }

    public static void init() {
        // Get Locations & Attractions
        locations.addAll(schedule.getLocations().values());
        attractions.addAll(schedule.getAttractions().values());

        spriteSheetHelper = new SpriteSheetHelper();
//        BufferedImage[] vistorSprites1 = spriteSheetHelper.createSpriteSheet("/walk template 2.png", 4);

        // Starting visitor amount
        while(visitors.size() < 10) {
            addVisitor();
        }

        // Set target of each Visitor
        for (Visitor visitor : visitors){
            setVisitorTargetLocation(visitor);
        }
    }

    private static void addVisitor(){
        if (visitors.size() < visitorAmount){
            // Spawn location coordinates
            Point2D newPosition = new Point2D.Double(384+(Math.random()*192), 864+(Math.random()*64));

            boolean hasCollision = false;
            for (Visitor visitor : visitors) {
                if(visitor.getPosition().distance(newPosition) < visitor.getHitBoxSize())
                    hasCollision = true;
            }
            if(!hasCollision) {
                visitors.add(new Visitor(newPosition, 0));
                setVisitorTargetLocation(visitors.get(visitors.size()-1));
            } else{
                addVisitor();
            }
        }
    }

    public static void setVisitorTargetLocation(Visitor visitor){
        if (schedule.getScheduleItems().isEmpty()){
            // Set random Target Position
            int locationIndex = (int) (Math.round(Math.random()*locations.size()-1));
            while (locationIndex < 0 || locationIndex > 3){
                locationIndex = (int) (Math.round(Math.random()*locations.size()-1));
            }
            visitor.setTargetPosition(new Point2D.Double(locations.get(locationIndex).getPosition().getX() + Math.random()*locations.get(locationIndex).getWidth(), locations.get(locationIndex).getPosition().getY() + Math.random()*locations.get(locationIndex).getHeight()));
            return;
        }


        // TODO: LOPEN OP BASIS VAN POPULARITEIT (met scheduleItems???)
        // Set new Location to go to
        HashMap<Integer, Location> popularityPerLocation = new HashMap<>();
        int highestPopularity = 0;
        Location theLocation = null;
        for (ScheduleItem scheduleItem : schedule.getScheduleItems().values()){
            Location location = scheduleItem.getLocation(schedule);
            int popularity = scheduleItem.getAttraction(schedule).getPopularity();
            popularityPerLocation.put(popularity, location);

            if (popularity > highestPopularity){
                highestPopularity = popularity;
            }
        }
        // Get Location with the most popularity
        theLocation = popularityPerLocation.get(highestPopularity);

        // Set new Target Position
        visitor.setTargetPosition(new Point2D.Double(theLocation.getPosition().getX() + Math.random()*theLocation.getWidth(), theLocation.getPosition().getY() + Math.random()*theLocation.getHeight()));
    }

    private static void draw(FXGraphics2D g2d){
        mapGenerator.draw(g2d);
        for (Visitor visitor : visitors) {
            visitor.draw(g2d);
        }
        for (Location location : locations){
            location.draw(g2d);
        }
    }

    private static void update(double deltaTime){
        // Get scale factors based on screen size
        double cacheImageWidth = mapGenerator.getCacheImageWidth();
        double cacheImageHeight = mapGenerator.getCacheImageHeight();

        // Transform the cacheimage
        canvas.setHeight(cacheImageHeight);
        canvas.setWidth(cacheImageWidth);

        for (int i = 0; i < visitors.size(); i++){
            // Despawn location coordinates
            Point2D exitPointLT = new Point2D.Double(384, 0);
            Point2D exitPointRB = new Point2D.Double(576, 64);

            if (visitors.get(i).getPosition().getX() > exitPointLT.getX() && visitors.get(i).getPosition().getX() < exitPointRB.getX() &&
                    visitors.get(i).getPosition().getY() > exitPointLT.getY() && visitors.get(i).getPosition().getY() < exitPointRB.getY()){
                visitors.remove(visitors.get(i));
            }
        }

        for (Visitor visitor : visitors) {
            visitor.update(visitors, mapGenerator.getCollisionLayer(), deltaTime);
        }
    }
    private static final double DEFAULT_SCALE = 1.0;
    private static final double ZOOM_FACTOR = 0.1;

    public static class Camera {
        private Canvas canvas;
        private double offsetX = 0;
        private double offsetY = 0;
        private double scale = DEFAULT_SCALE;

        public Camera(Canvas canvas) {
            this.canvas = canvas;
        }

        public void handleMousePressed(javafx.scene.input.MouseEvent event) {
            offsetX = event.getX();
            offsetY = event.getY();
        }

        public void handleMouseDragged(javafx.scene.input.MouseEvent event) {
            double deltaX = event.getX() - offsetX;
            double deltaY = event.getY() - offsetY;
            offsetX = event.getX();
            offsetY = event.getY();
            canvas.setTranslateX(canvas.getTranslateX() + deltaX);
            canvas.setTranslateY(canvas.getTranslateY() + deltaY);
        }

        public void handleScroll(ScrollEvent event) {
            double delta = event.getDeltaY();
            if (delta < 0) {
                scale -= ZOOM_FACTOR;
            } else {
                scale += ZOOM_FACTOR;
            }
            if (scale < 0){
                scale = -scale;
            }
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);
            event.consume();
        }
    }
}
