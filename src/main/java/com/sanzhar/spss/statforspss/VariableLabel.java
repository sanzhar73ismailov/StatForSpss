package com.sanzhar.spss.statforspss;

/**
 *
 * @author admin
 */
public class VariableLabel {
    private String columnName;
    private String columnComment;
    private boolean booleanType;

    @Override
    public String toString() {
        return "VariableLabel{" + "columnName=" + columnName + ", columnComment=" + columnComment + ", booleanType=" + booleanType + '}';
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public boolean isBooleanType() {
        return booleanType;
    }

    public void setBooleanType(boolean booleanType) {
        this.booleanType = booleanType;
    }
    
    

    
    
}
