package com.sanzhar.spss.statforspss;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TableData {

    TableInfo tableInfo;
    List<List> values = new ArrayList<>();

    public TableData(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<List> getValues() {
        return values;
    }

    public void setValues(List<List> values) {
        this.values = values;
    }
    
    
}
