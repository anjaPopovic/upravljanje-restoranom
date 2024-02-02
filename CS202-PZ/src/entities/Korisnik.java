/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import exceptions.InputNotValidException;
import exceptions.PasswordNotValidException;
import exceptions.UsernameNotValidException;

/**
 *
 * @author User
 */
public class Korisnik {

    private int korisnikId;
    private String ime;
    private String prezime;
    private String brojTelefona;
    private String mejl;
    private String korisnickoIme;
    private String lozinka;
    
    public Korisnik() {
    }
    
    public Korisnik(int korisnikId, String ime, String prezime, String brojTelefona, String mejl, String korisnickoIme, String lozinka) throws InputNotValidException, UsernameNotValidException, PasswordNotValidException {
        this.korisnikId = korisnikId;
        setIme(ime);
        setPrezime(prezime);
        setBrojTelefona(brojTelefona);
        setMejl(mejl);
        setKorisnickoIme(korisnickoIme);
        setLozinka(lozinka);
    }
    
    public Korisnik(String korisnickoIme, String lozinka) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }
    
    public int getKorisnikId() {
        return korisnikId;
    }
    
    public void setKorisnikId(int id) {
        this.korisnikId = id;
    }
    
    public String getIme() {
        return ime;
    }
    
    public void setIme(String ime) throws InputNotValidException {
        if (Metode.samoSlovaPrisutna(ime)) {
            this.ime = ime;            
        } else {
            throw new InputNotValidException("Ime sadrzi samo slova!");
        }        
    }
    
    public String getPrezime() {
        return prezime;
    }
    
    public void setPrezime(String prezime) throws InputNotValidException {
        if (Metode.samoSlovaPrisutna(prezime)) {
            this.prezime = prezime;
        } else {
            throw new InputNotValidException("Prezime sadrzi samo slova!");
        }
    }
    
    public String getBrojTelefona() {
        return brojTelefona;
    }
    
    public void setBrojTelefona(String brojTelefona) throws InputNotValidException {
        if (brojTelefona.length() <= 10 && brojTelefona.matches("^06[0-9]{0,8}$") && Metode.samoBrojeviPrisutni(brojTelefona)) {
            this.brojTelefona = brojTelefona;
        } else {
            throw new InputNotValidException("Broj telefona mora imati do 10 cifara i mora pocinjati sa 06!");
        }
    }
    
    public String getMejl() {
        return mejl;
    }
    
    public void setMejl(String mejl) throws InputNotValidException {
        if (mejl.matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+")) {
            this.mejl = mejl;
        } else {
            throw new InputNotValidException("Unesite ispravnu formataciju mejl naloga!");
        }
    }
    
    public String getKorisnickoIme() {
        return korisnickoIme;
    }
    
    public void setKorisnickoIme(String korisnickoIme) throws UsernameNotValidException {
        if (korisnickoIme.length() < 20) {
            this.korisnickoIme = korisnickoIme;
        } else {
            throw new UsernameNotValidException("Korisnicko ime mora imati do 20 karaktera!");
        }
    }
    
    public String getLozinka() {
        return lozinka;
    }
    
    public void setLozinka(String lozinka) throws PasswordNotValidException {
        if (lozinka.length() < 20 && lozinka.matches(".*[A-Z].*") && lozinka.matches(".*[a-z].*")
                && lozinka.matches(".*\\d.*")) {
            this.lozinka = lozinka;
        } else {
            throw new PasswordNotValidException("Lozinka mora imati manje od 20 karaktera, jedno veliko slovo, i broj!");
        }
    }
    
    @Override
    public String toString() {
        return "Korisnik{" + "id=" + korisnikId + ", ime=" + ime + ", prezime=" + prezime + ", brojTelefona=" + brojTelefona + ", mejl=" + mejl + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + '}';
    }
    
}
