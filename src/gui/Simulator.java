package gui;

import SimulatorObjects.MapGenerator;
import com.sun.webkit.graphics.WCGraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Simulator{

    private static ResizableCanvas canvas;
    private static double screenWidth = 1425;
    private static double screenHeight = 950;
    private static MapGenerator mapGenerator = new MapGenerator("testDrive.json", screenWidth, screenHeight);

    public static VBox getComponent(){
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        canvas.setWidth(screenWidth);
        canvas.setHeight(screenHeight);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

//        new AnimationTimer() {
//            long last = -1;
//            @Override
//            public void handle(long now) {
//                if(last == -1)
//                    last = now;
//                update((now - last) / 1000000000.0);
//                last = now;
//                draw(g2d);
//            }
//        }.start();

        draw(g2d);

        VBox mainBox = new VBox();
        return mainBox;
    }

    private static void draw(FXGraphics2D g2d){
        mapGenerator.draw(g2d);
    }
}
