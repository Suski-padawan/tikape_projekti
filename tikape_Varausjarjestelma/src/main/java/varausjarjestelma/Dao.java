/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varausjarjestelma;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Susanna Lauttia
 */
public interface Dao <T,K> {
    void create(T object) throws SQLException;
    
    T read(K key) throws SQLException;

    T update(T object) throws SQLException;

    void delete(K key) throws SQLException;

    List<T> list() throws SQLException;
    
    List<T> list2(Date alkuPVM, Date loppuPVM, String huonetyyppi, String hinta) throws SQLException;

}
