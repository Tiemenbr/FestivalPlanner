package Gui.Pathfinding;

import java.util.ArrayList;
import java.util.Objects;

public class Tile {
    private int x, y;
    private ArrayList<Tile> neighborTiles;
    private Boolean isSet;

    public Tile() {
        this.x = -1;
        this.y = -1;
        this.neighborTiles = new ArrayList<>();
        this.isSet = false;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighborTiles = new ArrayList<>();
        this.isSet = true;
    }

    public Boolean setTile(int x, int y) {
        if (!this.isSet) {
            this.x = x;
            this.y = y;
            this.neighborTiles = new ArrayList<>();
            this.isSet = true;
            return true;
        }
        return false;
    }

    public void setNeighborTiles(ArrayList<Tile> neighborTiles) {
        this.neighborTiles = neighborTiles;
    }

    public Boolean isSet() {
        return this.isSet;
    }
    public ArrayList<Tile> getNeighborTiles() {
        return neighborTiles;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
