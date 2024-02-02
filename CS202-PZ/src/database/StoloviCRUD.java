/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author User
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Sto;
import java.time.LocalDate;
import java.time.LocalTime;

public class StoloviCRUD {

    /**
     * Dodavanje stola u bazu podataka
     *
     * @param sto
     * @return
     * @throws SQLException
     */
    public static Sto dodajSto(Sto sto) throws SQLException {
        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "INSERT INTO stolovi (rezervisan, brojMesta) VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, sto.isRezervisan());
            statement.setInt(2, sto.getBrojMesta());

            statement.executeUpdate();
            ResultSet kljucevi = statement.getGeneratedKeys();

            while (kljucevi.next()) {
                sto.setIdStola(kljucevi.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoloviCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sto;
    }

    /**
     * Azuriranje podataka o stolu u bazi podataka
     *
     * @param sto
     * @throws SQLException
     */
    
    public static void azurirajSto(Sto sto) throws SQLException {
    try (PreparedStatement statement = DBUtil.con.prepareStatement(
            "UPDATE stolovi SET rezervisan=? , brojMesta=? WHERE stoId=?")) {
        statement.setBoolean(1, sto.isRezervisan());
        statement.setInt(2, sto.getBrojMesta());
        statement.setInt(3, sto.getIdStola());

        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            System.out.println("Status stola uspesno azuriran.");
        } else {
            System.out.println("Nema promena u statusu stola.");
        }
    } catch (SQLException ex) {
        System.out.println("Greška prilikom ažuriranja stola: " + ex.getMessage());
        ex.printStackTrace();
    }
}



    /**
     * Brisanje stola iz baze podataka
     *
     * @param sto
     * @throws SQLException
     */
    public static void obrisiSto(Sto sto) throws SQLException {
        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "DELETE FROM stolovi WHERE stoId=?")) {
            statement.setInt(1, sto.getIdStola());

            statement.executeUpdate();
        }
    }

    /**
     * Prikazivanje svih stolova iz baze podataka
     *
     * @return
     * @throws SQLException
     */
    public static List<Sto> prikaziSveStolove() throws SQLException {
        List<Sto> stolovi = new ArrayList<>();

        try (Statement statement = DBUtil.con.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM stolovi")) {

            while (resultSet.next()) {
                Sto sto = new Sto();
                sto.setIdStola(resultSet.getInt("stoId"));
                sto.setRezervisan(resultSet.getBoolean("rezervisan"));
                sto.setBrojMesta(resultSet.getInt("brojMesta"));

                stolovi.add(sto);
            }
        }

        return stolovi;
    }

    /**
     * Pronalazenje stola prema ID-u iz baze podataka
     *
     * @param idStola
     * @return
     * @throws SQLException
     */
    public static Sto pronadjiSto(int idStola) throws SQLException {
        Sto sto = new Sto();

        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "SELECT * FROM stolovi WHERE stoId=?")) {
            statement.setInt(1, idStola);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sto.setIdStola(resultSet.getInt("stoId"));
                sto.setRezervisan(resultSet.getBoolean("rezervisan"));
                sto.setBrojMesta(resultSet.getInt("brojMesta"));
            }
        }

        return sto;
    }

    /**
     * Filtriranje stolova na osnovu datuma, vremena i broja mesta
     *
     * @param datum
     * @param vreme
     * @param brojMesta
     * @return
     * @throws SQLException
     */
    public static List<Sto> filtrirajStolove(LocalDate datum, LocalTime vreme, int brojMesta) throws SQLException {
        List<Sto> filtriraniStolovi = new ArrayList<>();
        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "SELECT * FROM stolovi s "
                + "LEFT JOIN rezervacije r ON s.stoId = r.stoId "
                + "WHERE s.rezervisan = FALSE "
                + "AND s.brojMesta = ? "
                + "AND (r.datum IS NULL OR r.datum <> ? OR r.vreme IS NULL OR r.vreme <> ?)")) {
            statement.setInt(1, brojMesta);
            statement.setDate(2, java.sql.Date.valueOf(datum));
            statement.setTime(3, java.sql.Time.valueOf(vreme));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Sto sto = new Sto();
                sto.setIdStola(resultSet.getInt("stoId"));
                sto.setRezervisan(resultSet.getBoolean("rezervisan"));
                sto.setBrojMesta(resultSet.getInt("brojMesta"));

                filtriraniStolovi.add(sto);
            }
        }

        return filtriraniStolovi;
    }

}
