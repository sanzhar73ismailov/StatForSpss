package com.sanzhar.spss.statforspss;

import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class ValueLabel {
    private String label;
    private List<KeyVal> listKeyVals;
    private boolean booleanType;

    public ValueLabel() {
        this(null);
    }

    public ValueLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<KeyVal> getListKeyVals() {
        return listKeyVals;
    }

    public void setListKeyVals(List<KeyVal> listKeyVals) {
        this.listKeyVals = listKeyVals;
    }

    public boolean isBooleanType() {
        return booleanType;
    }

    public void setBooleanType(boolean booleanType) {
        this.booleanType = booleanType;
    }

    @Override
    public String toString() {
        return "ValueLabel{" + "label=" + label + ", listKeyVals=" + listKeyVals + ", booleanType=" + booleanType + '}';
    }
    
    
}