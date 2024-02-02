/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import entities.Admin;
import static database.DBUtil.con;
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

/**
 * dodavanje admina u bazi podataka
 * @author User
 */
public class AdminCRUD {
    public static Admin dodajAdmina(Admin admin) throws SQLException {
        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "INSERT INTO admini (imeAdmina, lozinka) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, admin.getImeAdmina());
            statement.setString(2, admin.getLozinka());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            while (generatedKeys.next()) {
                admin.setAdminId(generatedKeys.getInt(1));
            }
        }

        return admin;
    }

    /**
     * pronalazenje baze iz baze podataka - login admina
     *
     * @param imeAdmina Admin username.
     * @param lozinka   Admin password.
     * @return Admin object if login successful, null otherwise.
     * @throws SQLException                If a database error occurs.
     * @throws UsernameNotValidException   If the username is not valid.
     * @throws PasswordNotValidException   If the password is not valid.
     */
    public static Admin loginAdmin(String imeAdmina, String lozinka)
            throws SQLException, UsernameNotValidException, PasswordNotValidException {
        Admin admin = null;

        try (PreparedStatement statement = DBUtil.con.prepareStatement(
                "SELECT * FROM admini WHERE imeAdmina = ? AND lozinka = ?")) {

            statement.setString(1, imeAdmina);
            statement.setString(2, lozinka);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                admin = new Admin();
                admin.setAdminId(resultSet.getInt("adminId"));
                admin.setImeAdmina(resultSet.getString("imeAdmina"));
                admin.setLozinka(resultSet.getString("lozinka"));
            }
        }

        return admin;
    }

}
