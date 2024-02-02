/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import entities.Korisnik;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author User
 */
public class DBUtil {

    public static java.sql.Connection con = null;
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String dbName = "cs202Projekat";
    private static String username = "root";
    private static String password = "";

    public static void initializeDB() {
        try (Connection con = DriverManager.getConnection(url, username, password); Statement stmt = con.createStatement()) {
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            stmt.execute("USE " + dbName);
            stmt.execute("CREATE TABLE IF NOT EXISTS korisnici ("
                    + "korisnikId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                    + "ime VARCHAR(20) NOT NULL,"
                    + "prezime VARCHAR(30) NOT NULL,"
                    + "brojTelefona VARCHAR(10) NOT NULL,"
                    + "mejl VARCHAR(50) NOT NULL,"
                    + "korisnickoIme VARCHAR(15) NOT NULL,"
                    + "lozinka VARCHAR(20) NOT NULL"
                    + ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS stolovi ("
                    + "stoId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                    + "rezervisan BOOLEAN DEFAULT FALSE,"
                    + "brojMesta INT NOT NULL"
                    + ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS rezervacije ("
                    + "rezervacijaId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                    + "korisnikId INT NOT NULL,"
                    + "stoId INT NOT NULL,"
                    + "datum DATE NOT NULL,"
                    + "vreme TIME NOT NULL,"
                    + "FOREIGN KEY (korisnikId) REFERENCES korisnici(korisnikId),"
                    + "FOREIGN KEY (stoId) REFERENCES stolovi(stoId)"
                    + ")");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS admini ("
                    + "adminId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                    + "imeAdmina VARCHAR(15) NOT NULL,"
                    + "lozinka VARCHAR(20)"
                    + ")");

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    /**
     * Metoda za otvaranje konekcije
     *
     * @throws SQLException
     */
    public static void openConnection() throws SQLException {
        con = DriverManager.getConnection(url + dbName, username, password);
    }

    /**
     * Metoda za zatvaranje konekcije
     *
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
}
