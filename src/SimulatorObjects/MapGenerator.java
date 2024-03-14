package SimulatorObjects;

import javax.imageio.ImageIO;
import javax.json.*;
import javax.json.stream.JsonParser;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;

public class MapGenerator{

    private BufferedImage tilemap;
    private int layerWidth;
    private int layerHeight;
    private int[][] layerMap;
    private ArrayList<TileLayer> layers = new ArrayList<>();
    private int tileHeight;
    private int tileWidth;
    private ArrayList<BufferedImage> tiles = new ArrayList<>();

    public MapGenerator(String fileName, double screenWidth, double screenHeight) {
        JsonReader reader = null;
        //File file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getFile());
        reader = Json.createReader(getClass().getClassLoader().getResourceAsStream(fileName));



//        JsonObject object = (JsonObject) getClass().getResourceAsStream(fileName);
//        reader = Json.createReader((java.io.InputStream) object);

        JsonObject root = reader.readObject();

        try{
            // Find the tileset
            JsonArray list = root.getJsonArray("tilesets");
            JsonObject item = list.getJsonObject(0);
            String imagePath = item.getString("image");
            String[] imagePathItems = imagePath.split("/");
            String image = "";
            for (String imagePathItem : imagePathItems){
                //System.out.println(imagePathItem);
                if (imagePathItem.contains("/png"));{
                    image = imagePathItem;
                }
            }
            if (!image.equalsIgnoreCase("")){
                this.tilemap = ImageIO.read(getClass().getClassLoader().getResourceAsStream(image));
            }

            // OLD
            //JsonObject item = list.getJsonObject(1);
            //String source = item.getString("source");
            //System.out.println(source);
            //this.tilemap = ImageIO.read(getClass().getResourceAsStream(source));

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

                // Layers
                JsonArray layers = root.getJsonArray("layers");
                for (int i = 0; i < layers.size(); i++){
                    // Get layer width
                    if (layers.getJsonObject(i).containsKey("width")){
                        this.layerWidth = layers.getJsonObject(i).getInt("width");
                    }
                    // Get layer Height
                    if (layers.getJsonObject(i).containsKey("height")){
                        this.layerHeight = layers.getJsonObject(i).getInt("height");
                    }
                    // Get layer data (array)
                    if (layers.getJsonObject(i).containsKey("data")){
                        JsonArray arrayData = layers.getJsonObject(i).getJsonArray("data");
                        this.layerMap = new int[this.layerHeight][this.layerWidth];

                        for (int y = 0; y < this.layerHeight; y++){

                            int[] oneLayer = new int[this.layerWidth];
                            for (int x = 0; x < this.layerWidth; x++){
                                int oneItem = arrayData.getInt(x+(this.layerWidth*y));
                                oneLayer[x] = oneItem;
                            }
                            this.layerMap[y] = oneLayer;
                        }
                    }

                    // Get scaleFactor
                    double scaleFactor = screenWidth/screenHeight;

                    // Add layer to ArrayList
                    TileLayer tileLayer = new TileLayer(this.layerWidth, this.layerHeight, this.layerMap, this.tileWidth, this.tileHeight, this.tiles, scaleFactor);
                    this.layers.add(tileLayer);
                }
            }
        } catch (IOException e){
            System.out.println("IOException!");
           e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        // Draw each individual layer
        for (TileLayer layer : layers){
            layer.draw(g2d);
        }
    }
}
