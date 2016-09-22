/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package measure;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author morar
 */
@ManagedBean(name = "measureBean", eager = true)
@SessionScoped
public class MeasureBean implements Serializable{
    
    private int measureId;
    private String measureSign;
    private String measureName;
    
    private Measure selectedMeasure;
    
    private static ArrayList<Measure> MEASURES = new ArrayList<>(Arrays.asList(new Measure())); 
    
    // ovo treba prilagoditi
    private final String USER_NAME = "root";
    private final String USER_PWD = "JDevelopment013";
    private final String DB_HOST = "localhost";
    private final String DB_PORT = "3306";
    private final String DB_NAME = "wstore"; // ovo treba da bude dostupno kao izbor

    /**
     * Creates a new instance of MeasureBean
     */
    public MeasureBean() {
        
    }
    

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public String getMeasureSign() {
        return measureSign;
    }

    public void setMeasureSign(String measureSign) {
        this.measureSign = measureSign;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public Measure getSelectedMeasure() {
        return selectedMeasure;
    }

    public void setSelectedMeasure(Measure selectedMeasure) {
        this.selectedMeasure = selectedMeasure;
    }
    
    public ArrayList<Measure> getMesures(){
        return MEASURES;
    }
    
    public void resetValues(){
        this.measureId = 0;
        this.measureSign = null;
        this.measureName = null;
    }
    
    public String addMeasure() throws SQLException{
        Measure m = new Measure(measureSign, measureName);
        if(insertRec(m)){
            MEASURES.add(m);
            return null;
        }
        return "measure";
    }
    
    public String deleteMeasure(Measure m) throws SQLException{
        if(deleteRec(m)){
            MEASURES.remove(m);
            return null;
        }
        return "measure";
    }
    
    public String editMeasure(Measure m){
        m.setCanEdit(true);
        this.setSelectedMeasure(m);
        return null;
    }
    
    public String updateMeasure() throws SQLException{
        if(updateRec(this.selectedMeasure)){
            for(Measure m : MEASURES){
                m.setCanEdit(false);
            }
            return null;
        }
        return "measure";
    }
    
    public boolean insertRec(Measure m) throws SQLException {
        boolean success = false;
        Connection conn = null;
        try {
            conn = this.getDbConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return success;
        }

        int new_id = 0;
        String sql = "{call measure_add(?, ?, ?)}";
        try (CallableStatement cst = conn.prepareCall(sql);) {
            cst.setString(1, m.getMeasureSign());
            cst.setString(2, m.getMeasureName());
            cst.registerOutParameter(3, java.sql.Types.INTEGER);
            success = cst.execute(); // neznam zasto ali mi ovo nije promenilo vrednost success?
            new_id = cst.getInt(3);
            m.setMeasureId(new_id);
        } catch (SQLException ex) {
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, ex);
            return success;
        }
        // close connection
        if (conn != null) {
            conn.close();
        }

        success = true;
        return success;
    }

    public boolean updateRec(Measure m) throws SQLException {
        boolean success = false;
        Connection conn = null;
        try {
            conn = this.getDbConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return success;
        }

        String sql = "{call measure_update(?, ?, ?)}";
        try (CallableStatement cst = conn.prepareCall(sql);) {
            cst.setInt(1, m.getMeasureId());
            cst.setString(2, m.getMeasureSign());
            cst.setString(3, m.getMeasureName());
            success = cst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, ex);
            return success;
        }

        // close connection
        if (conn != null) {
            conn.close();
        }

        success = true;
        return success;
    }

    public boolean deleteRec(Measure m) throws SQLException {
        boolean success = false;
        Connection conn = null;
        try {
            conn = this.getDbConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return success;
        }

        String sql = "{call measure_delete(?)}";
        try (CallableStatement cst = conn.prepareCall(sql);) {
            cst.setInt(1, m.getMeasureId());
            success = cst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, ex);
            return success;
        }

        // close connection
        if (conn != null) {
            conn.close();
        }

        success = true;
        return success;
    }

    public ArrayList<Measure> getAllMeasures() {
        ArrayList<Measure> measures = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getDbConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return measures;
        }
        String sql = "Select * From measure";
        boolean added = false;
        try (PreparedStatement pst = conn.prepareStatement(sql);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Measure m = new Measure();
                m.setMeasureId(rs.getInt(1));
                m.setMeasureSign(rs.getString(2));
                m.setMeasureName(rs.getString(3));
                added = measures.add(m);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, ex);
            return measures;
        }

        return measures;

    }
    public Connection getDbConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver ());// zbog ove linije koda(dok nisam pronasao resenje na stackoverflow
                                                                   // izgubio sam pola dana. Molim vas, za neke buduce generacije uvedite
                                                                   // ovo objasnjenje u materijal(pdf). Ja zbog vremena nisam bio u mogucnosti
                                                                   // da odslusam sva vasa predavanja, mozda ste vi i pomenuli ovo, neznam 
                                                                   // ali u video-materijalu (kratka verzija) ovog objasnjenja nema.
        Connection conn = null;
        String url = "jdbc:mysql://"+ DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        //System.out.println(url + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
            conn = DriverManager.getConnection(url, USER_NAME , USER_PWD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //System.out.println("Nema konekcije >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return conn;
        }
        //System.out.println("IMA KONEKCIJU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return conn;
    }
    
}
