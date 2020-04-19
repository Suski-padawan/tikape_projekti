/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration.ORDER;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Susanna Lauttia
 */


@Component
public class HuoneDao implements Dao <Huone, Integer> {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void create(Huone huone) throws SQLException {
        String addRoomQuery="INSERT INTO Huone (huoneennumero, huoneentyyppi, hinta) VALUES (?, ?, ?)";
        jdbcTemplate.update(addRoomQuery, huone.getHuoneennumero(),  huone.getHuoneentyyppi(),huone.getHinta());
    }    

    @Override
    public Huone read(Integer key) throws SQLException {
        List<Huone> huoneet = jdbcTemplate.query("SELECT * FROM Huone WHERE Huone.huoneennumero = ?",
            (rs, rowNum) -> new Huone(rs.getInt("huoneennumero"), rs.getString("huoneentyyppi"),rs.getInt("hinta")),
             key);

        if(huoneet.isEmpty()) {
            return null;
        }
        return huoneet.get(0);
    }
    @Override
    public Huone update(Huone huone) throws SQLException {
        String updateQuery = "UPDATE Huone SET huoneentyyppi = ?, hinta=? WHERE huoneennumero = ?";
        jdbcTemplate.update(updateQuery, huone.getHuoneentyyppi(), huone.getHinta(), huone.getHuoneennumero());
       
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String deleteQuery = "DELETE from Huone WHERE huoneennumero = ?";
        jdbcTemplate.update(deleteQuery, key);
    }

    @Override
    public List<Huone> list() throws SQLException {
        String listRoomQuery="SELECT * FROM Huone ORDER BY huoneennumero ASC";

        List<Huone> huoneet=jdbcTemplate.query(listRoomQuery,
            (rs, rowNum) -> new Huone(rs.getInt("huoneennumero"),
            rs.getString("huoneentyyppi"), rs.getInt("hinta"))
            );
        return huoneet;
    }

    @Override
    public List list2(Date alkuPVM, Date loppuPVM, String huonetyyppi, String hinta) throws SQLException {
       List<Huone> vapaatHuoneet=new ArrayList(); 
       
        if (huonetyyppi.equals("")&& hinta.equals("")) {
            String listRoomQuery=
            "select * from huone where huone.huoneennumero not in ( " +
            "select huone.huoneennumero from varaus "+
            "inner join huonevaraus on huonevaraus.varaus_id = varaus.id " +
            "inner join huone on huonevaraus.huone_id = huone.huoneennumero " +
            "where (varaus.loppupvm > ? and varaus.alkupvm < ?)) "; 


            vapaatHuoneet=jdbcTemplate.query(listRoomQuery,
                
            (rs, rowNum) -> new Huone(rs.getInt("huoneennumero"),
            rs.getString("huoneentyyppi"), rs.getInt("hinta")),alkuPVM, loppuPVM
            );
        } else if (huonetyyppi.equals("")){
            String listRoomQuery=                      
            "select * from huone where huone.huoneennumero not in ( " +
            "select huone.huoneennumero from varaus "+
            "inner join huonevaraus on huonevaraus.varaus_id = varaus.id " +
            "inner join huone on huonevaraus.huone_id = huone.huoneennumero " +
            "where(varaus.loppupvm > ? and varaus.alkupvm < ?)) " +
            "and huone.hinta < ? "; 

            vapaatHuoneet=jdbcTemplate.query(listRoomQuery,
                
            (rs, rowNum) -> new Huone(rs.getInt("huoneennumero"),
            rs.getString("huoneentyyppi"), rs.getInt("hinta")),alkuPVM,loppuPVM,hinta
            );
        } else if (hinta.equals("")) {
            String listRoomQuery=
            
            "select * from huone where huone.huoneennumero not in ( " +
            "select huone.huoneennumero from varaus "+
            "inner join huonevaraus on huonevaraus.varaus_id = varaus.id " +
            "inner join huone on huonevaraus.huone_id = huone.huoneennumero " +
            "where(varaus.loppupvm > ? and varaus.alkupvm < ?)) " +
            "and huone.huoneentyyppi = ?"; 

            vapaatHuoneet=jdbcTemplate.query(listRoomQuery,
                
            (rs, rowNum) -> new Huone(rs.getInt("huoneennumero"),
            rs.getString("huoneentyyppi"), rs.getInt("hinta")),alkuPVM,loppuPVM,huonetyyppi
            );
        } else {
            String listRoomQuery=

            "select * from huone where huone.huoneennumero not in ( " +
            "select huone.huoneennumero from varaus "+
            "inner join huonevaraus on huonevaraus.varaus_id = varaus.id " +
            "inner join huone on huonevaraus.huone_id = huone.huoneennumero " +
            "where(varaus.loppupvm > ? and varaus.alkupvm < ?)) "+
            "and huone.hinta < ? " +
            "and huone.huoneentyyppi = ?"; 
 

            vapaatHuoneet=jdbcTemplate.query(listRoomQuery,
                
            (rs, rowNum) -> new Huone(rs.getInt("huoneennumero"),
            rs.getString("huoneentyyppi"), rs.getInt("hinta")),alkuPVM,loppuPVM,huonetyyppi,hinta
            );
        }
        return vapaatHuoneet;
    }  

    
    
}
