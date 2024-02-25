package gui;

import Objects.Attraction;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class AttractionsOverview {
    public static VBox getComponent(){

        VBox mainBox = new VBox();

        HBox listsContainerBox = new HBox();

        ListView<String> collumnNames = new ListView<>();
        collumnNames.getItems().add("Name");
        ListView<String> collumnPopularity = new ListView<>();
        collumnPopularity.getItems().add("Popularity");
        ListView<String> collumnPrice = new ListView<>();
        collumnPrice.getItems().add("Price");

        listsContainerBox.getChildren().addAll(collumnNames,collumnPopularity,collumnPrice);

        HashMap<Integer, Attraction> attractions = Planner.getSchedule().getAttractions();
        for (int i = 0; i < attractions.size(); i++) {
            collumnNames.getItems().add(attractions.get(i+1).getName());
            collumnPopularity.getItems().add(Integer.toString(attractions.get(i+1).getPopularity()));
            collumnPrice.getItems().add(Integer.toString(attractions.get(i+1).getPrice()));
        }

        mainBox.getChildren().add(listsContainerBox);
        //gui.Planner.getSchedule().getAttractions();


        return mainBox;
    }
}

