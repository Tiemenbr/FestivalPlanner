package Objects;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Attraction implements CRUD, Serializable {
    private UUID id;
    private String name;
    private int popularity = 1;
    private int price;


    public Attraction(String name,int popularity, int price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.popularity = popularity;

        this.update();
    }

    public void setAll(String name, int popularity, int price) {
        this.name = name;
        this.popularity = popularity;
        this.price = price;
    }

    public UUID getId() {
        return this.id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", popularity=" + popularity +
                ", price=" + price +
                '}';
    }

    @Override
    public void update() {
        IOController.update(this.id, this, IOController.ObjectType.ATTRACTION);
    }

    @Override
    public void delete(Schedule schedule) {
        //todo idk if it's right to pass the schedule... but it needs to access it somewhere I think
        schedule.deleteAttraction(this.getId());
        IOController.delete(this.id, IOController.ObjectType.ATTRACTION);
    }



}
