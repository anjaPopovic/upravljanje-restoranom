
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package scenes;

import database.DBUtil;
import database.KorisnikCRUD;
import entities.Korisnik;
import exceptions.InputNotValidException;
import exceptions.PasswordNotValidException;
import exceptions.UsernameNotValidException;
import java.sql.SQLException;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class LoginKorisnik extends Application {

    GridPane grid = new GridPane();

    Label korisnickoImeLabel = new Label("Korisničko ime:");
    TextField korisnickoImeField = new TextField();

    Label lozinkaLabel = new Label("Lozinka:");
    PasswordField lozinkaField = new PasswordField();

    Button nazadBtn = new Button("Nazad");
    Button loginBtn = new Button("Uloguj se");

    HBox buttonBox = new HBox(10);

    StackPane root = new StackPane();

    public static Korisnik korisnik;

    Label validacijaLabel = new Label("Netačni unos!");

    @Override
    public void start(Stage primaryStage) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        grid.setStyle("-fx-font-size: 15px; -fx-background-color: #DFD3BD");

        validacijaLabel.setStyle("-fx-text-fill: red");
        validacijaLabel.setVisible(false);

        korisnickoImeField.setPromptText("Unesite korisničko ime");
        lozinkaField.setPromptText("Unesite lozinku");

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nazadBtn, loginBtn);

        grid.add(korisnickoImeLabel, 0, 0);
        grid.add(korisnickoImeField, 1, 0);

        grid.add(lozinkaLabel, 0, 1);
        grid.add(lozinkaField, 1, 1);

        grid.add(validacijaLabel, 1, 2);

        grid.add(buttonBox, 0, 4, 2, 2);

        root.getChildren().add(grid);

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new user.KorisnikPanel().start(primaryStage);
        });

        loginBtn.setOnAction(e -> {
            try {
                proveraPraznihPolja();
                DBUtil.openConnection();

                String korisnickoIme = korisnickoImeField.getText();
                String lozinka = lozinkaField.getText();

                if (loginUBazu()) {

                    Korisnik pronadjeniKorisnik = KorisnikCRUD.pronadjiKorisnika(korisnickoIme, lozinka);
                    primaryStage.close();
                    new scenes.KorisnikScene().start(primaryStage);
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(LoginKorisnik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (UsernameNotValidException ex) {
                System.out.println("Nevazeca lozinka");
            } catch (PasswordNotValidException ex) {
                java.util.logging.Logger.getLogger(LoginKorisnik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

            } catch (InputNotValidException ex) {
            }

        });

        Scene scene = new Scene(root, 550, 450);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    protected void proveraPraznihPolja() throws InputNotValidException {
        if (korisnickoImeField.getText().isEmpty() || lozinkaField.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Morate popuniti sva polja!");
            errorAlert.setTitle("Greška pri unosu!");
            errorAlert.setHeaderText("Nedostaju podaci!");
            errorAlert.showAndWait();

            throw new InputNotValidException("Morate popuniti sva polja!");
        }

    }

    protected boolean loginUBazu() throws UsernameNotValidException, PasswordNotValidException, SQLException, InputNotValidException {
        proveraPraznihPolja();
        String korisnickoIme = korisnickoImeField.getText();
        String lozinka = lozinkaField.getText();

        Korisnik pronadjeniKorisnik = KorisnikCRUD.pronadjiKorisnika(korisnickoIme, lozinka);

        if (pronadjeniKorisnik != null) {
            if (korisnickoIme.equals(pronadjeniKorisnik.getKorisnickoIme())
                    && lozinka.equals(pronadjeniKorisnik.getLozinka())) {
                //korisnickoImeValidacijaLabel.setVisible(false);
                validacijaLabel.setVisible(false);

                korisnik = KorisnikCRUD.pronadjiKorisnika(korisnickoIme, lozinka);

                Alert a1 = new Alert(Alert.AlertType.INFORMATION, "Ulogovani ste!", ButtonType.CLOSE);
                a1.setTitle("Uspešno logovanje");
                a1.setHeaderText("Dobrodošli!");
                a1.showAndWait();

                return true;
            } else if (!korisnickoIme.equals(pronadjeniKorisnik.getKorisnickoIme()) && !lozinka.equals(pronadjeniKorisnik.getLozinka())) {
                validacijaLabel.setVisible(true);

            }
        } else {
            validacijaLabel.setVisible(true);
        }

        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
