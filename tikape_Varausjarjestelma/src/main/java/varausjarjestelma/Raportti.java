/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Susanna Lauttia
 */
public class Raportti {
    
    String nimi;
    String sahkoposti;
    Date alkuPVM;
    Date loppuPVM;
    int paivat;
    int lisavarusteet;
    int huoneet;
    
    
    public Raportti (ResultSet rs) throws SQLException {
        
        this.nimi=rs.getString("nimi");
        this.sahkoposti=rs.getString("sahkoposti");
        this.alkuPVM=rs.getDate("alkuPVM");
        this.loppuPVM=rs.getDate("loppuPVM");
        this.paivat=rs.getInt("paivat");
        this.lisavarusteet=rs.getInt("lisavarusteet");
        this.huoneet=rs.getInt("huoneet");
        
    }

    @Override
    public String toString() {        
        String rivi;
       // if (huoneet==1) {
       //     return  nimi + ", " + sahkoposti + ", " + alkuPVM + ", " + loppuPVM + ", " + paivat + " paivaa, "+ lisavarusteet+ " lisävarustetta, "+huoneet+ " huone";
       // }
        rivi = nimi + ", " 
                + sahkoposti + ", " 
                + alkuPVM + ", " 
                + loppuPVM + ", ";
        
        if(paivat==1) {
            rivi = rivi + paivat + " päivä, ";
        }
        else {
            rivi = rivi + paivat + " päivää, ";
        }
        
        if(lisavarusteet==1) {
            rivi = rivi + lisavarusteet + " lisävaruste, ";
        }
        else {
            rivi = rivi + lisavarusteet + " lisävarustetta, ";
        }
        
        if(huoneet==1) {
            rivi = rivi + huoneet + " huone.";
        }
        else {
            rivi = rivi + huoneet + " huonetta.";
        }
        rivi = rivi + " Huoneet: \nHuoneiden tulostusta ei implementoitu :(\n";
     return rivi;
     
    }
}