package Gui.SimulatorView;

import Gui.Pathfinding.FrontierQueue;
import Gui.Pathfinding.Tile;
import Objects.Location;

import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapGenerator {

    private BufferedImage tilemap;
    //    private int layerWidth;
//    private int layerHeight;
    private int[][] layerMap;
    private ArrayList<TileLayer> layers = new ArrayList<>();
    private int tileHeight;
    private int tileWidth;
    private ArrayList<BufferedImage> tiles = new ArrayList<>();
    private HashMap<String,HashMap<Tile,Integer>> distances = new HashMap<>();
    private HashMap<String, ArrayList<BufferedImage>> tileSetImages = new HashMap<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private boolean createLocationsOnce = true;

    public MapGenerator(String fileName){
        JsonReader reader = Json.createReader(getClass().getClassLoader().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();

        try {
            // Find the tilesets
            JsonArray list = root.getJsonArray("tilesets");
            ArrayList<BufferedImage> tiles = new ArrayList<>();
            for (int o = 0; o < list.size(); o++){
                JsonObject item = list.getJsonObject(o);
                String imagePath = item.getString("image");
                String[] imagePathItems = imagePath.split("/");
                String image = "";
                for (String imagePathItem : imagePathItems){
                    if (imagePathItem.contains("/png")) ;
                    {
                        image = imagePathItem;
                    }
                }
                if (!image.equalsIgnoreCase("")){
                    this.tilemap = ImageIO.read(getClass().getClassLoader().getResourceAsStream(image));
                }

                // Get Tilewidth & Tileheight
                if (root.containsKey("tilewidth")){
                    this.tileWidth = root.getInt("tilewidth");
                }
                if (root.containsKey("tileheight")){
                    this.tileHeight = root.getInt("tileheight");
                }

                // Get tile images
                if (this.tileWidth != 0 && this.tileHeight != 0){
                    for (int y = 0; y < tilemap.getHeight(); y += tileHeight){
                        for (int x = 0; x < tilemap.getWidth(); x += tileWidth){
                            tiles.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
                        }
                    }

                    // Initialize & Get layer values
                    String layerName = "";
                    String tileType = "";
                    int layerWidth = 0;
                    int layerHeight = 0;
                    JsonArray layers = root.getJsonArray("layers");
                    for (int i = 0; i < layers.size(); i++){
                        // Get layer name
                        if (layers.getJsonObject(i).containsKey("name")){
                            layerName = layers.getJsonObject(i).getString("name");
                        }
                        // Get layer type
                        if (layers.getJsonObject(i).containsKey("type")){
                            tileType = layers.getJsonObject(i).getString("type");

                            // Get all objects from objectgroup
                            if (tileType.equalsIgnoreCase("objectgroup")){
                                if (createLocationsOnce){
                                    int objectAmount = layers.getJsonObject(i).getJsonArray("objects").size();
                                    for (int j = 0; j < objectAmount; j++){
                                        String name = layers.getJsonObject(i).getJsonArray("objects").getJsonObject(j).getString("name");
                                        if (name.equalsIgnoreCase("")){
                                            name = layers.getJsonObject(i).getJsonArray("objects").getJsonObject(j).getString("type");
                                        }
                                        int height = layers.getJsonObject(i).getJsonArray("objects").getJsonObject(j).getInt("height");
                                        int width = layers.getJsonObject(i).getJsonArray("objects").getJsonObject(j).getInt("width");
                                        int x = layers.getJsonObject(i).getJsonArray("objects").getJsonObject(j).getInt("x");
                                        int y = layers.getJsonObject(i).getJsonArray("objects").getJsonObject(j).getInt("y");

                                        if (!name.equalsIgnoreCase("entrance") && !name.equalsIgnoreCase("exit")){
                                            locations.add(new Location(height, width, name, new Point2D.Double(x, y)));
                                        }
                                    }
                                    createLocationsOnce = false;
                                }
                            }
                        }
                        // Get layer width
                        if (layers.getJsonObject(i).containsKey("width")){
                            layerWidth = layers.getJsonObject(i).getInt("width");
                        }
                        // Get layer Height
                        if (layers.getJsonObject(i).containsKey("height")){
                            layerHeight = layers.getJsonObject(i).getInt("height");
                        }
                        // Get layer data (array)
                        if (layers.getJsonObject(i).containsKey("data")){
                            JsonArray arrayData = layers.getJsonObject(i).getJsonArray("data");
                            this.layerMap = new int[layerHeight][layerWidth];

                            for (int y = 0; y < layerHeight; y++){

                                int[] oneLayer = new int[layerWidth];
                                for (int x = 0; x < layerWidth; x++){
                                    int oneItem = arrayData.getInt(x + (layerWidth * y));
                                    oneLayer[x] = oneItem;
                                }
                                this.layerMap[y] = oneLayer;
                            }
                        }

                        // Add layer to ArrayList
                        this.tileSetImages.put(layerName, tiles);
                        TileLayer tileLayer = new TileLayer(tileType, layerWidth, layerHeight, this.layerMap, this.tileWidth, this.tileHeight, this.tileSetImages, layerName);
                        this.layers.add(tileLayer);
                    }
                }
            }
            TileLayer bottomLayer = this.getBottomLayer();
            Tile[][] pathfindingTiles = createPathfindingTiles(bottomLayer.getMap()[0].length,bottomLayer.getMap().length);
            JsonArray locations = root.getJsonArray("layers").getJsonObject(3).getJsonArray("objects");
            for (int i = 0; i < locations.size(); i++) {
                FrontierQueue<Tile> frontier = new FrontierQueue<>();
                JsonObject location = locations.getJsonObject(i);
                int x = (location.getInt("x")+(location.getInt("width")/2));
                int y = (location.getInt("y")+(location.getInt("height")/2));
                frontier.offer(pathfindingTiles[x/32][y/32]);
                HashMap<Tile, Integer> distance = new HashMap<>();
                distance.put(frontier.peek(), 0);

                while (!frontier.isEmpty()) {
                    Tile current = frontier.poll();
                    for (Tile neighborTile : current.getNeighborTiles()) {
                        if (distance.get(neighborTile) == null) {
                            frontier.offer(neighborTile);
                            distance.put(neighborTile,1 + distance.get(current));
                        }
                    }
                }
                this.distances.put(locations.getJsonObject(i).getString("name"), distance);
            }
        } catch(IOException e){
            System.out.println("IOException!");
            e.printStackTrace();
        }
    }

    private Tile[][] createPathfindingTiles(int borderX, int borderY) {
        Tile[][] pathfindingTiles = new Tile[borderX][borderY];
        int[][] collisionTiles = this.getCollisionLayer().getMap();
        for (int i = 0; i < borderX; i++) {
            for (int j = 0; j < borderY; j++) {
                pathfindingTiles[i][j] = new Tile();
            }
        }

        for (int i = 0; i < borderX; i++) {
            for (int j = 0; j < borderY; j++) {
                if (!(collisionTiles[i][j]>0))
                    pathfindingTiles[i][j].setTile(i*32,j*32);
            }
        }
        for (int i = 0; i < borderX; i++) {
            for (int j = 0; j < borderY; j++) {
                if (pathfindingTiles[i][j] == null)
                    continue;
                ArrayList<Tile> neighbors = new ArrayList<>();
                if (i+1 < borderX && pathfindingTiles[i+1][j].isSet())
                    neighbors.add(pathfindingTiles[i+1][j]);
                if (j+1 < borderY && pathfindingTiles[i][j+1].isSet())
                    neighbors.add(pathfindingTiles[i][j+1]);
                if (i != 0 && pathfindingTiles[i-1][j].isSet())
                    neighbors.add(pathfindingTiles[i-1][j]);
                if (j != 0 && pathfindingTiles[i][j-1].isSet())
                    neighbors.add(pathfindingTiles[i][j-1]);
                pathfindingTiles[i][j].setNeighborTiles(neighbors);
            }
        }
        return pathfindingTiles;
    }

    public void draw(Graphics2D g2d) {
        // Draw each individual layer
        for (TileLayer layer : layers) {
            layer.draw(g2d);
        }
        // Draw objects
        for (Location location : locations){
            location.draw(g2d);
        }
    }

    public double getCacheImageWidth() {
        double width = 0;
        for (TileLayer layer : layers) {
            if (layer.hasCacheImage()) {
                width = layer.getCacheImageWidth();
            }
        }
        return width;
    }

    public double getCacheImageHeight() {
        double height = 0;
        for (TileLayer layer : layers) {
            if (layer.hasCacheImage()) {
                height = layer.getCacheImageHeight();
            }
        }
        return height;
    }

    private TileLayer getBottomLayer() {
        for (TileLayer layer : layers) {
            if (layer.getName().equalsIgnoreCase("bottomLayer")) {
                return layer;
            }
        }
        return null;
    }

    public HashMap<String,HashMap<Tile,Integer>> getDistanceMaps() {
        return this.distances;
    }

    public TileLayer getCollisionLayer() {
        for (TileLayer layer : layers) {
            if (layer.getName().equalsIgnoreCase("collisionlayer")) {
                return layer;
            }
        }
        return null;
    }

    public ArrayList<Location> getLocations(){
        return locations;
    }

}
