/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package webScraping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author User
 */
public class DezertScraping extends Application {

    Button nazadBtn = new Button("Nazad");

    @Override
    public void start(Stage primaryStage) {
        String url = "https://www.restorandurmitor.rs/jelovnik/";

        Map<String, String> menu = new HashMap<>();

        try {
            menu = scrapeMenuItems(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> itemList = FXCollections.observableArrayList();
        for (Map.Entry<String, String> entry : menu.entrySet()) {
            itemList.add(entry.getKey() + " - " + entry.getValue());
        }

        ListView<String> listView = new ListView<>(itemList);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(listView, nazadBtn);
        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.setStyle("-fx-font-size: 15px; -fx-background-color: #DFD3BD");

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new admin.Meni().start(primaryStage);
        });

        Scene scene = new Scene(vBox, 500, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dezerti");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Map<String, String> scrapeMenuItems(String url) throws IOException {
        Map<String, String> menuItems = new HashMap<>();

        Document document = Jsoup.connect(url).get();

        Elements titleElements = document.select(".eluid416ed070 .zn-priceList-itemTitle");
        Elements priceElements = document.select(".eluid416ed070 .zn-priceList-itemPrice");

        String[] naslovi = new String[titleElements.size()];
        String[] cene = new String[priceElements.size()];

        for (int i = 0; i < titleElements.size(); i++) {
            naslovi[i] = titleElements.get(i).text();
            // System.out.println("Naslovi: " + naslovi[i]);
        }

        for (int i = 0; i < priceElements.size(); i++) {
            cene[i] = priceElements.get(i).text();
            //System.out.println("Cene: " + cene[i]);
        }

        for (int j = 0; j < Math.min(naslovi.length, cene.length); j++) {
            menuItems.put(naslovi[j], cene[j]);
        }

        return menuItems;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
