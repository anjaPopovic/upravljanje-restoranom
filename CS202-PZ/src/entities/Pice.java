/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.TipPica;

/**
 *
 * @author User
 */
public class Pice {
    private int idPica;
    private String nazivPica;
    private int cenaPica;
    private TipPica tipPica;

    public Pice() {
    }

    public Pice(int idPica, String nazivPica, int cenaPica, TipPica tipPica) {
        this.idPica = idPica;
        this.nazivPica = nazivPica;
        this.cenaPica = cenaPica;
        this.tipPica = tipPica;
    }

    public int getIdPica() {
        return idPica;
    }

    public void setIdPica(int idPica) {
        this.idPica = idPica;
    }

    public String getNazivPica() {
        return nazivPica;
    }

    public void setNazivPica(String nazivPica) {
        this.nazivPica = nazivPica;
    }

    public int getCenaPica() {
        return cenaPica;
    }

    public void setCenaPica(int cenaPica) {
        this.cenaPica = cenaPica;
    }

    public TipPica getTipPica() {
        return tipPica;
    }

    public void setTipPica(TipPica tipPica) {
        this.tipPica = tipPica;
    }

    @Override
    public String toString() {
        return "Pice{" + "idPica=" + idPica + ", nazivPica=" + nazivPica + ", cenaPica=" + cenaPica + ", tipPica=" + tipPica + '}';
    }
    
    
}
