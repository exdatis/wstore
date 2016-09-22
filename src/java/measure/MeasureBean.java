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
        if(m.insertRec()){
            MEASURES.add(m);
            return null;
        }
        return "measure";
    }
    
    public String deleteMeasure(Measure m) throws SQLException{
        if(m.deleteRec()){
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
        if(this.selectedMeasure.updateRec()){
            for(Measure m : MEASURES){
                m.setCanEdit(false);
            }
            return null;
        }
        return "measure";
    }
    
}
