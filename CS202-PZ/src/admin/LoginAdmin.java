/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package admin;

import database.AdminCRUD;
import database.DBUtil;
import database.KorisnikCRUD;
import entities.Admin;
import entities.Korisnik;
import exceptions.InputNotValidException;
import exceptions.PasswordNotValidException;
import exceptions.UsernameNotValidException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import scenes.AdminScene;
import scenes.PocetnaScene;
import static scenes.LoginKorisnik.korisnik;

/**
 *
 * @author User
 */
public class LoginAdmin extends Application {

    GridPane grid = new GridPane();

    Label adminImeLabel = new Label("Admin ime:");
    TextField adminImeField = new TextField();

    Label lozinkaLabel = new Label("Lozinka:");
    PasswordField lozinkaField = new PasswordField();

    Button nazadBtn = new Button("Nazad");
    Button loginBtn = new Button("Uloguj se");

    HBox buttonBox = new HBox(10);

    StackPane root = new StackPane();

    public static Admin admin;

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

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new scenes.PocetnaScene().start(primaryStage);
        });

        //loginBtn.setDefaultButton(true);
        loginBtn.setOnAction(e -> {
            try {
                if (validacijaAdmina()) {
                    primaryStage.close();
                    new scenes.AdminScene().start(primaryStage);
                }
            } catch (UsernameNotValidException | PasswordNotValidException | SQLException ex) {
                Logger.getLogger(LoginAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InputNotValidException ex) {
                Logger.getLogger(LoginAdmin.class.getName()).log(Level.SEVERE, "Polja ne mogu biti prazna");
            }

        });

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nazadBtn, loginBtn);

        grid.add(adminImeLabel, 0, 0);
        grid.add(adminImeField, 1, 0);

        grid.add(lozinkaLabel, 0, 1);
        grid.add(lozinkaField, 1, 1);

        grid.add(validacijaLabel, 1, 2);

        grid.add(buttonBox, 0, 4, 2, 2);

        root.getChildren().add(grid);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
    }

    protected boolean validacijaAdmina() throws UsernameNotValidException, PasswordNotValidException, InputNotValidException, SQLException {
        proveraPraznihPolja();
        String adminIme = adminImeField.getText();
        String lozinka = lozinkaField.getText();

        DBUtil.openConnection();
        if (adminIme.equals("admin") && lozinka.equals("Admin123")) {
            validacijaLabel.setVisible(false);

            admin = AdminCRUD.loginAdmin(adminIme, lozinka);

            Alert a1 = new Alert(Alert.AlertType.INFORMATION, "Ulogovani ste kao admin!", ButtonType.CLOSE);
            a1.setTitle("Uspešno logovanje");
            a1.setHeaderText("Dobrodošli, admine!");
            a1.showAndWait();

            return true;
        } else {
            validacijaLabel.setVisible(true);
        }

        return false;
    }

    protected void proveraPraznihPolja() throws InputNotValidException {
        if (adminImeField.getText().isEmpty() || lozinkaField.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Morate popuniti sva polja!");
            errorAlert.setTitle("Greška pri unosu!");
            errorAlert.setHeaderText("Nedostaju podaci!");
            errorAlert.showAndWait();

            throw new InputNotValidException("Morate popuniti sva polja!");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
