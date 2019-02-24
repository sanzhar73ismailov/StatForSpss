package com.sanzhar.spss.statforspss;

/**
 *
 * @author admin
 */
public class Propers {

    private String xlsFileSrc;
    private String folder;
    private TableInfo[] tableInfos;

    public Propers(String xlsFileSrc, String folder, TableInfo[] tableInfos) {
        this.xlsFileSrc = xlsFileSrc;
        this.folder = folder;
        this.tableInfos = tableInfos;
    }

    public String getXlsFileSrc() {
        return xlsFileSrc;
    }

    public String getFolder() {
        return folder;
    }

    public TableInfo[] getTableInfos() {
        return tableInfos;
    }



}
