/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.TipJela;

/**
 *
 * @author User
 */
public class Jelo {

    private int idJela;
    private String nazivJela;
    private int cenaJela;
    private TipJela tipJela;

    public Jelo() {
    }

    public Jelo(int idJela, String nazivJela, int cenaJela, TipJela tipJela) {
        this.idJela = idJela;
        this.nazivJela = nazivJela;
        this.cenaJela = cenaJela;
        this.tipJela = tipJela;
    }

    public int getIdJela() {
        return idJela;
    }

    public void setIdJela(int idJela) {
        this.idJela = idJela;
    }

    public String getNazivJela() {
        return nazivJela;
    }

    public void setNazivJela(String nazivJela) {
        this.nazivJela = nazivJela;
    }

    public int getCenaJela() {
        return cenaJela;
    }

    public void setCenaJela(int cenaJela) {
        this.cenaJela = cenaJela;
    }

    public TipJela getTipJela() {
        return tipJela;
    }

    public void setTipJela(TipJela tipJela) {
        this.tipJela = tipJela;
    }

    @Override
    public String toString() {
        return "Jelo{" + "idJela=" + idJela + ", nazivJela=" + nazivJela + ", cenaJela=" + cenaJela + ", tipJela=" + tipJela + '}';
    }

}
