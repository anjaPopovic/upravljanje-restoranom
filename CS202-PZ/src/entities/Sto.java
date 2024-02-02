/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author User
 */
public class Sto {
    private int idStola;
    private boolean rezervisan = false;
    private int brojMesta;

    public Sto() {
    }

    public Sto(int idStola, int brojMesta) {
        this.idStola = idStola;
        this.brojMesta = brojMesta;
    }

    public int getIdStola() {
        return idStola;
    }

    public void setIdStola(int idStola) {
        this.idStola = idStola;
    }

    public boolean isRezervisan() {
        return rezervisan;
    }

    public void setRezervisan(boolean rezervisan) {
        this.rezervisan = rezervisan;
    }

    public int getBrojMesta() {
        return brojMesta;
    }

    public void setBrojMesta(int brojMesta) {
        this.brojMesta = brojMesta;
    }

    @Override
    public String toString() {
        return "Sto{" + "idStola=" + idStola + ", rezervisan=" + rezervisan + ", brojMesta=" + brojMesta + '}';
    }

    
}
