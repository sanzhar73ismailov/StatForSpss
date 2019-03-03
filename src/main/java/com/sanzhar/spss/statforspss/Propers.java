package com.sanzhar.spss.statforspss;

/**
 *
 * @author admin
 */
public class Propers {

    private final String xlsFileSrc;
    private final String syntaxFolder;
    private final TableInfo[] tableInfos;

    public Propers(String xlsFileSrc, String folder, TableInfo[] tableInfos) {
        this.xlsFileSrc = xlsFileSrc;
        this.syntaxFolder = folder;
        this.tableInfos = tableInfos;
    }

    public String getXlsFileSrc() {
        return xlsFileSrc;
    }

    public String getSyntaxFolder() {
        return syntaxFolder;
    }

    public TableInfo[] getTableInfos() {
        return tableInfos;
    }



}
