package varausjarjestelma;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import varausjarjestelma.Dao;
import varausjarjestelma.Raportti;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Susanna Lauttia
 */
@Component
public class RaporttiDao implements Dao <Raportti, Integer> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void create(Raportti object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Raportti read(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Raportti update(Raportti object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Raportti> list() throws SQLException {
        
    String listBookingsQuery="Select Asiakas.nimi AS nimi, Asiakas.sahkoposti AS sahkoposti, Varaus.alkuPVM AS alkuPVM, "
    + "Varaus.loppuPVM AS loppuPVM, DATEDIFF(day, Varaus.alkuPVM, Varaus.loppuPVM) AS paivat, COUNT(Lisavaruste.varaus_id) AS lisavarusteet, "
    +"COUNT(HuoneVaraus.varaus_id) AS huoneet FROM Asiakas "+
    "JOIN Varaus ON Varaus.asiakas_id=Asiakas.id "+
    "LEFT JOIN Lisavaruste ON Lisavaruste.varaus_id= Varaus.id " 
    +" JOIN HuoneVaraus ON HuoneVaraus.varaus_id=Varaus.id "
    +"GROUP BY Asiakas.nimi ORDER BY Varaus.alkuPVM ASC";
   
           List<Raportti> varausraportti=jdbcTemplate.query(listBookingsQuery, 
                (rs, rowNum) -> new Raportti(rs));
            
        
   
        return varausraportti;  
    }   

    @Override
    public List<Raportti> list2(Date alkuPVM, Date loppuPVM, String huonetyyppi, String hinta) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
