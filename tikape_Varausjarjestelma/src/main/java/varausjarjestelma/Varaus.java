/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Susanna Lauttia
 */
public class Varaus {
    
    Integer asiakas_id;
    Integer varaus_id;
    Date alkuPVM;
    Date loppuPVM;
    Asiakas asiakas;
    List<Huone> vapaathuoneet;
    List<String> lisavarusteet;
    Integer huonemaara;


    
    public Varaus (Date alku, Date loppu, Asiakas asiakas) {
        this.alkuPVM=alku;
        this.loppuPVM=loppu;
        this.asiakas_id=null;
        this.varaus_id=null;
        this.asiakas=asiakas;
        this.huonemaara=0;
        this.vapaathuoneet=new ArrayList();
        this.lisavarusteet=new ArrayList(); 

        
    }

    Varaus(int asiakas_id, Date alkuPVM, Date loppuPVM) {
        this.asiakas_id=asiakas_id;
        this.alkuPVM=alkuPVM;
        this.loppuPVM=loppuPVM;

    }
   
    public Integer getAsiakas_id() {
        return asiakas_id;
    }

    public Integer getVaraus_id() {
        return varaus_id;
    }

    public Integer getHuonemaara() {
        return huonemaara;
    }

    public Date getAlkuPVM() {
        return alkuPVM;
    }

    public Date getLoppuPVM() {
        return loppuPVM;
    }

    public Asiakas getAsiakas() {
        return asiakas;
    }

    public List<Huone> getVapaathuoneet() {
        return vapaathuoneet;
    }

    public List<String> getLisavarusteet() {
        return lisavarusteet;
    }


    public void setHuonemaara(Integer huonemaara) {
        this.huonemaara = huonemaara;
    }

    public void setAlkuPVM(Date alkuPVM) {
        this.alkuPVM = alkuPVM;
    }

    public void setLoppuPVM(Date loppuPVM) {
        this.loppuPVM = loppuPVM;
    }

    public void setAsiakas(Asiakas asiakas) {
        this.asiakas = asiakas;
    }

    public void setVapaathuoneet(List<Huone> vapaathuoneet) {
        this.vapaathuoneet = vapaathuoneet;
    }

    public void setLisavarusteet(List<String> lisavarusteet) {
        this.lisavarusteet = lisavarusteet;
    }

    public void setAsiakas_id(Integer asiakas_id) {
        this.asiakas_id = asiakas_id;
    }

    public void setVaraus_id(Integer varaus_id) {
        this.varaus_id = varaus_id;
    }

}
