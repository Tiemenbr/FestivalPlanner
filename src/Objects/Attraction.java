package Objects;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Attraction {
    private String name;
    private int popularity = 1;
    private int prijs;


    public Attraction(String name, int popularity,int price) {
        this.name = name;
        this.popularity = popularity;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

}
