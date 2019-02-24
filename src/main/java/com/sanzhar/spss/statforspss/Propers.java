package com.sanzhar.spss.statforspss;

/**
 *
 * @author admin
 */
public class Propers {

    private final String xlsFileSrc;
    private final String folder;
    private final TableInfo[] tableInfos;

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
