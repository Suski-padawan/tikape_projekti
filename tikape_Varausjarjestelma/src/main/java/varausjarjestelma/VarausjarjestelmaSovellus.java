package varausjarjestelma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VarausjarjestelmaSovellus implements CommandLineRunner {

    public static void main(String[] args) {
        alustaTietokanta();
        SpringApplication.run(VarausjarjestelmaSovellus.class);

    }

    @Autowired
    Tekstikayttoliittyma tekstikayttoliittyma;

    @Override
    public void run(String... args) throws Exception {
        Scanner lukija = new Scanner(System.in);
        tekstikayttoliittyma.kaynnista(lukija);
    }
    
    private static void alustaTietokanta() {
   

        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
            conn.prepareStatement("DROP TABLE Huone IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Huone(huoneennumero INTEGER PRIMARY KEY, huoneentyyppi varchar(255), hinta INTEGER);").executeUpdate();
            conn.prepareStatement("DROP TABLE Asiakas IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Asiakas(id serial PRIMARY KEY, nimi varchar(255), sahkoposti varchar(255), puhelinnumero varchar(255));").executeUpdate();
            conn.prepareStatement("DROP TABLE Varaus IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Varaus(id serial PRIMARY KEY, asiakas_id INTEGER, alkuPVM date, loppuPVM date, FOREIGN KEY (asiakas_id) REFERENCES Asiakas(id));").executeUpdate();
            conn.prepareStatement("DROP TABLE HuoneVaraus IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE HuoneVaraus(huone_id INTEGER, varaus_id INTEGER, FOREIGN KEY (huone_id) REFERENCES Huone(huoneennumero), FOREIGN KEY (varaus_id) REFERENCES Varaus(id));").executeUpdate();
            conn.prepareStatement("DROP TABLE Lisavaruste IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Lisavaruste(id serial PRIMARY KEY, varaus_id INTEGER, nimi varchar(255), FOREIGN KEY (varaus_id) REFERENCES Varaus(id));").executeUpdate();
     
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}


