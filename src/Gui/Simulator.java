package Gui;

import Gui.SimulatorView.MapGenerator;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class Simulator{

    private static Camera camera;
    private static Canvas canvas;
    private static MapGenerator mapGenerator = new MapGenerator("testDrive.json");

    public static StackPane getComponent() {
//        BorderPane mainPane = new BorderPane();
//        canvas = new ResizableCanvas(g -> draw(g), mainPane);
//        mainPane.setCenter(canvas);
//        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
//        draw(g2d);
//        new AnimationTimer() {
//            long last = -1;
//            @Override
//            public void handle(long now) {
//                if (last == -1)
//                    last = now;
//                update();
//                last = now;
//            }
//        }.start();
//        return mainPane;
        Canvas canvas = new Canvas(800, 600);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d); // Draw your content initially

        camera = new Camera(canvas);

        StackPane stackPane = new StackPane(canvas);

        // Handle mouse events for panning
        stackPane.setOnMousePressed(event -> camera.handleMousePressed(event));
        stackPane.setOnMouseDragged(event -> camera.handleMouseDragged(event));

        // Handle scroll event for zooming
        stackPane.setOnScroll(event -> camera.handleScroll(event));

        return stackPane;
    }
    private static void draw(FXGraphics2D g2d){
        mapGenerator.draw(g2d);
    }

    private static void update(){
        AffineTransform tx = new AffineTransform();
        //tx.translate(canvas.getWidth(), canvas.getHeight()/2);
        canvas.setTranslateX(tx.getTranslateX());
        canvas.setTranslateY(tx.getTranslateY());
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
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);
            event.consume();
        }
    }
}
