package varausjarjestelma;

import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Tekstikayttoliittyma {
    
    @Autowired
    HuoneDao huoneDao;
    
    @Autowired
    VarausDao varausDao;
    
        
    @Autowired
    RaporttiDao raporttiDao;
    
    
    @Autowired
    JdbcTemplate jdbcTemplate;


    public void kaynnista(Scanner lukija) throws SQLException {
        while (true) {
            System.out.println("Komennot: ");
            System.out.println(" x - lopeta");
            System.out.println(" 1 - lisaa huone");
            System.out.println(" 2 - listaa huoneet");
            System.out.println(" 3 - hae huoneita");
            System.out.println(" 4 - lisaa varaus");
            System.out.println(" 5 - listaa varaukset");
            System.out.println(" 6 - tilastoja");
            System.out.println("");

            String komento = lukija.nextLine();
            if (komento.equals("x")) {
                break;
            }

            if (komento.equals("1")) {
                lisaaHuone(lukija);
            } else if (komento.equals("2")) {
                listaaHuoneet();
            } else if (komento.equals("3")) {
                haeHuoneita(lukija);
            } else if (komento.equals("4")) {
                lisaaVaraus(lukija);
            } else if (komento.equals("5")) {
                listaaVaraukset();
            } else if (komento.equals("6")) {
                tilastoja(lukija);
            }
        }
    }

    private void lisaaHuone(Scanner lukija)  {
     
            System.out.println("Lisätään huone");
            System.out.println("");
            
            System.out.println("Minkä tyyppinen huone on?");
            String tyyppi = lukija.nextLine();
            System.out.println("Mikä huoneen numeroksi asetetaan?");
            int numero = Integer.valueOf(lukija.nextLine());
            System.out.println("Kuinka monta euroa huone maksaa yöltä?");
            int hinta = Integer.valueOf(lukija.nextLine());
            
            Huone huone=new Huone(numero, tyyppi, hinta);
            try {
                Huone onkoHuoneJoLisatty=huoneDao.read(huone.getHuoneennumero());
                if (onkoHuoneJoLisatty==null) {
                   huoneDao.create(huone);
                } else {
                    System.out.println("Huone on jo lisätty");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Tekstikayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                      
    private void listaaHuoneet() {
        System.out.println("Listataan huoneet");
        System.out.println("");
        try {
            List <Huone> huoneet=huoneDao.list();
           
            huoneet.forEach(System.out::println);
        } catch (SQLException ex) {
            Logger.getLogger(Tekstikayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");

        
    }

    private void haeHuoneita(Scanner lukija) {
            List<Huone> vapaathuoneet=new ArrayList();
            System.out.println("Haetaan huoneita");
            System.out.println("");
            
            System.out.println("Milloin varaus alkaisi (yyyy-MM-dd)?");;
            LocalDateTime alku = LocalDateTime.parse(lukija.nextLine() + " " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.println("Milloin varaus loppuisi (yyyy-MM-dd)?");
            LocalDateTime loppu = LocalDateTime.parse(lukija.nextLine() + " " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.println("Minkä tyyppinen huone? (tyhjä = ei rajausta)");
            String tyyppi = lukija.nextLine();
            System.out.println("Minkä hintainen korkeintaan? (tyhjä = ei rajausta)");
            String maksimihinta = lukija.nextLine();
            
            Date alkuPVM=new Date(alku.getYear()-1900,alku.getMonthValue()-1, alku.getDayOfMonth());
            Date loppuPVM=new Date(loppu.getYear()-1900, loppu.getMonthValue()-1,loppu.getDayOfMonth());
            /*System.out.println(alku);
            System.out.println(alkuPVM);
            System.out.println(loppu);
            System.out.println(loppuPVM);*/
            try {
                vapaathuoneet=huoneDao.list2(alkuPVM, loppuPVM, tyyppi, maksimihinta);
            } catch (SQLException ex) {
                Logger.getLogger(Tekstikayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(vapaathuoneet.isEmpty()) {
                System.out.println("Ei vapaita huoneita.");
            }
            
            System.out.println("Vapaat huoneet: ");
            
            vapaathuoneet.forEach(System.out::println);
            
    }

    private void lisaaVaraus(Scanner lukija) {

            while (true) {
            System.out.println("Haetaan huoneita");
            System.out.println("");
            
            System.out.println("Milloin varaus alkaisi (yyyy-MM-dd)?");;
            LocalDateTime alku = LocalDateTime.parse(lukija.nextLine() + " " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.println("Milloin varaus loppuisi (yyyy-MM-dd)?");
            LocalDateTime loppu = LocalDateTime.parse(lukija.nextLine() + " " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.println("Minkä tyyppinen huone? (tyhjä = ei rajausta)");
            String tyyppi = lukija.nextLine();
            System.out.println("Minkä hintainen korkeintaan? (tyhjä = ei rajausta)");
            String maksimihinta = lukija.nextLine();
            
            Date alkuPVM=new Date(alku.getYear()-1900,alku.getMonthValue()-1, alku.getDayOfMonth());
            Date loppuPVM=new Date(loppu.getYear()-1900, loppu.getMonthValue()-1,loppu.getDayOfMonth());
            //Haetaan vapaat huoneet:
            List<Huone> vapaathuoneet=new ArrayList();
            
            
            try {
                vapaathuoneet=huoneDao.list2(alkuPVM, loppuPVM, tyyppi, maksimihinta);
                
            } catch (SQLException ex) {
            Logger.getLogger(Tekstikayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
            // mikäli huoneita ei ole vapaana, ohjelma tulostaa seuraavan viestin
            // ja varauksen lisääminen loppuu
            if (vapaathuoneet.isEmpty()) {
                System.out.println("Ei vapaita huoneita!");
                break;
            } 

            System.out.println("Huoneita vapaana:"+vapaathuoneet.size());
            System.out.println("");

                
            
            int huoneita = 0;
                while (true) {
                    System.out.println("Montako huonetta varataan?");
                    huoneita = Integer.valueOf(lukija.nextLine());
                    if (huoneita > 0 || huoneita < vapaathuoneet.size()-1) {
                        break;
                    } else {
                        System.out.println("Epäkelpo huoneiden lukumäärä.");
                        continue;
                    }
                }          
            // tämän jälkeen kysytään lisävarusteet
            List<String> lisavarusteet = new ArrayList<>();
            while (true) {
                System.out.println("Syötä lisävaruste, tyhjä lopettaa");
                String lisavaruste = lukija.nextLine();
                if (lisavaruste.isEmpty()) {
                    break;
                }
                
                lisavarusteet.add(lisavaruste);
            }
            
            // ja lopuksi varaajan tiedot
            System.out.println("Syötä varaajan nimi:");
            String nimi = lukija.nextLine();
            System.out.println("Syötä varaajan puhelinnumero:");
            String puhelinnumero = lukija.nextLine();
            System.out.println("Syötä varaajan sähköpostiosoite:");
            String sahkoposti = lukija.nextLine();
            
            //Luodaan uusi asiakas
            Asiakas asiakas=new Asiakas(nimi, puhelinnumero, sahkoposti);
            //Luodaan uusi varaus
            Varaus varaus=new Varaus(alkuPVM, loppuPVM, asiakas);
            //Tallennetaan lisavarusteet
            varaus.setLisavarusteet(lisavarusteet);
            //Tallennetaan huonemäärä talteen
            varaus.setHuonemaara(huoneita);
            //Tallennetaan vapaat huoneet talteen:
            varaus.setVapaathuoneet(vapaathuoneet);
            //Ja sitten yritetään luoda koko varaushässäkkä
                try {
                    varausDao.create(varaus);
                    System.out.println("Varaus lisätty!");
        
                } catch (SQLException ex) {
                    Logger.getLogger(Tekstikayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;        
    
        }
    }        
   
    private void listaaVaraukset() {
        System.out.println("Listataan varaukset");
        System.out.println("");
        try {
            List <Raportti> varaukset=raporttiDao.list();
            varaukset.forEach(System.out::println);
        } catch (SQLException ex) {
            Logger.getLogger(Tekstikayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");


        // alla olevassa esimerkissä oletetaan, että tietokannassa on 
        // kolme varausta
        /*
        System.out.println("Essi Esimerkki, essi@esimerkki.net, 2019-02-14, 2019-02-15, 1 päivä, 2 lisävarustetta, 1 huone. Huoneet:");
        System.out.println("\tCommodore, 128, 229 euroa");
        System.out.println("\tYhteensä: 229 euroa");
        System.out.println("");
        System.out.println("Anssi Asiakas, anssi@asiakas.net, 2019-02"
                + "2-14, 2019-02-15, 1 päivä, 0 lisävarustetta, 1 huone. Huoneet:");
        System.out.println("\tSuperior, 705, 159 euroa");
        System.out.println("\tYhteensä: 159 euroa");
        System.out.println("");
        System.out.println("Anssi Asiakas, anssi@asiakas.net, 2020-03-18, 2020-03-21, 3 päivää, 6 lisävarustetta, 2 huonetta. Huoneet:");
        System.out.println("\tSuperior, 705, 159 euroa");
        System.out.println("\tCommodore, 128, 229 euroa");
        System.out.println("\tYhteensä: 1164 euroa");
        */
    }

    private void tilastoja(Scanner lukija) {
        System.out.println("Mitä tilastoja tulostetaan?");
        System.out.println("");

        // tilastoja pyydettäessä käyttäjältä kysytään tilasto
        System.out.println(" 1 - Suosituimmat lisävarusteet");
        System.out.println(" 2 - Parhaat asiakkaat");
        System.out.println(" 3 - Varausprosentti huoneittain");
        System.out.println(" 4 - Varausprosentti huonetyypeittäin");

        System.out.println("Syötä komento: ");
        int komento = Integer.valueOf(lukija.nextLine());

        if (komento == 1) {
            suosituimmatLisavarusteet();
        } else if (komento == 2) {
            parhaatAsiakkaat();
        } else if (komento == 3) {
            varausprosenttiHuoneittain(lukija);
        } else if (komento == 4) {
            varausprosenttiHuonetyypeittain(lukija);
        }
    }

    private void suosituimmatLisavarusteet() {
        System.out.println("Tulostetaan suosituimmat lisävarusteet");
        System.out.println("");

        // alla oletetaan, että lisävarusteita on vain muutama
        // mikäli tietokannassa niitä on enemmän, tulostetaan 10 suosituinta
        System.out.println("Teekannu, 2 varausta");
        System.out.println("Kahvinkeitin, 2 varausta");
        System.out.println("Silitysrauta, 1 varaus");
    }

    private void parhaatAsiakkaat() {
        System.out.println("Tulostetaan parhaat asiakkaat");
        System.out.println("");

        // alla oletetaan, että asiakkaita on vain 2
        // mikäli tietokannassa niitä on enemmän, tulostetaan asiakkaita korkeintaan 10
        System.out.println("Anssi Asiakas, anssi@asiakas.net, +358441231234, 1323 euroa");
        System.out.println("Essi Esimerkki, essi@esimerkki.net, +358443214321, 229 euroa");
    }

    private void varausprosenttiHuoneittain(Scanner lukija) {
        System.out.println("Tulostetaan varausprosentti huoneittain");
        System.out.println("");

        System.out.println("Mistä lähtien tarkastellaan?");
        LocalDateTime alku = LocalDateTime.parse(lukija.nextLine() + "-01 " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Mihin asti tarkastellaan?");
        LocalDateTime loppu = LocalDateTime.parse(lukija.nextLine() + "-01 " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // alla esimerkkitulostus
        System.out.println("Tulostetaan varausprosentti huoneittain");
        System.out.println("Excelsior, 604, 119 euroa, 0.0%");
        System.out.println("Excelsior, 605, 119 euroa, 0.0%");
        System.out.println("Superior, 705, 159 euroa, 22.8%");
        System.out.println("Commodore, 128, 229 euroa, 62.8%");
    }

    private void varausprosenttiHuonetyypeittain(Scanner lukija) {
        System.out.println("Tulostetaan varausprosentti huonetyypeittäin");
        System.out.println("");

        System.out.println("Mistä lähtien tarkastellaan?");
        LocalDateTime alku = LocalDateTime.parse(lukija.nextLine() + "-01 " + "16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Mihin asti tarkastellaan?");
        LocalDateTime loppu = LocalDateTime.parse(lukija.nextLine() + "-01 " + "10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // alla esimerkkitulostus
        System.out.println("Tulostetaan varausprosentti huonetyypeittän");
        System.out.println("Excelsior, 0.0%");
        System.out.println("Superior, 22.8%");
        System.out.println("Commodore, 62.8%");
    }
    
}


