package gui;

import Objects.Attraction;
import Objects.Schedule;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AttractionCreate {

    public static VBox getComponent(){
        Schedule schedule = Planner.getSCHEDULE();
        VBox mainCreateAttractionBox = new VBox(20);
        mainCreateAttractionBox.setPadding(new Insets(20));

        Label forumTypeLabel = new Label("Create new Attraction:");
        mainCreateAttractionBox.getChildren().add(forumTypeLabel);

        HBox contentRowBox = new HBox(10);
        VBox labelColumnBox = new VBox(22);
        VBox inputsColumnBox = new VBox(10);

        contentRowBox.getChildren().addAll(labelColumnBox, inputsColumnBox);

        //#region Name TextField
        Label nameInputLabel = new Label("Name: ");
        labelColumnBox.getChildren().add(nameInputLabel);

        TextField nameInput = new TextField();
        inputsColumnBox.getChildren().add(nameInput);
        //#endregion

        //#region Popularity TextField
        Label popularityInputLabel = new Label("Popularity: ");
        labelColumnBox.getChildren().add(popularityInputLabel);

        TextField popularityInput = new TextField();
        inputsColumnBox.getChildren().add(popularityInput);
        //#endregion

        //#region Price TextField
        Label priceInputLabel = new Label("Price: ");
        labelColumnBox.getChildren().add(priceInputLabel);

        TextField priceInput = new TextField();
        inputsColumnBox.getChildren().add(priceInput);
        //#endregion


        //#region Submit Button
        Button createAttractionButton = new Button("Create Attraction");
        createAttractionButton.setOnAction(e ->{

            String name = nameInput.getText();
            int popularity = Integer.parseInt(popularityInput.getText());
            int price = Integer.parseInt(popularityInput.getText());

            Attraction newItem = new Attraction(name,popularity,price);
            schedule.addAttraction(newItem);

            System.out.println("created Attraction: " + newItem);
        });

        inputsColumnBox.getChildren().add(createAttractionButton);
        //#endregion

        mainCreateAttractionBox.getChildren().add(contentRowBox);

        return mainCreateAttractionBox;
    }

}
