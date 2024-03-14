package gui;

import Objects.Attraction;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Attr;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class AttractionsOverview {
    public static VBox getComponent(){

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));

        Label label = new Label("Overview of Attractions:");
        mainBox.getChildren().add(label);


        if(Planner.getSCHEDULE().getAttractions().isEmpty()){
            Label labelEmpty = new Label("No Attractions yet");
            mainBox.getChildren().add(labelEmpty);
        }else {
            HBox listsContainerBox = new HBox();

            ListView<String> collumnNames = new ListView<>();
            collumnNames.getItems().add("Name");

            ListView<String> collumnPopularity = new ListView<>();
            collumnPopularity.getItems().add("Popularity");

            ListView<String> collumnPrice = new ListView<>();
            collumnPrice.getItems().add("Price");

            ListView<Button> collumnDelete = new ListView<>();
            Button deleteButtonLabel = new Button("Delete");
            deleteButtonLabel.setPadding(new Insets(0));
            deleteButtonLabel.setStyle("-fx-border: none; -fx-background-color: none;");
            collumnDelete.getItems().add(deleteButtonLabel);

            listsContainerBox.getChildren().addAll(collumnNames, collumnPopularity, collumnPrice, collumnDelete);

            HashMap<UUID, Attraction> attractions = Planner.getSCHEDULE().getAttractions();
            for (UUID key : attractions.keySet()) {
                collumnNames.getItems().add(attractions.get(key).getName());
                collumnPopularity.getItems().add(Integer.toString(attractions.get(key).getPopularity()));
                collumnPrice.getItems().add(Integer.toString(attractions.get(key).getPrice()));
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> {
                    attractions.get(key).delete(Planner.getSCHEDULE());
                });
                deleteButton.setPadding(new Insets(0));
                collumnDelete.getItems().add(deleteButton);
            }


            collumnNames.setPrefWidth(100);
            collumnPopularity.setPrefWidth(100);
            collumnPrice.setPrefWidth(100);
            collumnDelete.setPrefWidth(100);

            mainBox.getChildren().add(listsContainerBox);
        }

        return mainBox;
    }
}

