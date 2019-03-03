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

    protected String getOutputExportCommand() {
        return "OUTPUT EXPORT\n"
                + "  /CONTENTS EXPORT=VISIBLE  LAYERS=PRINTSETTING  MODELVIEWS=PRINTSETTING\r\n"
                + "  /XLS  DOCUMENTFILE=xls_file\r\n"
                + "     OPERATION=CREATEFILE\r\n"
                + "     LOCATION=LASTCOLUMN  NOTESCAPTIONS=YES.\r\n"
                + "OUTPUT CLOSE NAME =report_output.\r\n";
    }

}
