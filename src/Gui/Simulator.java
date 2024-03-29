package Gui;

import Gui.SimulatorView.MapGenerator;
import Gui.SimulatorView.SpriteSheetHelper;
import Gui.SimulatorView.Visitor;
import com.sun.javafx.tk.FontLoader;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.DayOfWeek;
import java.util.ArrayList;

import javafx.scene.control.Label;

public class Simulator{
    // TODO: fix zooming bug (not centered in the middle of the screen)
    private static Canvas canvas;
    private static BorderPane mainBox;
    private static Camera camera;
    private static final MapGenerator mapGenerator = new MapGenerator("testDrive.json");
    static ArrayList<Visitor> visitors = new ArrayList<>();
    private static SpriteSheetHelper spriteSheetHelper;

    private static double time;
    private static DayOfWeek currentDay;

    public static BorderPane getComponent() {
        mainBox = new BorderPane();
        currentDay = DayOfWeek.MONDAY;

        VBox vBox = new VBox();
        canvas = new Canvas();
//        canvas = new ResizableCanvas(g -> draw(g), stackPane);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d); // Draw your content initially

        camera = new Camera(canvas);
        vBox.getChildren().add(canvas);
        mainBox.setCenter(vBox);


        // Handle mouse events for panning
        vBox.setOnMousePressed(event -> camera.handleMousePressed(event));
        vBox.setOnMouseDragged(event -> camera.handleMouseDragged(event));

        // Handle scroll event for zooming
        vBox.setOnScroll(event -> camera.handleScroll(event));
        canvas.setOnMouseMoved(event -> {
            for (Visitor visitor : visitors) {
                visitor.setTargetPosition(new Point2D.Double(event.getX(), event.getY()));
            }
        });
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();
        init();


        return mainBox;
    }



    public static void init() {

        double cacheImageWidth = mapGenerator.getCacheImageWidth();
        double cacheImageHeight = mapGenerator.getCacheImageHeight();
        canvas.setWidth(cacheImageWidth);
        canvas.setHeight(cacheImageHeight);


        spriteSheetHelper = new SpriteSheetHelper();
//        BufferedImage[] vistorSprites1 = spriteSheetHelper.createSpriteSheet("/walk template 2.png", 4);

        while(visitors.size() < 100) {
            Point2D newPosition = new Point2D.Double(Math.random()*1000, Math.random()*1000);
            boolean hasCollision = false;
            for (Visitor visitor : visitors) {
                if(visitor.getPosition().distance(newPosition) < 64)
                    hasCollision = true;
            }
            if(!hasCollision)
                visitors.add(new Visitor(newPosition, 0));
        }

    }

    private static void draw(FXGraphics2D g2d){
        mapGenerator.draw(g2d);
        for (Visitor visitor : visitors) {
            visitor.draw(g2d);
        }
    }

    private static void update(double deltaTime){
        //update time var
        time += deltaTime*100;

        // Get scale factors based on screen size
        double cacheImageWidth = mapGenerator.getCacheImageWidth();
        double cacheImageHeight = mapGenerator.getCacheImageHeight();

        for (Visitor visitor : visitors) {
            visitor.update(visitors,mapGenerator.getCollisionLayer(),deltaTime);
        }

        // Transform the cacheimage
        canvas.setScaleX(camera.scale);
        canvas.setScaleY(camera.scale);

        updateTimeLine();
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


    public static void updateTimeLine(){
        int timeLineScale = 1000;

        //24*60*60/20  : 24 hours divided into 5 minute segments (just like the planner)

        if(timeLineScale/(24*60*60/20.0)*time > timeLineScale){
            time = 0;
            currentDay = currentDay.plus(1);
        }

        VBox timeLineContainer = new VBox();
        timeLineContainer.setMaxWidth(timeLineScale);
        timeLineContainer.setPadding(new Insets(5,0,0,0));
        timeLineContainer.setStyle("-fx-background-color: lightgray;");

        Label dayLabel = new Label(currentDay.toString());
        timeLineContainer.getChildren().add(dayLabel);
        dayLabel.setPadding(new Insets(0,0,0,(timeLineScale+40)/2.0 -50));

        HBox timeLine = new HBox();
        timeLineContainer.getChildren().add(timeLine);
//        timeLine.setStyle("-fx-background-color: blue;");
        timeLine.setPrefHeight(25);
        timeLine.setPadding(new Insets(5,20,5,20));



        HBox lineBackdrop = new HBox();
        timeLine.getChildren().add(lineBackdrop);
        lineBackdrop.setMaxHeight(10);
        lineBackdrop.setPrefWidth(timeLineScale);
        lineBackdrop.setStyle("-fx-background-color: white;");

        HBox line = new HBox();
        lineBackdrop.getChildren().add(line);
        lineBackdrop.setMaxHeight(10);
        line.setPrefWidth((double)timeLineScale/(24*60*60/20.0)*time);
        line.setStyle("-fx-background-color: darkslateblue;");
//        System.out.println((double)timeLineScale/(24*60*60/20.0)*time);



        mainBox.setTop(timeLineContainer);
    }

}
