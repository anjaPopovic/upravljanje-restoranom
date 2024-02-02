/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package scenes;

import database.DBUtil;
import database.RezervacijaCRUD;
import database.StoloviCRUD;
import static entities.Metode.validanBrojMesta;
import static entities.Metode.validniDatum;
import static entities.Metode.validnoVreme;
import entities.Rezervacija;
import entities.Sto;
import exceptions.ReservationNotValidException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static scenes.LoginKorisnik.korisnik;

/**
 *
 * @author User
 */
public class Rezervisi1 extends Application {

    GridPane pane = new GridPane();

    Label datumLabel = new Label("Datum: ");
    TextField datumField = new TextField();

    Label vremeLabel = new Label("Vreme: ");
    TextField vremeField = new TextField();

    Label mestaLabel = new Label("Unesite broj mesta: ");
    TextField mestaField = new TextField();

    Button nastaviBtn = new Button("Nastavi");
    Button nazadBtn = new Button("Nazad");

    @Override
    public void start(Stage primaryStage) {
        pane.add(datumLabel, 0, 0);
        pane.add(datumField, 1, 0);
        pane.add(vremeLabel, 0, 1);
        pane.add(vremeField, 1, 1);
        pane.add(mestaLabel, 0, 2);
        pane.add(mestaField, 1, 2);
        pane.add(nastaviBtn, 1, 3);
        pane.add(nazadBtn, 0, 3);

        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-font-size: 15px");

        nazadBtn.setOnAction(e -> {
            primaryStage.close();
            new scenes.KorisnikScene().start(primaryStage);
        });

        //nastaviBtn.setOnAction(e -> prikaziDostupneStolove());
        nastaviBtn.setOnAction(e -> {
            if (validacijaUnosa()) {
                prikaziDostupneStolove();
                primaryStage.close();
            }
        });

        Scene scene = new Scene(pane, 500, 400);

        primaryStage.setTitle("Rezerviši");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void prikaziDostupneStolove() {
        String unetiDatum = datumField.getText();
        String unetoVreme = vremeField.getText();
        String unetiBrojMesta = mestaField.getText();

        try {

            LocalDate datum = LocalDate.parse(unetiDatum, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime vreme = LocalTime.parse(unetoVreme, DateTimeFormatter.ofPattern("HH:mm"));

            DBUtil.openConnection();

            int brojMesta = Integer.parseInt(unetiBrojMesta);

            List<Sto> dostupniStolovi = StoloviCRUD.filtrirajStolove(datum, vreme, brojMesta);

            Stage stageSto = new Stage();
            VBox stoloviBox = new VBox(10);
            stoloviBox.setAlignment(Pos.CENTER);
            stoloviBox.setStyle("-fx-font-size: 15px");
            stoloviBox.setSpacing(15);

//            for (Sto sto : dostupniStolovi) {
//                Button stoButton = new Button("Sto " + sto.getIdStola());
//                stoButton.setOnAction(e -> {
//                    try {
//                        int rezervacijaId = rezervisiSto(sto.getIdStola());
//                        if (rezervacijaId != -1) {
//
//                            //System.out.println("Uspesna rezervacija!");
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Uspesna rezervacija", ButtonType.YES);
//                            alert.showAndWait();
//                            stage.close();
//
//                        }
//                    } catch (SQLException ex) {
//                        Logger.getLogger(Rezervisi.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (ReservationNotValidException ex) {
//                        System.out.println("Nevalidna rezervacija!");
//                    } 
//                });
//                stoloviBox.getChildren().add(stoButton);
//            }
//
//            Scene scene = new Scene(stoloviBox, 500, 400);
//            stage.setScene(scene);
//            stage.show();
            for (Sto sto : dostupniStolovi) {
                if (!sto.isRezervisan()) {
                    Button stoBtn = new Button("Sto " + sto.getIdStola());
                    stoBtn.setOnAction(el -> {
                        try {
                            int rezervacijaID = rezervisiSto(sto.getIdStola());
                            if (rezervacijaID != -1) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Uspešna rezervacija!", ButtonType.YES);
                                alert.showAndWait();
                                stageSto.close();
                            }
                        } catch (SQLException | ReservationNotValidException ex) {
                            Logger.getLogger(Rezervisi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    stoloviBox.getChildren().add(stoBtn);
                } else {
                    System.out.println("Sto " + sto.getIdStola() + " je vec rezervisan.");
                }
            }

            if (stoloviBox.getChildren().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nema dostupnih stolova za rezervaciju.", ButtonType.OK);
                alert.showAndWait();
                stageSto.setTitle("Raspolozivi stolovi");
                stageSto.close();
            } else {

                Scene scene = new Scene(stoloviBox, 500, 400);
                stageSto.setScene(scene);
                stageSto.show();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unesite validne vrednosti za datum, vreme i broj mesta.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private int rezervisiSto(int stoId) throws SQLException, ReservationNotValidException {
        int rezervacijaId = -1;
        DBUtil.openConnection();
        Sto rezervisaniSto = StoloviCRUD.pronadjiSto(stoId);
        if (rezervisaniSto != null && !rezervisaniSto.isRezervisan()) {

            rezervisaniSto.setRezervisan(true);
            StoloviCRUD.azurirajSto(rezervisaniSto);

            Rezervacija rezervacija = new Rezervacija();
            rezervacija.setKorisnik(korisnik);
            rezervacija.setSto(rezervisaniSto);
            rezervacija.setDatum(LocalDate.parse(datumField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            rezervacija.setVreme(LocalTime.parse(vremeField.getText(), DateTimeFormatter.ofPattern("HH:mm")));

            rezervacijaId = RezervacijaCRUD.dohvatiPoslednjiIdRezervacije();

            RezervacijaCRUD.dodajRezervaciju(rezervacija);

//            System.out.println("korisnik" + korisnik);
//            System.out.println("sto" + rezervisaniSto);
        }

        return rezervacijaId;
    }

    public static void prikaziDetalje(Rezervacija rezervacija) {
        Stage stage = new Stage();
        stage.setTitle("Detalji rezervacije");

        VBox detaljiBox = new VBox(10);
        detaljiBox.setAlignment(Pos.CENTER);
        detaljiBox.setStyle("-fx-font-size: 15px");
        detaljiBox.setSpacing(15);

        Label idLabel = new Label("ID rezervacije: " + rezervacija.getRezervacijaId());
        Label korisnikLabel = new Label("Korisnik: " + rezervacija.getKorisnik().getIme() + " " + rezervacija.getKorisnik().getPrezime());
        Label brojMestaLabel = new Label("Broj mesta: " + rezervacija.getSto().getBrojMesta());
        Label vremeLabel = new Label("Vreme: " + rezervacija.getVreme());
        Label datumLabel = new Label("Datum: " + rezervacija.getDatum());

        detaljiBox.getChildren().addAll(idLabel, korisnikLabel, brojMestaLabel, vremeLabel, datumLabel);

        Scene scene = new Scene(detaljiBox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private boolean validacijaUnosa() {
        String unetiDatum = datumField.getText();
        String unetoVreme = vremeField.getText();
        String unetiBrojMesta = mestaField.getText();

        if (!validniDatum(unetiDatum)) {
            prikaziGresku("Unesite validan datum u formatu yyyy-MM-dd.");
            return false;
        }

        if (!validnoVreme(unetoVreme)) {
            prikaziGresku("Unesite validno vreme u formatu HH:mm.");
            return false;
        }

        if (!validanBrojMesta(unetiBrojMesta)) {
            prikaziGresku("Unesite validan broj mesta (1-9).");
            return false;
        }

        return true;
    }

    private void prikaziGresku(String poruka) {
        Alert alert = new Alert(Alert.AlertType.ERROR, poruka, ButtonType.OK);
        alert.setTitle("Greška!");
        alert.showAndWait();

        if (!validniDatum(datumField.getText())) {
            datumField.requestFocus();
        } else if (!validnoVreme(vremeField.getText())) {
            vremeField.requestFocus();
        } else if (!validanBrojMesta(mestaField.getText())) {
            mestaField.requestFocus();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
