package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Attraction implements CRUD, Serializable {


    private String imagePath;
    private UUID id;
    private String name;
    private int popularity = 1;
    private int price;

    // Constructor voor het maken van een nieuwe attractie
    public Attraction(String name, int popularity, int price, String filename) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.popularity = popularity;
        this.imagePath = filename;


        this.update();
    }

    // Methode om de eigenschappen van de attractie in te stellen
    public void setAll(String name, int popularity, int price, String imagePath) {
        this.name = name;
        this.popularity = popularity;
        this.price = price;
        this.imagePath = imagePath;

        this.update();
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

    public int getPrice() {
        return price;
    }


    public String getImagePath() {
        return imagePath;
    }


    @Override
    public String toString() // Methode om de attractie te beschrijven
    {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", popularity=" + popularity +
                ", price=" + price +
                '}';
    }

    @Override
    public void update()
    {
        IOController.update(this.id, this, IOController.ObjectType.ATTRACTION);
    }

    @Override
    public void delete(Schedule schedule) {
        //todo idk if it's right to pass the schedule... but it needs to access it somewhere I think

        ArrayList<ScheduleItem> toBeDeletedScheduleItems = new ArrayList<>();

        // Loop door de schedule items
        for (UUID key : schedule.getScheduleItems().keySet()) {
//            System.out.println(schedule.getScheduleItem(key));

            if (schedule.getScheduleItem(key).getAttraction(schedule).getId() == this.getId()) {
//                System.out.println(key + " has the attraction");
                //schedule.getScheduleItem(key).delete(schedule);
                toBeDeletedScheduleItems.add(schedule.getScheduleItem(key));
            }
        }

        // Loop door de lijst met schedule items en verwijder ze
        for (ScheduleItem scheduleItem : toBeDeletedScheduleItems) {
            scheduleItem.delete(schedule);
        }
        schedule.deleteAttraction(this.getId());

        // Verwijder de attractie uit het bestand
        IOController.delete(this.id, IOController.ObjectType.ATTRACTION);
    }


}
