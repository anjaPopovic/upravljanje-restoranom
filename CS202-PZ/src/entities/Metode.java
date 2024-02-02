/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author User
 */
public class Metode {

    public static boolean samoSlovaPrisutna(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean samoBrojeviPrisutni(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validanMejl(String email) {
        String emailRegex = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+";
        return email.matches(emailRegex);
    }

    public static boolean validnaLozinka(String lozinka) {
        return lozinka.length() < 20 && lozinka.matches(".*[A-Z].*")
                && lozinka.matches(".*[a-z].*") && lozinka.matches(".*\\d.*");
    }

    public static boolean validnoVreme(String inputTime) {
        try {
            LocalTime.parse(inputTime, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validniDatum(String inputDate) {
        try {
            LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static boolean validanBrojMesta(String inputSeats) {
    try {
        int numberOfSeats = Integer.parseInt(inputSeats);
        return (numberOfSeats > 0 && numberOfSeats <= 9);  
    } catch (NumberFormatException e) {
        return false;
    }
}


}
