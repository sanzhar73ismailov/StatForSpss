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
    private final int number;
    private final String fileSyntaxName;
    private final String fileQuantDescrSyntaxName;
    private final String fileQualDescrSyntaxName;


    public TableInfo(String table, int number) {
        this.table = table;
        this.number = number;
        
        String baseName = String.format("%02d", number) + "_" + table;
        
        this.fileSyntaxName = baseName + ".sps"; 
        this.fileQuantDescrSyntaxName = baseName + "_quant.sps"; 
        this.fileQualDescrSyntaxName = baseName + "_qual.sps"; 
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

    public int getNumber() {
        return number;
    }

    public String getFileSyntaxName() {
        return fileSyntaxName;
    }

    public String getFileQuantDescrSyntaxName() {
        return fileQuantDescrSyntaxName;
    }

    public String getFileQualDescrSyntaxName() {
        return fileQualDescrSyntaxName;
    }

}
