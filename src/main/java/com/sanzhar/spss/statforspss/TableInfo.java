package com.sanzhar.spss.statforspss;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class TableInfo {

    private String table;
    private List<VariableLabel> columnNamesAndComments;
    private List<ValueLabel> valueLabels;

    public TableInfo(String table) {
        this.table = table;
        init();
    }

    final void init() {
        try {
            columnNamesAndComments = DbWork.getColumnNamesAndComments(table);
            valueLabels = DbWork.getValueLabels(table);
        } catch (SQLException ex) {
            Logger.getLogger(TableInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<VariableLabel> getColumnNamesAndComments() {
        return columnNamesAndComments;
    }

    public void setColumnNamesAndComments(List<VariableLabel> columnNamesAndComments) {
        this.columnNamesAndComments = columnNamesAndComments;
    }

    public List<ValueLabel> getValueLabels() {
        return valueLabels;
    }

    public void setValueLabels(List<ValueLabel> valueLabels) {
        this.valueLabels = valueLabels;
    }

}
