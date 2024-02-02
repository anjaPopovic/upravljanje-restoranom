/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package scenes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class KorisnikScene extends Application {

    VBox root = new VBox(20);
    Button rezervisibtn = new Button("Rezerviši sto");
    Button nazadBtn = new Button("Nazad");

    @Override
    public void start(Stage primaryStage) {

        rezervisibtn.setPadding(new Insets(5));
        nazadBtn.setPadding(new Insets(5));

        rezervisibtn.setFocusTraversable(false);
        nazadBtn.setFocusTraversable(false);

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(rezervisibtn, nazadBtn);

        rezervisibtn.setOnAction(e -> {
            primaryStage.close();
            new scenes.Rezervisi1().start(primaryStage);
        });

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new user.KorisnikPanel().start(primaryStage);
        });

        root.setStyle("-fx-background-color: #6A6F4C; -fx-font-size: 15px");
        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Korisnik Scena");
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
