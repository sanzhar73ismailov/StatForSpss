package com.sanzhar.spss.statforspss;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class VariableLabel {

    private String columnName;
    private String columnComment;
    private boolean booleanType;
    private String dbDataType; //int, date, double, varchar, text, timestamp

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

    public String getDbDataType() {
        return dbDataType;
    }

    public void setDbDataType(String dbDataType) {
        this.dbDataType = dbDataType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableLabel other = (VariableLabel) obj;
        if (this.booleanType != other.booleanType) {
            return false;
        }
        if (!Objects.equals(this.columnName, other.columnName)) {
            return false;
        }
        if (!Objects.equals(this.columnComment, other.columnComment)) {
            return false;
        }
        return true;
    }

}
