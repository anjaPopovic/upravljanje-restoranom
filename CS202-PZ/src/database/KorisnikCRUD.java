/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import static database.DBUtil.con;
import entities.Korisnik;
import exceptions.InputNotValidException;
import exceptions.PasswordNotValidException;
import exceptions.UsernameNotValidException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

/**
 *
 * @author User
 */
public class KorisnikCRUD {

    /**
     * Dodavanje korisnika u bazu podataka - registracija
     *
     * @param korisnik
     * @return
     * @throws SQLException
     */
    public static Korisnik dodajKorisnika(Korisnik korisnik) throws SQLException {
        try (
                PreparedStatement statement = con.prepareStatement(
                        "INSERT INTO korisnici (ime, prezime, brojTelefona, mejl, korisnickoIme, lozinka) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, korisnik.getIme());
            statement.setString(2, korisnik.getPrezime());
            statement.setString(3, korisnik.getBrojTelefona());
            statement.setString(4, korisnik.getMejl());
            statement.setString(5, korisnik.getKorisnickoIme());
            statement.setString(6, korisnik.getLozinka());

            statement.executeUpdate();
            ResultSet kljucevi = statement.getGeneratedKeys();

            while (kljucevi.next()) {
                korisnik.setKorisnikId(kljucevi.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return korisnik;
    }

    /**
     * Azuriranje korisnika u bazi podataka
     *
     * @param korisnik
     * @throws SQLException
     */
    public static void azurirajKorisnika(Korisnik korisnik) throws SQLException {
        try (
                PreparedStatement statement = con.prepareStatement(
                        "UPDATE korisnici SET ime=?, prezime=?, brojTelefona=?, mejl=?, korisnickoIme=?, lozinka=? WHERE korisnikId=?")) {
            statement.setString(1, korisnik.getIme());
            statement.setString(2, korisnik.getPrezime());
            statement.setString(3, korisnik.getBrojTelefona());
            statement.setString(4, korisnik.getMejl());
            statement.setString(5, korisnik.getKorisnickoIme());
            statement.setString(6, korisnik.getLozinka());

            statement.setInt(7, korisnik.getKorisnikId());

            statement.executeUpdate();
        }
    }

    /**
     * Brisanje korisnika iz baze podataka
     *
     * @param korisnik
     * @throws SQLException
     */
    public static void obrisiKorisnika(int korisnikId) throws SQLException {
        try (
                PreparedStatement statement = con.prepareStatement("DELETE FROM korisnici WHERE korisnikId=?")) {
            statement.setInt(1, korisnikId);

            statement.executeUpdate();
        }
    }

    /**
     * Pronalazenje korisnika iz baze podataka - login
     *
     * @param korisnickoIme
     * @param lozinka
     * @return
     * @throws SQLException
     * @throws UsernameNotValidException
     * @throws PasswordNotValidException
     */
    public static Korisnik pronadjiKorisnika(String korisnickoIme, String lozinka) throws SQLException, UsernameNotValidException, PasswordNotValidException {
        Korisnik korisnik = null;
        PreparedStatement statement = con.prepareStatement("SELECT * FROM korisnici WHERE korisnickoIme = ? AND lozinka = ?");
        statement.setString(1, korisnickoIme);
        statement.setString(2, lozinka);

        ResultSet set = statement.executeQuery();

        while (set.next()) {
            korisnik = new Korisnik();
            try {
                korisnik.setKorisnikId(set.getInt("korisnikId"));
                korisnik.setIme(set.getString("ime"));
                korisnik.setPrezime(set.getString("prezime"));
                korisnik.setBrojTelefona(set.getString("brojTelefona"));
                korisnik.setMejl(set.getString("mejl"));
                korisnik.setKorisnickoIme(set.getString("korisnickoIme"));
                korisnik.setLozinka(set.getString("lozinka"));
            } catch (InputNotValidException ex) {
                ex.printStackTrace();
            }
        }
        return korisnik;
    }

}
