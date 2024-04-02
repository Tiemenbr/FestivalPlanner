package Gui.SimulatorView;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class TileLayer {

    private final String name;
    private String tileType;
    private int width;
    private int height;
    private int[][] map;
    private int tileWidth;
    private int tileHeight;
    private HashMap<String, ArrayList<BufferedImage>> tileSetImages;
    private BufferedImage cacheImage = null;

    public TileLayer(String tileType, int layerWidth, int layerHeight, int[][] layerMap, int tileWidth, int tileHeight, HashMap<String, ArrayList<BufferedImage>> tileSetImages, String name) {
        this.tileType = tileType;
        this.width = layerWidth;
        this.height = layerHeight;
        this.map = layerMap;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileSetImages = tileSetImages;
        this.name = name;
    }

    public void draw(Graphics2D g2d) {
        if (cacheImage == null && tileType.equalsIgnoreCase("tilelayer") && !this.name.equalsIgnoreCase("collisionlayer")) {
            cacheImage = new BufferedImage(width * tileWidth, height * tileHeight, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = cacheImage.createGraphics();

            // Draw layer
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // If the number on the map is <=0: Skip this index
                    if (map[y][x] <= 0)
                        continue;

                    // Draw image of position on map
                    ArrayList<BufferedImage> tiles = tileSetImages.get(name);
                    graphics.drawImage(
                            tiles.get(map[y][x] - 1),
                            AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight),
                            null);
                }
            }
        }
        g2d.drawImage(cacheImage, null, null);
    }

    public int[][] getMap() {
        return this.map;
    }

    public boolean hasCacheImage(){
        if (this.cacheImage != null){
            return true;
        }
        return false;
    }

    public double getCacheImageWidth() {
        return this.cacheImage.getWidth();
    }

    public double getCacheImageHeight() {
        return this.cacheImage.getHeight();
    }

    public String getName() {
        return this.name;
    }

}
