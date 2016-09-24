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
import javax.faces.bean.ViewScoped;

/**
 *
 * @author morar
 */
@ManagedBean(name = "measureBean", eager = true)
@ViewScoped
public class MeasureBean implements Serializable{
    
    private int measureId;
    private String measureSign;
    private String measureName;
    
    private String errorMsg;
    
    private Measure selectedMeasure;
    
    private final static ArrayList<Measure> measures = Measure.getAllMeasures() ; 
    
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
        return measures;
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
            measures.add(m);
            return null;
        }
        msg = "Error: " + msg;
        this.setErrorMsg(msg);
        // System.out.println("Poruka(addMeasure()): >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + this.getErrorMsg());
        return null;
    }
    
    public String deleteMeasure(Measure m) throws SQLException{
        this.setErrorMsg(null);
        String msg = m.deleteRec();
        if(msg.equalsIgnoreCase("y")){
            measures.remove(m);
            return null;
        }
        msg = "Error: " + msg;
        this.setErrorMsg(msg);
        return null;
    }
    
    public String editMeasure(Measure m){
        this.setSelectedMeasure(m);
        //System.out.println("Selektovani red: " + m.getMeasureId() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        m.setCanEdit(true);
        return null;
    }
    
    public String updateMeasure() throws SQLException{
        // ako je greskom pritisnuo sacuvaj izmene
        if(this.selectedMeasure == null){
            String error = "Error: Nema izmena na zapisima(nije selektovan slog za izmenu).";
            this.setErrorMsg(error);
            return null;
        }
        this.setErrorMsg(null);
        String msg = this.selectedMeasure.updateRec();
        //System.out.println("Selected measure: " + this.selectedMeasure.getMeasureId() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if(msg.equalsIgnoreCase("y")){
            for(Measure m : measures){
                m.setCanEdit(false);
            }
            // resetuj selectedMeasure
            this.setSelectedMeasure(null);
            return null;
        }
        msg = "Error: " + msg;
        this.setErrorMsg(msg);
        return null;
    }
    
}
