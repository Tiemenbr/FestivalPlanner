package Gui;

import Gui.SimulatorView.DrawAttraction;
import Gui.SimulatorView.MapGenerator;
import Gui.SimulatorView.Visitor;
import Objects.Attraction;
import Objects.Location;
import Objects.Schedule;
import Objects.ScheduleItem;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.UUID;

public class Simulator {
    private static final MapGenerator mapGenerator = Schedule.getMapGenerator();
    private static final DrawAttraction drawAttraction = new DrawAttraction();
    private static final double DEFAULT_SCALE = 1.0;
    private static final double ZOOM_FACTOR = 0.1;
    static ArrayList<Visitor> visitors = new ArrayList<>();
    private static Schedule schedule = Planner.getSCHEDULE();
    private static Canvas canvas;
    private static BorderPane mainBox;
    private static Camera camera;
    private static int visitorAmount = 60;
    private static int startVisitorAmount = 5;
    private static double timeModifyer = 3;
    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<Attraction> attractions = new ArrayList<>();
    private static double time;
    private static DayOfWeek currentDay;
    private static ArrayList<ScheduleItem> currentScheduleItems;

    public static BorderPane getComponent() {
        mainBox = new BorderPane();

        VBox vBox = new VBox();
        canvas = new Canvas();
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d);

        camera = new Camera(canvas);
        vBox.getChildren().add(canvas);
        mainBox.setCenter(vBox);

        // Handle mouse events
        vBox.setOnMousePressed(event -> camera.handleMousePressed(event));
        vBox.setOnMouseDragged(event -> camera.handleMouseDragged(event));
        vBox.setOnScroll(event -> camera.handleScroll(event));

        new AnimationTimer() {
            long last = -1;
            int frameCount = 0;
            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                if (frameCount % 60 == 1) {
                    addVisitor();
                }
                last = now;
                draw(g2d);
                frameCount++;
            }
        }.start();
        init();
        return mainBox;
    }

    public static void init() {
        currentDay = DayOfWeek.MONDAY;
        currentScheduleItems = new ArrayList<>();
        setCurrentScheduleItems();
        // Get all locations
        for (Location location : mapGenerator.getLocations()) {
            schedule.addLocation(location);
        }
        // Get Locations & Attractions
        locations.addAll(schedule.getLocations().values());
        attractions.addAll(schedule.getAttractions().values());
        drawAttraction.init(schedule.getScheduleItems(), schedule);

        double cacheImageWidth = mapGenerator.getCacheImageWidth();
        double cacheImageHeight = mapGenerator.getCacheImageHeight();
        canvas.setWidth(cacheImageWidth);
        canvas.setHeight(cacheImageHeight);

        for (int i = 0; i < startVisitorAmount; i++) {
            addVisitor();
        }
    }

    private static void setCurrentScheduleItems() {
        currentScheduleItems.clear();
        for (UUID uuid : schedule.getScheduleItems().keySet()) {
            int startTimeMinutes = schedule.getScheduleItem(uuid).getStartTime().getHour() * 60 + schedule.getScheduleItem(uuid).getStartTime().getMinute();
            int endTimeMinutes = schedule.getScheduleItem(uuid).getEndTime().getHour() * 60 + schedule.getScheduleItem(uuid).getEndTime().getMinute();
            if (schedule.getScheduleItem(uuid).getDay() == currentDay && startTimeMinutes / 5.0 <= time && endTimeMinutes / 5.0 >= time) {
                for (int i = 0; i < schedule.getScheduleItem(uuid).getAttraction(schedule).getPopularity(); i++) {
                    currentScheduleItems.add(schedule.getScheduleItem(uuid));
                }
            }
        }
    }

    private static void addVisitor() {
        if (visitors.size() < visitorAmount) {
            // Spawn location coordinates
            Point2D newPosition = new Point2D.Double(384 + (Math.random() * 192), 864 + (Math.random() * 64));

            boolean hasCollision = false;
            for (Visitor visitor : visitors) {
                if (visitor.getPosition().distance(newPosition) < visitor.getHitBoxSize())
                    hasCollision = true;
            }
            if (!hasCollision) {
                Visitor newVisitor = new Visitor(newPosition, 0, mapGenerator.getDistanceMaps(), mapGenerator.getPathfindingTiles());
                newVisitor.setTargetPosition(currentScheduleItems, schedule);
                visitors.add(newVisitor);
            } else {
                addVisitor();
            }
        }
    }

    private static void draw(FXGraphics2D g2d) {
        mapGenerator.draw(g2d);
        drawAttraction.draw(g2d);
        for (Visitor visitor : visitors) {
            visitor.draw(g2d);
        }
        for (Location location : locations) {
            location.draw(g2d);
        }
    }

    private static void update(double deltaTime) {
        //update time
        time += deltaTime * timeModifyer;

        // Get scale factors based on screen size
        double cacheImageWidth = mapGenerator.getCacheImageWidth();
        double cacheImageHeight = mapGenerator.getCacheImageHeight();

        // Transform the canvas
        canvas.setHeight(cacheImageHeight);
        canvas.setWidth(cacheImageWidth);

        for (int i = 0; i < visitors.size(); i++) {
            // Despawn location coordinates
            Point2D exitPointLT = new Point2D.Double(384, 0);
            Point2D exitPointRB = new Point2D.Double(576, 64);

            if (visitors.get(i).getPosition().getX() > exitPointLT.getX() && visitors.get(i).getPosition().getX() < exitPointRB.getX() &&
                    visitors.get(i).getPosition().getY() > exitPointLT.getY() && visitors.get(i).getPosition().getY() < exitPointRB.getY()) {
                visitors.remove(visitors.get(i));
            }
        }

        for (Visitor visitor : visitors) {
            visitor.update(visitors, mapGenerator.getCollisionLayer(), deltaTime);
            visitor.setTargetPosition(currentScheduleItems, schedule);
        }

        // Scale canvas to Camera
        canvas.setScaleX(camera.scale);
        canvas.setScaleY(camera.scale);

        setCurrentScheduleItems();
        updateTimeLine();
        drawAttraction.setScheduleItem(currentScheduleItems, schedule);

    }

    public static void updateTimeLine() {
        int timeLineScale = (int) mainBox.getWidth();

        //24*60/5  : 24 hours divided into 5 minute segments (just like the planner)

        if (timeLineScale / (24 * 60 / 5.0) * time > timeLineScale) {
            time = 0;
            currentDay = currentDay.plus(1);
        }

        VBox timeLineContainer = new VBox();
        timeLineContainer.setMaxWidth(timeLineScale);
        timeLineContainer.setPadding(new Insets(5, 0, 0, 0));
        timeLineContainer.setStyle("-fx-background-color: lightgray;");

        Label dayLabel = new Label(currentDay.toString());
        timeLineContainer.getChildren().add(dayLabel);
        dayLabel.setPadding(new Insets(0, 0, 0, (timeLineScale + 40) / 2.0 - 30));

        Label timeLabel = new Label(String.format("%02d", ((int) time) * 5 / 60) + ":" + String.format("%02d", ((int) time) * 5 % 60));
        timeLineContainer.getChildren().add(timeLabel);
        timeLabel.setPadding(new Insets(0, 0, 0, (timeLineScale + 40) / 2.0 - 15));

        HBox timeLine = new HBox();
        timeLineContainer.getChildren().add(timeLine);
        timeLine.setPrefHeight(25);
        timeLine.setPadding(new Insets(5, 20, 5, 20));

        HBox lineBackdrop = new HBox();
        timeLine.getChildren().add(lineBackdrop);
        lineBackdrop.setMaxHeight(10);
        lineBackdrop.setPrefWidth(timeLineScale);
        lineBackdrop.setStyle("-fx-background-color: white;");

        HBox line = new HBox();
        lineBackdrop.getChildren().add(line);
        lineBackdrop.setMaxHeight(10);
        line.setPrefWidth((double) timeLineScale / (24 * 60 / 5.0) * time);
        line.setStyle("-fx-background-color: darkslateblue;");

        mainBox.setTop(timeLineContainer);
    }

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
            if (scale < 0) {
                scale = -scale;
            }
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);
            event.consume();
        }
    }
}
