/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author User
 */
public class Rezervacija {

    private Korisnik korisnik;
    private Sto sto;
    private int rezervacijaId;
    private LocalDate datum;
    private LocalTime vreme;

    public Rezervacija() {
    }

    public Rezervacija(Korisnik korisnik, Sto sto, int rezervacijaId, LocalDate datum, LocalTime vreme) {
        this.korisnik = korisnik;
        this.sto = sto;
        this.rezervacijaId = rezervacijaId;
        this.datum = datum;
        this.vreme = vreme;
    }    

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Sto getSto() {
        return sto;
    }

    public void setSto(Sto sto) {
        this.sto = sto;
    }

    public int getRezervacijaId() {
        return rezervacijaId;
    }

    public void setRezervacijaId(int rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getVreme() {
        return vreme;
    }

    public void setVreme(LocalTime vreme) {
        this.vreme = vreme;
    }

    @Override
    public String toString() {
        return "Rezervacija{" + "korisnik=" + korisnik + ", sto=" + sto + ", rezervacijaId=" + rezervacijaId + ", datum=" + datum + ", vreme=" + vreme + '}';
    }
    
}
