package Gui;

import Objects.Attraction;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.UUID;

public class AttractionsOverview {
    public static VBox getComponent() {

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));

        Label label = new Label("Overview of Attractions:");
        mainBox.getChildren().add(label);

        // Controleer of er geen attracties zijn, toon dan een melding
        if (Planner.getSCHEDULE().getAttractions().isEmpty()) {
            Label labelEmpty = new Label("No Attractions yet");
            mainBox.getChildren().add(labelEmpty);
        }
        else // Als er attracties zijn, maak dan een HBox voor de kolomme
        {
            HBox listsContainerBox = new HBox();

            ListView<String> collumnNames = new ListView<>();
            collumnNames.getItems().add("Name");

            ListView<String> collumnPopularity = new ListView<>();
            collumnPopularity.getItems().add("Popularity");

            ListView<String> collumnPrice = new ListView<>();
            collumnPrice.getItems().add("Price");

            ListView<String> collumnFileName = new ListView<>();
            collumnFileName.getItems().add("FileName");

            ListView<Button> collumnDelete = new ListView<>();
            Button deleteButtonLabel = new Button("Delete");
            deleteButtonLabel.setPadding(new Insets(0));
            deleteButtonLabel.setStyle("-fx-border: none; -fx-background-color: none;");
            collumnDelete.getItems().add(deleteButtonLabel);

            listsContainerBox.getChildren().addAll(collumnNames, collumnPopularity, collumnPrice, collumnFileName, collumnDelete);

            // Haal alle attracties op uit de Planner en vul de ListView's
            HashMap<UUID, Attraction> attractions = Planner.getSCHEDULE().getAttractions();
            for (UUID key : attractions.keySet()) {
                collumnNames.getItems().add(attractions.get(key).getName());
                collumnPopularity.getItems().add(Integer.toString(attractions.get(key).getPopularity()));
                collumnPrice.getItems().add(Integer.toString(attractions.get(key).getPrice()));
                collumnFileName.getItems().add(attractions.get(key).getImagePath());
                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> {
                    attractions.get(key).delete(Planner.getSCHEDULE());
                });
                deleteButton.setPadding(new Insets(0));
                collumnDelete.getItems().add(deleteButton);
            }

            // Stel de breedte van de ListView's in
            collumnNames.setPrefWidth(100);
            collumnPopularity.setPrefWidth(100);
            collumnPrice.setPrefWidth(100);
            collumnDelete.setPrefWidth(100);

            mainBox.getChildren().add(listsContainerBox);
        }
        // return de VBox met alle inhoud
        return mainBox;
    }
}

