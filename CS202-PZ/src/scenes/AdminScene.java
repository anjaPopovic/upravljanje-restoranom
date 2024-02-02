/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package scenes;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class AdminScene extends Application {

    GridPane pane = new GridPane();
    VBox buttonBox = new VBox(30);
    Button meniBtn = new Button("Meni");
    Button korisniciBtn = new Button("Korisnici");
    Button nazadBtn = new Button("Nazad");

    @Override
    public void start(Stage primaryStage) {

        meniBtn.setOnAction(e -> {
            primaryStage.close();
            new admin.Meni().start(primaryStage);
        });

        korisniciBtn.setOnAction(e -> {
            primaryStage.close();
            new admin.PregledKorisnika().start(primaryStage);
        });

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new scenes.PocetnaScene().start(primaryStage);
        });

        buttonBox.getChildren().addAll(meniBtn, korisniciBtn, nazadBtn);
        buttonBox.setAlignment(Pos.CENTER);

        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(buttonBox);
        pane.setStyle("-fx-font-size: 15px; -fx-background-color: #6A6F4C");

        Scene scene = new Scene(pane, 500, 400);

        primaryStage.setTitle("Admin Panel");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
