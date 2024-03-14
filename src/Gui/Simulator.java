package Gui;

import Gui.SimulatorView.MapGenerator;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.geom.AffineTransform;

public class Simulator{

    private static ResizableCanvas canvas;
    private static MapGenerator mapGenerator = new MapGenerator("testDrive.json");

    public static VBox getComponent(){
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(g2d);
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update();
                last = now;
            }
        }.start();

        VBox mainBox = new VBox();
        mainBox.getChildren().add(mainPane);
        return mainBox;
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

}
