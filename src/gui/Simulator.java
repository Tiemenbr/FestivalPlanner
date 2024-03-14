package gui;

import SimulatorObjects.MapGenerator;
import javafx.scene.layout.VBox;

import java.awt.*;

public class Simulator{
    private static double screenWidth = 1425;
    private static double screenHeight = 950;
    private static MapGenerator mapGenerator = new MapGenerator("testDrive.json", screenWidth, screenHeight);
    public static VBox getComponent(){
        VBox mainBox = new VBox();

        return mainBox;
    }

    public static void draw(Graphics2D g2d){
        mapGenerator.draw(g2d);
    }
}
