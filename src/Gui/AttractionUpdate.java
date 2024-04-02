package Gui;

import Objects.Attraction;
import Objects.Schedule;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.UUID;

public class AttractionUpdate {

    public static VBox getComponent(){
        Schedule schedule = Planner.getSCHEDULE();
        VBox mainCreateAttractionBox = new VBox(20);
        mainCreateAttractionBox.setPadding(new Insets(20));

        Label forumTypeLabel = new Label("Update Attraction data:");
        mainCreateAttractionBox.getChildren().add(forumTypeLabel);

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox);

        //#region Attraction
        Label attractionInputLabel = new Label("Attraction: ");
        labelColumnBox.getChildren().add(attractionInputLabel);

        ComboBox<Attraction> AttractionOptionsComboBox = new ComboBox<>();
        HashMap<UUID, Attraction> attractions = schedule.getAttractions();
        for (UUID key : attractions.keySet()) {
            AttractionOptionsComboBox.getItems().add(attractions.get(key));
        }
        AttractionOptionsComboBox.setConverter(new StringConverter<Attraction>() {
            @Override
            public String toString(Attraction attraction) {
                StringBuilder scheduleData = new StringBuilder();
                scheduleData.append(attraction.getName());
                scheduleData.append(", ");
                scheduleData.append(attraction.getPopularity() + " popularity");
                scheduleData.append(", ");
                scheduleData.append("€"+attraction.getPrice());
                return scheduleData.toString();
            }

            @Override
            public Attraction fromString(String string) {
                return null;
            }
        });

        inputsColumnBox.getChildren().add(AttractionOptionsComboBox);
        //#endregion

        //#region Name TextField
        Label nameInputLabel = new Label("Name: ");
        labelColumnBox.getChildren().add(nameInputLabel);

        javafx.scene.control.TextField nameInput = new javafx.scene.control.TextField();
        inputsColumnBox.getChildren().add(nameInput);
        //#endregion

        //#region Popularity TextField
        Label popularityInputLabel = new Label("Popularity: ");
        labelColumnBox.getChildren().add(popularityInputLabel);

        javafx.scene.control.TextField popularityInput = new javafx.scene.control.TextField();
        inputsColumnBox.getChildren().add(popularityInput);
        //#endregion

        //#region Price TextField
        Label priceInputLabel = new Label("Price: ");
        labelColumnBox.getChildren().add(priceInputLabel);

        javafx.scene.control.TextField priceInput = new javafx.scene.control.TextField();
        inputsColumnBox.getChildren().add(priceInput);
        //#region Filename TextField
        Label fileNameInputLabel = new Label("Filename: ");
        labelColumnBox.getChildren().add(fileNameInputLabel);

        javafx.scene.control.TextField fileNameInput = new javafx.scene.control.TextField();
        inputsColumnBox.getChildren().add(fileNameInput);
        //#endregion

        AttractionOptionsComboBox.setOnAction(e ->{
            //get the scheduleItem that was selected
            Attraction selectedAttraction = AttractionOptionsComboBox.getValue();
            //fill name
            nameInput.setText(selectedAttraction.getName());
            //fill popularity
            popularityInput.setText(Integer.toString(selectedAttraction.getPopularity()));
            //fill price
            priceInput.setText(Integer.toString(selectedAttraction.getPrice()));
        });


        //#region update button
        Button updateScheduleItemButton = new Button("Update Attraction");
        updateScheduleItemButton.setOnAction(event -> {
            Attraction attraction = AttractionOptionsComboBox.getValue();

            System.out.println("updating scheduleItem "+attraction.getId()+":");
            System.out.println("from:");
            System.out.println(attraction);

            String name = nameInput.getText();
            int popularity = Integer.parseInt(popularityInput.getText());
            int price = Integer.parseInt(priceInput.getText());


            attraction.setAll(name, popularity, price);

            System.out.println("To:");
            System.out.println(attraction);

            //updates the value in the Attraction options Combobox
            AttractionOptionsComboBox.getItems().set(AttractionOptionsComboBox.getSelectionModel().getSelectedIndex(),attraction);
        });
        inputsColumnBox.getChildren().add(updateScheduleItemButton);
        //#endregion


        mainCreateAttractionBox.getChildren().add(contentRowBox);

        return mainCreateAttractionBox;
    }



}
