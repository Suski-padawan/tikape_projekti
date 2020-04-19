/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

/**
 *
 * @author Susanna Lauttia
 */
public class Asiakas {
    String nimi;
    String puhelinnumero;
    String sahkoposti;
    
    public Asiakas (String nimi, String puhelinnumero, String sahkoposti) {
        this.nimi=nimi;
        this.puhelinnumero=puhelinnumero;
        this.sahkoposti=sahkoposti;
    }

    public String getNimi() {
        return nimi;
    }

    public String getPuhelinnumero() {
        return puhelinnumero;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setPuhelinnumero(String puhelinnumero) {
        this.puhelinnumero = puhelinnumero;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

}
