/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Susanna Lauttia
 */
@Component
public class VarausDao implements Dao <Varaus, Integer>{
    @Autowired
    JdbcTemplate jdbcTemplate;

    
    //Tässä hoidetaan kaikki varaukseen liittyvät insert into -komennot putkeen. Ehkä tätä osiota voisi jossain vaiheessa selkeyttää?
    @Override
    public void create(Varaus varaus) throws SQLException {
        
    //Lisataan ensin asiakas    
     KeyHolder asiakasKey = new GeneratedKeyHolder();
     jdbcTemplate.update(connection -> {
     PreparedStatement stmt = connection.prepareStatement(
         "INSERT INTO Asiakas (nimi, sahkoposti, puhelinnumero) "
         + "VALUES (?, ?, ?)", new String[]{"id"});
             stmt.setString(1, varaus.getAsiakas().getNimi());
             stmt.setString(2, varaus.getAsiakas().getSahkoposti());
             stmt.setString(3, varaus.getAsiakas().getPuhelinnumero());
             return stmt;
     }, asiakasKey);
     varaus.setAsiakas_id(asiakasKey.getKey().intValue());
     
    //Sitten lisätään varaus:
     
     KeyHolder varausKey = new GeneratedKeyHolder();
     jdbcTemplate.update(connection -> {
     PreparedStatement stmt = connection.prepareStatement(
         "INSERT INTO Varaus (asiakas_id, alkuPVM, loppuPVM) "
         + "VALUES (?, ?, ?)", new String[]{"id"});
             stmt.setInt(1, varaus.getAsiakas_id());
             stmt.setDate(2, varaus.getAlkuPVM());
             stmt.setDate(3, varaus.getLoppuPVM());
             return stmt;
     }, varausKey);
     varaus.setVaraus_id(varausKey.getKey().intValue());
     
     //Varaus-id:tä tarvitsee varauslisavaruste-tauluun sekä huonevaraus-tauluun
     //Toteutetaan ensin huonevaraus-taulun ratkaisut
     //1. sortataan vapaathuoneet-lista hinnan mukaan laskevaan järjestykseen (reversed)
     //2. kerätään listaan, niin monta huonetta kuin on tarkoitus varata (limit)
     
     List<Huone> vapaathuoneet=varaus.getVapaathuoneet();
     List <Huone> varattavatHuoneet = vapaathuoneet.stream()
                    .sorted(Comparator.comparingInt(Huone::getHinta)
                    .reversed())
                    .limit(varaus.getHuonemaara())
                    .collect(Collectors.toCollection(ArrayList::new));
     
     int i=0;
     while (i<varattavatHuoneet.size()) {
        Huone varattavahuone=varattavatHuoneet.get(i);    
        String addRoomQuery="INSERT INTO HuoneVaraus (varaus_id, huone_id) VALUES (?, ?)";
        jdbcTemplate.update(addRoomQuery, varaus.getVaraus_id(),varattavatHuoneet.get(i).getHuoneennumero());
        i++;
     }    
     
    //Seuraavaksi pitää hoitaa vielä lisävarusteet lisavaruste-tauluun:
        for (i=0; i<varaus.getLisavarusteet().size();i++) {
            String lisavaruste=varaus.getLisavarusteet().get(i);
            String addStuffQuery="INSERT INTO Lisavaruste (varaus_id, nimi) VALUES (?, ?)";
            jdbcTemplate.update(addStuffQuery, varaus.getVaraus_id(), lisavaruste);   
        }
    }
    @Override
    public Varaus read(Integer key) throws SQLException {
        List<Varaus> varaukset = jdbcTemplate.query("SELECT * FROM Varaus WHERE Varaus.id = ?",
            (rs, rowNum) -> new Varaus(rs.getInt("asiakas_id"),rs.getDate("alkuPVM"),rs.getDate("loppuPVM")),
             key);

        if(varaukset.isEmpty()) {
            return null;
        }
        return varaukset.get(0);    }

    @Override
    public Varaus update(Varaus varaus) throws SQLException {
              String updateQuery = "UPDATE Varaus SET asiakas_id = ?, alkuPVM=?, loppuPVM=? WHERE id = ?";
        jdbcTemplate.update(updateQuery, varaus.getAsiakas_id(), varaus.getAlkuPVM(), varaus.getLoppuPVM());
        return null;    }

    @Override
    public void delete(Integer key) throws SQLException {
        String deleteQuery = "DELETE from Varaus WHERE id = ?";
        jdbcTemplate.update(deleteQuery, key);    }

    @Override
    public List list() throws SQLException {
    
        
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
    

    @Override
    public List<Varaus> list2(Date alkuPVM, Date loppuPVM, String huonetyyppi, String hinta) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}    

