/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdb;

import java.sql.SQLException;

/**
 * Za standardne Java klase koje se bave podacima(baza podataka).
 * @author morar
 */
public interface CRUDrecord {
    
    /**
     * Sacuvaj novi zapis
     * @return (poruka o gresci ili uspehu)
     * @throws java.sql.SQLException
     */
    public String insertRec() throws SQLException;
    
    /**
     * Sacuvaj izmene na zapisu.
     * @return (poruka o gresci ili uspehu)
     * @throws java.sql.SQLException
     */
    public String updateRec() throws SQLException;
    
    /**
     * Brisanje zapisa.
     * @return (poruka o gresci ili uspehu)
     * @throws java.sql.SQLException
     */
    public String deleteRec() throws SQLException;
    
}
