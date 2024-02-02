/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author User
 */
public class Admin {
    private int adminId;
    private String imeAdmina;
    private String lozinka;

    public Admin() {
    }

    public Admin(int adminId, String imeAdmina, String lozinka) {
        this.adminId = adminId;
        this.imeAdmina = imeAdmina;
        this.lozinka = lozinka;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getImeAdmina() {
        return imeAdmina;
    }

    public void setImeAdmina(String imeAdmina) {
        this.imeAdmina = imeAdmina;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    @Override
    public String toString() {
        return "Admin{" + "adminId=" + adminId + ", imeAdmina=" + imeAdmina + ", lozinka=" + lozinka + '}';
    }

    
}
