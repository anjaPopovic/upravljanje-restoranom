/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package admin;

import database.DBUtil;
import database.KorisnikCRUD;
import database.RezervacijaCRUD;
import entities.Korisnik;
import exceptions.InputNotValidException;
import exceptions.PasswordNotValidException;
import exceptions.UsernameNotValidException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class PregledKorisnika extends Application {

    GridPane pane = new GridPane();

    VBox buttonBox = new VBox(10);
    Button azurirajKorisnika = new Button("Ažuriraj korisnika");
    Button obrisiKorisnika = new Button("Obriši korisnika");
    Button nazadBtn = new Button("Nazad");

    @Override
    public void start(Stage primaryStage) {

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new scenes.AdminScene().start(primaryStage);

        });

        pane.setStyle("-fx-background-color: #DFD3BD; -fx-font-size: 15px");

        obrisiKorisnika.setOnAction(e -> {
            Label label = new Label("Unesite ID korisnika: ");
            TextField idField = new TextField();

            Button obrisiBtn = new Button("Obriši korisnika");
            Button nazad1Button = new Button("Nazad");

            nazad1Button.setOnAction(el -> {
                primaryStage.close();
                new admin.PregledKorisnika().start(primaryStage);
            });

            GridPane root = new GridPane();
            root.setAlignment(Pos.CENTER);
            root.setHgap(10);
            root.setVgap(20);
            root.setStyle("-fx-font-size: 15px");

            obrisiBtn.setOnAction(event -> {
                try {
                    int idKorisnika = Integer.parseInt(idField.getText());

                    DBUtil.openConnection();
                    KorisnikCRUD.obrisiKorisnika(idKorisnika);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Korisnik uspešno obrisan!");
                    alert.setTitle("Brisanje");
                    alert.showAndWait();

                    idField.clear();

                    DBUtil.closeConnection();
                } catch (SQLException ex) {
                    //Logger.getLogger(PregledKorisnika.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Izabrani korisnik ima rezervaciju!");
                    alert.showAndWait();
                }

            });

            root.add(label, 0, 0);
            root.add(idField, 1, 0);
            root.add(obrisiBtn, 1, 1);
            root.add(nazad1Button, 0, 1);

            Scene deleteScene = new Scene(root, 500, 400);
            primaryStage.setScene(deleteScene);
        });

        azurirajKorisnika.setOnAction(e -> {
            Label idLabel = new Label("Unesite ID korisnika: ");
            TextField idField = new TextField();

            Label imeLabel = new Label("Novo ime: ");
            TextField imeField = new TextField();

            Label prezimeLabel = new Label("Novo prezime: ");
            TextField prezimeField = new TextField();

            Label telefonLabel = new Label("Novi broj telefona: ");
            TextField telefonField = new TextField();

            Label mejlLabel = new Label("Novi mejl: ");
            TextField mejlField = new TextField();

            Label korisnickoImeLabel = new Label("Novo korisničko ime: ");
            TextField korisnickoImeField = new TextField();

            Label lozinkaLabel = new Label("Nova lozinka: ");
            PasswordField lozinkaField = new PasswordField();

            Button azurirajBtn = new Button("Ažuriraj korisnika");
            Button nazBtn = new Button("Nazad");

            nazBtn.setOnAction(element -> {
                primaryStage.close();
                new admin.PregledKorisnika().start(primaryStage);
            });

            GridPane root = new GridPane();
            root.setAlignment(Pos.CENTER);
            root.setHgap(10);
            root.setVgap(20);
            root.setStyle("-fx-font-size: 15px");

            azurirajBtn.setOnAction(event -> {
                try {
                    int idKorisnika = Integer.parseInt(idField.getText());

                    Korisnik updatedKorisnik = new Korisnik();
                    updatedKorisnik.setKorisnikId(idKorisnika);
                    updatedKorisnik.setIme(imeField.getText());
                    updatedKorisnik.setPrezime(prezimeField.getText());
                    updatedKorisnik.setBrojTelefona(telefonField.getText());
                    updatedKorisnik.setMejl(mejlField.getText());
                    updatedKorisnik.setKorisnickoIme(korisnickoImeField.getText());
                    updatedKorisnik.setLozinka(lozinkaField.getText());

                    DBUtil.openConnection();
                    KorisnikCRUD.azurirajKorisnika(updatedKorisnik);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Korisnik uspešno ažuriran!");
                    alert.setTitle("Ažuriranje");
                    alert.showAndWait();

                    DBUtil.closeConnection();

                } catch (SQLException ex) {
                    Logger.getLogger(PregledKorisnika.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Netačan unos ili greška prilikom ažuriranja korisnika!");
                    alert.showAndWait();
                } catch (InputNotValidException | UsernameNotValidException | PasswordNotValidException ex) {
                    Logger.getLogger(PregledKorisnika.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            root.add(idLabel, 0, 0);
            root.add(idField, 1, 0);
            root.add(imeLabel, 0, 1);
            root.add(imeField, 1, 1);
            root.add(prezimeLabel, 0, 2);
            root.add(prezimeField, 1, 2);
            root.add(telefonLabel, 0, 3);
            root.add(telefonField, 1, 3);
            root.add(mejlLabel, 0, 4);
            root.add(mejlField, 1, 4);
            root.add(korisnickoImeLabel, 0, 5);
            root.add(korisnickoImeField, 1, 5);
            root.add(lozinkaLabel, 0, 6);
            root.add(lozinkaField, 1, 6);
            root.add(azurirajBtn, 1, 7);
            root.add(nazBtn, 0, 7);

            Scene updateScene = new Scene(root, 500, 500);
            primaryStage.setScene(updateScene);
        });

        buttonBox.getChildren().addAll(azurirajKorisnika, obrisiKorisnika, nazadBtn);
        pane.getChildren().add(buttonBox);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.setStyle("-fx-font-size: 15px");
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane, 500, 400);

        primaryStage.setTitle("Pregled korisnika");
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
