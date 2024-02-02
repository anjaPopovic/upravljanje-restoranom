/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package user;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class KorisnikPanel extends Application {
    Label natpisLabel = new Label("Da li ste registrovani?");
    Button daBtn = new Button("Da");
    Button neBtn = new Button("Ne");
    Button nazadBtn = new Button("Nazad");
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nazadBtn, daBtn, neBtn);
        
        
        natpisLabel.setStyle("-fx-font-size: 20px");
        buttonBox.setStyle("-fx-font-size: 15px");
        
        daBtn.setOnAction(e -> {
              primaryStage.close();
              new scenes.LoginKorisnik().start(primaryStage);
        });
        
        neBtn.setOnAction(e -> {
            primaryStage.close();
            new user.RegistracijaKorisnik().start(primaryStage);
        });
        
        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new scenes.PocetnaScene().start(primaryStage);
        });
        
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #CBB89D");
        root.getChildren().addAll(natpisLabel, buttonBox);
        
        Scene scene = new Scene(root, 500, 400);
        
        primaryStage.setTitle("Korisnik Panel");
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
