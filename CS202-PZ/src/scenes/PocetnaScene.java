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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class PocetnaScene extends Application {

    BorderPane pane = new BorderPane();

    Label label1 = new Label("Dobrodošli!");
    Label label2 = new Label("Izaberite panel:");

    VBox labelBox = new VBox(10);

    Button korisnikBtn = new Button("Korisnik");
    Button adminBtn = new Button("Admin");

    HBox buttonBox = new HBox(15);

    @Override
    public void start(Stage primaryStage) {

        ImageView img = new ImageView("https://logowik.com/content/uploads/images/755_food.jpg");

        img.setFitWidth(130);
        img.setFitHeight(100);

        BorderPane.setAlignment(img, Pos.CENTER);

        label1.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        label2.setStyle("-fx-font-size: 20px");

        korisnikBtn.setStyle("-fx-font-size: 15px");
        adminBtn.setStyle("-fx-font-size: 15px");

        korisnikBtn.setOnAction(e -> {
            primaryStage.close();
            new user.KorisnikPanel().start(primaryStage);
        });

        adminBtn.setOnAction(e -> {
          //primaryStage.close();
            new admin.LoginAdmin().start(primaryStage);
        });

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(korisnikBtn, adminBtn);
        buttonBox.setPadding(new Insets(10));

        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().addAll(label1, label2, buttonBox);

        pane.setTop(img);
        pane.setCenter(labelBox);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Početna stranica");
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
