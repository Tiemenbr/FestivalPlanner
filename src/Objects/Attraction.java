package Objects;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

        ArrayList<ScheduleItem> toBeDeletedScheduleItems = new ArrayList<>();

        for(UUID key : schedule.getScheduleItems().keySet()){
            System.out.println(schedule.getScheduleItem(key));

            if(schedule.getScheduleItem(key).getAttraction(schedule).getId() == this.getId()){
                System.out.println(key+" has the attraction");
                //schedule.getScheduleItem(key).delete(schedule);
                toBeDeletedScheduleItems.add(schedule.getScheduleItem(key));
            }
        }

        for(ScheduleItem scheduleItem : toBeDeletedScheduleItems){
            scheduleItem.delete(schedule);
        }

        schedule.deleteAttraction(this.getId());
        IOController.delete(this.id, IOController.ObjectType.ATTRACTION);
    }



}
