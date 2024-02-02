/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import static database.StoloviCRUD.azurirajSto;
import java.sql.Statement;
import entities.Rezervacija;
import entities.Sto;
import exceptions.ReservationNotValidException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class RezervacijaCRUD {

    public static Rezervacija dodajRezervaciju(Rezervacija rezervacija) throws SQLException, ReservationNotValidException {

        if (rezervacija == null || rezervacija.getKorisnik() == null || rezervacija.getSto() == null) {
            throw new IllegalArgumentException("Rezervacija, korisnik ili sto ne smeju biti null.");
        }

        if (!validnaRezervacija(rezervacija)) {
            throw new ReservationNotValidException("Nevazeca rezervacija.");
        }
        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "INSERT INTO rezervacije (korisnikId, stoId, datum, vreme) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, rezervacija.getKorisnik().getKorisnikId());
            statement.setInt(2, rezervacija.getSto().getIdStola());
            java.sql.Date sqlDate = java.sql.Date.valueOf(rezervacija.getDatum());
            statement.setDate(3, sqlDate);

            java.sql.Time sqlTime = java.sql.Time.valueOf(rezervacija.getVreme());
            statement.setTime(4, sqlTime);

            statement.executeUpdate();

            ResultSet kljucevi = statement.getGeneratedKeys();

            while (kljucevi.next()) {
                rezervacija.setRezervacijaId(kljucevi.getInt(1));
            }
        }
        return rezervacija;
    }

//    private static boolean validnaRezervacija(Rezervacija rezervacija) {
//
//        LocalDate danas = LocalDate.now();
//        LocalTime trenutnoVreme = LocalTime.now();
////        if (!rezervacija.getDatum().isBefore(danas)
////                || ((!rezervacija.getDatum().isEqual(danas) && !rezervacija.getVreme().isBefore(trenutnoVreme)))) {
////            return true;
////        }
//
//        return false;
//    }
    private static boolean validnaRezervacija(Rezervacija rezervacija) {
        LocalDate danas = LocalDate.now();
        LocalTime trenutnoVreme = LocalTime.now();

        return rezervacija.getDatum().isAfter(danas)
                || (rezervacija.getDatum().isEqual(danas) && rezervacija.getVreme().isAfter(trenutnoVreme));
    }

    public static int dohvatiPoslednjiIdRezervacije() throws SQLException {
        int poslednjiId = -1;

        try (Statement statement = DBUtil.con.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(rezervacijaId) AS poslednjiId FROM rezervacije");

            if (resultSet.next()) {
                poslednjiId = resultSet.getInt("poslednjiId");
            }
        }

        return poslednjiId;
    }

//    public static void obrisiRezervaciju(int rezervacijaId) throws SQLException {
//        try (PreparedStatement statement = DBUtil.con.prepareStatement("DELETE FROM rezervacije WHERE rezervacijaId = ?")) {
//            statement.setInt(1, rezervacijaId);
//            statement.executeUpdate();
//        }
//    }
//    public static void obrisiRezervaciju(int rezervacijaId) throws SQLException {
//        Rezervacija rezervacija = pronadjiRezervaciju(rezervacijaId);
//
//        if (rezervacija != null) {
//            Sto sto = rezervacija.getSto();
//
//            // Postavljanje statusa stola na nerezervisan
//            if (sto != null) {
//                sto.setRezervisan(false);
//                azurirajSto(sto); // Ovde pozivamo azurirajSto da bismo ažurirali status stola
//            }
//
//            // Brisanje rezervacije iz baze podataka
//            try (PreparedStatement statement = DBUtil.con.prepareStatement("DELETE FROM rezervacije WHERE rezervacijaId = ?")) {
//                statement.setInt(1, rezervacijaId);
//                statement.executeUpdate();
//            } catch (SQLException ex) {
//                ex.printStackTrace(); // Dodajte ovu liniju kako biste ispisali grešku u konzoli
//            }
//        }
//    }
//    public static void obrisiRezervaciju(int rezervacijaId) throws SQLException {
//        System.out.println("Ulaz u obrisiRezervaciju.");
//
//        Rezervacija rezervacija = pronadjiRezervaciju(rezervacijaId);
//
//        if (rezervacija != null) {
//            Sto sto = rezervacija.getSto();
//            sto.setRezervisan(false);
//
////            // Postavljanje statusa stola na nerezervisan
////            if (sto != null) {
////                sto.setRezervisan(false);
////                azurirajSto(sto); // Ovde pozivamo azurirajSto da bismo ažurirali status stola
////            }
//
//            // Brisanje rezervacije iz baze podataka
//            try (PreparedStatement statement = DBUtil.con.prepareStatement("DELETE FROM rezervacije WHERE rezervacijaId = ?")) {
//                statement.setInt(1, rezervacijaId);
//                statement.executeUpdate();
//            } catch (SQLException ex) {
//                ex.printStackTrace(); // Dodajte ovu liniju kako biste ispisali grešku u konzoli
//            }
//        }
//
//        System.out.println("Izlaz iz obrisiRezervaciju.");
//    }
    public static void obrisiRezervaciju(int rezervacijaId) throws SQLException {
        System.out.println("Ulaz u obrisiRezervaciju.");

        Rezervacija rezervacija = pronadjiRezervaciju(rezervacijaId);
        Sto sto = rezervacija.getSto();
        sto.setRezervisan(false);

        if (rezervacija != null) {
            // Brisanje rezervacije iz baze podataka
            try (PreparedStatement statement = DBUtil.con.prepareStatement("DELETE FROM rezervacije WHERE rezervacijaId = ?")) {
                statement.setInt(1, rezervacijaId);
                statement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace(); // Dodajte ovu liniju kako biste ispisali grešku u konzoli
            }
        }

        System.out.println("Izlaz iz obrisiRezervaciju.");
    }

    public static Rezervacija pronadjiRezervaciju(int rezervacijaId) throws SQLException {
        Rezervacija rezervacija = null;

        try (PreparedStatement statement = DBUtil.con.prepareStatement("SELECT * FROM rezervacije WHERE rezervacijaId = ?")) {
            statement.setInt(1, rezervacijaId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rezervacija = mapirajRezervaciju(resultSet);
            }
        }

        return rezervacija;
    }

    private static Rezervacija mapirajRezervaciju(ResultSet resultSet) throws SQLException {
        Rezervacija rezervacija = new Rezervacija();

        // Postavljamo podatke rezervacije iz ResultSet-a u objekat Rezervacija
        rezervacija.setRezervacijaId(resultSet.getInt("rezervacijaId"));
        // Postavite i druge atribute rezervacije prema strukturi vaše baze podataka

        return rezervacija;
    }

}
