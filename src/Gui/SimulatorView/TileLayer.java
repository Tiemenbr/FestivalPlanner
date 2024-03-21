package Gui.SimulatorView;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileLayer{

    private final String name;
    private String tileType;
    private int width;
    private int height;
    private int[][] map;
    private int tileWidth;
    private int tileHeight;
    private ArrayList<BufferedImage> tiles;

    public TileLayer(String tileType, int layerWidth, int layerHeight, int[][] layerMap, int tileWidth, int tileHeight, ArrayList<BufferedImage> tiles,String name){
        this.tileType = tileType;
        this.width = layerWidth;
        this.height = layerHeight;
        this.map = layerMap;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tiles = tiles;
        this.name = name;

//        // Scale to nearest 0.25, to prevent lines forming
//        double roundTo = 0.25;
//        this.scaleFactor = Math.floor(scaleFactor/roundTo)*roundTo;
    }

//    public BufferedImage scaleImage(BufferedImage image){
//        BufferedImage before = image;
//        // Change width & height of image
//        int w = before.getWidth();
//        int h = before.getHeight();
//        BufferedImage scaled = new BufferedImage((int) (w*scaleFactor), (int) (h*scaleFactor), BufferedImage.TYPE_INT_ARGB);
//
//        // Zoom in on image
//        AffineTransform at = new AffineTransform();
//        at.scale(scaleFactor, scaleFactor);
//        AffineTransformOp scaleOp =
//                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//        scaled = scaleOp.filter(before, scaled);
//
//        return scaled;
//    }

    private BufferedImage cacheImage = null;
    public void draw(Graphics2D g2d){
        if (cacheImage == null && tileType.equalsIgnoreCase("tilelayer") && !this.name.equalsIgnoreCase("colisionlayer")){
            cacheImage = new BufferedImage(width*tileWidth, height*tileHeight, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = cacheImage.createGraphics();

            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    // If the number on the map is <=0: Skip this index
                    if (map[y][x] <= 0)
                        continue;

                    // Draw image of position on map
                    graphics.drawImage(
                            tiles.get(map[y][x] - 1), // OLD: tiles.get(map[y][x]-359)
                            AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight),
                            null);
                }
            }
        }
        g2d.drawImage(cacheImage, null, null);
    }

    public boolean hasCacheImage(){
        if (this.cacheImage != null){
            return true;
        }
        return false;
    }

    public double getCacheImageWidth(){
        return this.cacheImage.getWidth();
    }

    public double getCacheImageHeight(){
        return this.cacheImage.getHeight();
    }

    public String getName() {
        return this.name;
    }
}
