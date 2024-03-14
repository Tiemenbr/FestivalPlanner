package SimulatorObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileLayer{

    private int width;
    private int height;
    private int[][] map;
    private int tileWidth;
    private int tileHeight;
    private ArrayList<BufferedImage> scaledTiles = new ArrayList<>();
    private double scaleFactor;

    public TileLayer(int layerWidth, int layerHeight, int[][] layerMap, int tileWidth, int tileHeight, ArrayList<BufferedImage> tiles, double scaleFactor){
        this.width = layerWidth;
        this.height = layerHeight;
        this.map = layerMap;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        // Scale to nearest 0.25, to prevent lines forming
        double roundTo = 0.25;
        this.scaleFactor = Math.floor(scaleFactor/roundTo)*roundTo;

        // Scale images to correct size
        for (BufferedImage image : tiles){
            BufferedImage scaledImage = scaleImage(image);
            this.scaledTiles.add(scaledImage);
        }
    }

    public BufferedImage scaleImage(BufferedImage image){
        BufferedImage before = image;

        // Change width & height of image
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage scaled = new BufferedImage((int) (w*scaleFactor), (int) (h*scaleFactor), BufferedImage.TYPE_INT_ARGB);

        // Zoom in on image
        AffineTransform at = new AffineTransform();
        at.scale(scaleFactor, scaleFactor);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaled = scaleOp.filter(before, scaled);

        return scaled;
    }

    public void draw(Graphics2D g2d){
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                // If the number on the map is <=0: Skip this index
                if(map[y][x] <= 0)
                    continue;

                // Draw image of position on map
                g2d.drawImage(
                        scaledTiles.get(map[y][x]-1),
                        //tiles.get(map[y][x]-1), // OLD: tiles.get(map[y][x]-359)
                        AffineTransform.getTranslateInstance((x*tileWidth)*scaleFactor, (y*tileHeight)*scaleFactor),
                        null);
            }
        }
    }
}
