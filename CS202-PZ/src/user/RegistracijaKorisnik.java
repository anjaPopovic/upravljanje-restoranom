/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package user;

import database.DBUtil;
import database.KorisnikCRUD;
import entities.Korisnik;
import exceptions.InputNotValidException;
import exceptions.PasswordNotValidException;
import exceptions.UsernameNotValidException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import entities.Metode;

/**
 *
 * @author User
 */
public class RegistracijaKorisnik extends Application {

    GridPane grid = new GridPane();

    Label imeLabel = new Label("Ime:");
    TextField imeField = new TextField();

    Label prezimeLabel = new Label("Prezime:");
    TextField prezimeField = new TextField();

    Label telefonLabel = new Label("Broj telefona:");
    TextField telefonField = new TextField();

    Label emailLabel = new Label("Email:");
    TextField emailField = new TextField();

    Label korisnickoImeLabel = new Label("Korisničko ime:");
    TextField korisnickoImeField = new TextField();

    Label lozinkaLabel = new Label("Lozinka:");
    PasswordField lozinkaField = new PasswordField();

    Button registracijaBtn = new Button("Registruj se");
    Button nazadBtn = new Button("Nazad");

    HBox buttonBox = new HBox(10);

    Korisnik korisnik = new Korisnik();

    Label imeValidacijaLabel = new Label("Ime sadrži samo slova!");
    Label prezimeValidacijaLabel = new Label("Prezime sadrži samo slova!");
    Label brojTelefonaValidacijaLabel = new Label("Telefon sadrži do 10 cifara i nema slova!");
    Label emailValidacijaLabel = new Label("Email mora imati ispravnu formataciju!");
    Label lozinkaValidacijaLabel = new Label("Lozinka ima jedno veliko slovo i broj!");

    @Override
    public void start(Stage primaryStage) {

        imeValidacijaLabel.setStyle("-fx-text-fill: red");
        imeValidacijaLabel.setVisible(false);

        prezimeValidacijaLabel.setStyle("-fx-text-fill: red");
        prezimeValidacijaLabel.setVisible(false);

        brojTelefonaValidacijaLabel.setStyle("-fx-text-fill: red");
        brojTelefonaValidacijaLabel.setVisible(false);

        emailValidacijaLabel.setStyle("-fx-text-fill: red");
        emailValidacijaLabel.setVisible(false);

        lozinkaValidacijaLabel.setStyle("-fx-text-fill: red");
        lozinkaValidacijaLabel.setVisible(false);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-font-size: 15px");

        imeField.setPromptText("Unesite svoje ime");
        prezimeField.setPromptText("Unesite svoje prezime");
        telefonField.setPromptText("Unesite svoj broj telefona");
        emailField.setPromptText("Unesite svoj email");
        korisnickoImeField.setPromptText("Unesite korisničko ime");
        lozinkaField.setPromptText("Unesite lozinku");

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new user.KorisnikPanel().start(primaryStage);
        });

        registracijaBtn.setOnAction(e -> {
            try {
                proveraPraznihPolja();
                if (jeValidan()) {
                    DBUtil.openConnection();
                    postaviKorisnickiUnos();

                    KorisnikCRUD.dodajKorisnika(korisnik);

                    Alert a1 = new Alert(Alert.AlertType.INFORMATION, "Korisnik uspešno kreiran!", ButtonType.CLOSE);
                    a1.showAndWait();

                    ocistiPolja();

                    primaryStage.close();
                    new scenes.LoginKorisnik().start(primaryStage);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RegistracijaKorisnik.class.getName()).log(Level.SEVERE, null, ex);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Greška prilikom stvaranja novog korisnika!", ButtonType.OK);
                errorAlert.setHeaderText("Greska");
                errorAlert.showAndWait();

            } catch (InputNotValidException | UsernameNotValidException | PasswordNotValidException ex) {
            } finally {
                try {
                    DBUtil.closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nazadBtn, registracijaBtn);

        grid.add(imeLabel, 0, 0);
        grid.add(imeField, 1, 0);
        grid.add(imeValidacijaLabel, 2, 0);

        grid.add(prezimeLabel, 0, 1);
        grid.add(prezimeField, 1, 1);
        grid.add(prezimeValidacijaLabel, 2, 1);

        grid.add(telefonLabel, 0, 2);
        grid.add(telefonField, 1, 2);
        grid.add(brojTelefonaValidacijaLabel, 2, 2);

        grid.add(emailLabel, 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(emailValidacijaLabel, 2, 3);

        grid.add(korisnickoImeLabel, 0, 4);
        grid.add(korisnickoImeField, 1, 4);

        grid.add(lozinkaLabel, 0, 5);
        grid.add(lozinkaField, 1, 5);
        grid.add(lozinkaValidacijaLabel, 2, 5);

        grid.add(buttonBox, 0, 6, 2, 1);

        Scene scene = new Scene(grid, 700, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Registracija");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void ocistiPolja() {
        imeField.clear();
        prezimeField.clear();
        telefonField.clear();
        emailField.clear();
        korisnickoImeField.clear();
        lozinkaField.clear();
    }

    public void proveraPraznihPolja() throws InputNotValidException {
        if (imeField.getText().isEmpty() || prezimeField.getText().isEmpty()
                || telefonField.getText().isEmpty() || emailField.getText().isEmpty()
                || korisnickoImeField.getText().isEmpty() || lozinkaField.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Morate popuniti sva polja!");
            errorAlert.showAndWait();

            throw new InputNotValidException("Morate popuniti sva polja!");
        }

    }

    public void postaviKorisnickiUnos() throws InputNotValidException, UsernameNotValidException, PasswordNotValidException {
        korisnik.setIme(imeField.getText());
        korisnik.setPrezime(prezimeField.getText());
        korisnik.setBrojTelefona(telefonField.getText());
        korisnik.setMejl(emailField.getText());
        korisnik.setKorisnickoIme(korisnickoImeField.getText());
        korisnik.setLozinka(lozinkaField.getText());
    }

    public boolean jeValidan() throws PasswordNotValidException {
        boolean isValid = true;

        try {
            if (!Metode.samoSlovaPrisutna(imeField.getText())) {
                imeValidacijaLabel.setVisible(true);
                throw new InputNotValidException("Ime sadrži samo slova!");
            } else {
                imeValidacijaLabel.setVisible(false);
            }
        } catch (InputNotValidException e) {
            isValid = false;
        }

        try {
            if (!Metode.samoSlovaPrisutna(prezimeField.getText())) {
                prezimeValidacijaLabel.setVisible(true);
                throw new InputNotValidException("Prezime sadrži samo slova!");
            } else {
                prezimeValidacijaLabel.setVisible(false);
            }
        } catch (InputNotValidException e) {
            isValid = false;
        }

        try {
            if (!Metode.samoBrojeviPrisutni(telefonField.getText()) || telefonField.getText().length() > 10
                    || !telefonField.getText().matches("^06[0-9]{0,8}$")) {
                brojTelefonaValidacijaLabel.setVisible(true);
                throw new InputNotValidException("Broj telefona sadrži ukupno 10 cifara!");
            } else {
                brojTelefonaValidacijaLabel.setVisible(false);
            }
        } catch (InputNotValidException e) {
            isValid = false;
        }

        try {
            if (!Metode.validanMejl(emailField.getText())) {
                emailValidacijaLabel.setVisible(true);
                throw new InputNotValidException("Email mora imati ispravnu formataciju!");
            } else {
                emailValidacijaLabel.setVisible(false);
            }
        } catch (InputNotValidException e) {
            isValid = false;
        }

        try {
            if (!Metode.validnaLozinka(lozinkaField.getText())) {
                lozinkaValidacijaLabel.setVisible(true);
                throw new PasswordNotValidException("Lozinka mora ispravno zadovoljavati kriterijume!");
            } else {
                lozinkaValidacijaLabel.setVisible(false);
            }
        } catch (PasswordNotValidException e) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
