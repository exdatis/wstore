/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package measure;


/**
 *
 * @author morar
 */
public class Measure {
    
    private int measureId;
    private String measureSign;
    private String measureName;
    private boolean canEdit;

    public Measure() {
    }

    public Measure(String measureName) {
        this.measureName = measureName;
    }

    public Measure(String measureSign, String measureName) {
        this.measureSign = measureSign;
        this.measureName = measureName;
    }

    public Measure(int measureId, String measureSign, String measureName) {
        this.measureId = measureId;
        this.measureSign = measureSign;
        this.measureName = measureName;
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
}
