/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package measure;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    private String errorMsg;
    
    private Measure selectedMeasure;
    
    private final static ArrayList<Measure> MEASURES = Measure.getAllMeasures() ; 
    
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
        this.setErrorMsg(null);
    }
    
    public String addMeasure() throws SQLException{
        this.setErrorMsg(null);
        Measure m = new Measure(measureSign, measureName);
        String msg = m.insertRec();
        if(msg.equalsIgnoreCase("y")){
            MEASURES.add(m);
            return null;
        }
        this.setErrorMsg(msg);
        // System.out.println("Poruka(addMeasure()): >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + this.getErrorMsg());
        return null;
    }
    
    public String deleteMeasure(Measure m) throws SQLException{
        this.setErrorMsg(null);
        String msg = m.deleteRec();
        if(msg.equalsIgnoreCase("y")){
            MEASURES.remove(m);
            return null;
        }
        this.setErrorMsg(msg);
        return null;
    }
    
    public String editMeasure(Measure m){
        m.setCanEdit(true);
        this.setSelectedMeasure(m);
        return null;
    }
    
    public String updateMeasure() throws SQLException{
        this.setErrorMsg(null);
        String msg = this.selectedMeasure.updateRec();
        if(msg.equalsIgnoreCase("y")){
            for(Measure m : MEASURES){
                m.setCanEdit(false);
            }
            return null;
        }
        this.setErrorMsg(msg);
        return null;
    }
    
}
