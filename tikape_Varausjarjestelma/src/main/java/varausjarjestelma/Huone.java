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
public class Huone {
    Integer huoneennumero;
    String huoneentyyppi;
    Integer hinta;
    
    
    public Huone (Integer numero, String tyyppi, Integer hinta) {
        this.huoneennumero=numero;
        this.huoneentyyppi=tyyppi;
        this.hinta=hinta;  
    }


    public Integer getHuoneennumero() {
        return huoneennumero;
    }

    public String getHuoneentyyppi() {
        return huoneentyyppi;
    }

    public Integer getHinta() {
        return hinta;
    }


    public void setHuoneennumero(Integer huoneennumero) {
        this.huoneennumero = huoneennumero;
    }

    public void setHuoneentyyppi(String huoneentyyppi) {
        this.huoneentyyppi = huoneentyyppi;
    }

    public void setHinta(Integer hinta) {
        this.hinta = hinta;
    }

    @Override
    public String toString() {
        return huoneentyyppi +", "+huoneennumero +", "+  hinta +" euroa";
    }
    
}
