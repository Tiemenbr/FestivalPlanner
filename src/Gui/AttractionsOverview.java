package Gui;

import Objects.Attraction;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class AttractionsOverview {
    public static VBox getComponent(){

        VBox mainBox = new VBox();

        HBox listsContainerBox = new HBox();
        ObservableList<Attraction> list = null;
        TableView<Attraction> tableView = new TableView<Attraction>();

        TableColumn<Attraction, String> column1 =
                new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("Name"));


        TableColumn<Attraction, Integer> column2 =
                new TableColumn<>("Popularity");
        column2.setCellValueFactory(new PropertyValueFactory<>("Popularity"));

        TableColumn<Attraction, Integer> column3 =
                new TableColumn<>("Price");
        column3.setCellValueFactory(new PropertyValueFactory<>("Price"));



        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        for(UUID key : Planner.getSCHEDULE().getAttractions().keySet()) {
            tableView.getItems().add(Planner.getSCHEDULE().getAttraction(key));
        }

        mainBox.getChildren().add(tableView);

//        ListView<String> collumnNames = new ListView<>();
//        collumnNames.getItems().add("Name");
//        ListView<String> collumnPopularity = new ListView<>();
//        collumnPopularity.getItems().add("Popularity");
//        ListView<String> collumnPrice = new ListView<>();
//        collumnPrice.getItems().add("Price");
//
//        listsContainerBox.getChildren().addAll(collumnNames,collumnPopularity,collumnPrice);
//
//        HashMap<UUID, Attraction> attractions = Planner.getSCHEDULE().getAttractions();
//        for (UUID key : attractions.keySet()) {
//            collumnNames.getItems().add(attractions.get(key).getName());
//            collumnPopularity.getItems().add(Integer.toString(attractions.get(key).getPopularity()));
//            collumnPrice.getItems().add(Integer.toString(attractions.get(key).getPrice()));
//        }

//        mainBox.getChildren().add(listsContainerBox);
//        gui.Planner.getSchedule().getAttractions();


        return mainBox;
    }
}

