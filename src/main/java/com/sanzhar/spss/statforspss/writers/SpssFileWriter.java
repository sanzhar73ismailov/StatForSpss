package com.sanzhar.spss.statforspss.writers;

import com.sanzhar.spss.statforspss.TableInfo;

/**
 *
 * @author admin
 */
public abstract class SpssFileWriter {

    protected String xlsFile;
    protected final String syntaxFolder;
    protected final TableInfo tableInfo;

    public SpssFileWriter(String xlsFile, String syntaxFolder, TableInfo tableInfo) {
        this.syntaxFolder = syntaxFolder;
        this.xlsFile = xlsFile;
        this.tableInfo = tableInfo;
    }

    abstract void writeToFile();

}
