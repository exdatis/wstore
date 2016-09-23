/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package measure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import wdb.CRUDrecord;
import wdb.Wdb;


/**
 *
 * @author morar
 */
public class Measure implements CRUDrecord{
    
    private int measureId;
    private String measureSign;
    private String measureName;
    private boolean canEdit;

    public Measure() {
        this.canEdit = false;
    }

    public Measure(String measureName) {
        this.measureName = measureName;
        this.canEdit = false;
    }

    public Measure(String measureSign, String measureName) {
        this.measureSign = measureSign;
        this.measureName = measureName;
        this.canEdit = false;
    }

    public Measure(int measureId, String measureSign, String measureName) {
        this.measureId = measureId;
        this.measureSign = measureSign;
        this.measureName = measureName;
        this.canEdit = false;
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

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }   

    @Override
    public String insertRec() throws SQLException {
        String success = "n"; // error
        Connection conn = null;
        try {
            conn = Wdb.getDbConnection();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            return msg;
        }

        int new_id = 0;
        String sql = "{call measure_add(?, ?, ?)}";
        try (CallableStatement cst = conn.prepareCall(sql);) {
            cst.setString(1, this.getMeasureSign());
            cst.setString(2, this.getMeasureName());
            cst.registerOutParameter(3, java.sql.Types.INTEGER);
            boolean added = cst.execute(); // neznam zasto ali mi ovo nije promenilo vrednost success?
            new_id = cst.getInt(3);
            this.setMeasureId(new_id);
        } catch (SQLException ex) {
            String sqlError = ex.getMessage();
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, sqlError);
            return sqlError;
        }
        // close connection
        if (conn != null) {
            conn.close();
        }
        success = "y"; // ok
        return success;
    }

    @Override
    public String updateRec() throws SQLException {
        String success = "n"; // error
        Connection conn = null;
        try {
            conn = Wdb.getDbConnection();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            return msg;
        }

        String sql = "{call measure_update(?, ?, ?)}";
        try (CallableStatement cst = conn.prepareCall(sql);) {
            cst.setInt(1, this.getMeasureId());
            cst.setString(2, this.getMeasureSign());
            cst.setString(3, this.getMeasureName());
            boolean updated = cst.execute();
        } catch (SQLException ex) {
            String sqlError = ex.getMessage();
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, sqlError);
            return sqlError;
        }

        // close connection
        if (conn != null) {
            conn.close();
        }
        success = "y"; // ok
        return success;
    }

    @Override
    public String deleteRec() throws SQLException {
        String success = "y";
        Connection conn = null;
        try {
            conn = Wdb.getDbConnection();
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            return msg;
        }

        String sql = "{call measure_delete(?)}";
        try (CallableStatement cst = conn.prepareCall(sql);) {
            cst.setInt(1, this.getMeasureId());
            boolean deleted = cst.execute();
        } catch (SQLException ex) {
            String sqlError = ex.getMessage();
            Logger.getLogger(Measure.class.getName()).log(Level.SEVERE, null, sqlError);
            return sqlError;
        }

        // close connection
        if (conn != null) {
            conn.close();
        }
        
        success = "y"; // ok
        return success;
    }

    public static ArrayList<Measure> getAllMeasures() {
        ArrayList<Measure> measures = new ArrayList<>();
        Connection conn = null;
        try {
            conn = Wdb.getDbConnection();
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
    
}
