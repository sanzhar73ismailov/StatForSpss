package com.sanzhar.spss.statforspss;

import java.io.*;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class FilesWriter {

    final static Logger LOGGER = Logger.getLogger(FilesWriter.class);
    private final String syntaxFolder;
    private final TableInfo tableInfo;
    private final String xlsFile;

    public FilesWriter(String xlsFile, String folder, TableInfo info) {
        this.xlsFile = xlsFile;
        this.syntaxFolder = folder;
        this.tableInfo = info;
    }

    public String getGetDataCommand() {
        String tabName = tableInfo.getTable(); // tabName is the same as table name in DB
        String command = String.format("GET DATA /TYPE=XLSX \r\n"
                + "  /FILE='%s' \r\n"
                + "  /SHEET=name '%s' \r\n"
                + "  /CELLRANGE=full \r\n"
                + "  /READNAMES=on \r\n"
                + "  /ASSUMEDSTRWIDTH=32767. \r\n"
                + "DATASET NAME %s WINDOW=FRONT.\r\n"
                + "DATASET ACTIVATE %s.\r\n", xlsFile, tabName, tabName, tabName);

        return command;
    }

    public String getVarLabelCommand() {
        final List<VariableLabel> colNamesAndComments = tableInfo.getColumnNamesAndComments();
        StringBuilder stb = new StringBuilder("VARIABLE LABELS\r\n");
        for (VariableLabel colNamesAndComment : colNamesAndComments) {
            String comment = colNamesAndComment.getColumnComment();
            if (colNamesAndComment.getColumnComment() == null || colNamesAndComment.getColumnComment().trim().isEmpty()) {
                comment = colNamesAndComment.getColumnName();
            }
            stb.append(colNamesAndComment.getColumnName()).append(" \"");
            stb.append(comment).append("\"\r\n");
        }
        stb.append(".\r\n");
        //LOGGER.debug("stb = " + stb);
        return stb.toString();
    }

    public String getValueLabelCommand() {
        final List<ValueLabel> valLabels = tableInfo.getValueLabels();
        StringBuilder stb = new StringBuilder("VARIABLE LABELS\r\n");
        //add boolean types
        for (ValueLabel valueLabel : valLabels) {
            if (!valueLabel.isBooleanType()) {
                continue;
            }
            stb.append(valueLabel.getLabel()).append("\r\n");
        }
        stb.append("1 \"Да\"\r\n");
        stb.append("0 \"Нет\"\r\n");
        stb.append("-1 \"Нет данных\"\r\n");

        //add other multitypes
        for (ValueLabel valLabel : valLabels) {
            if (valLabel.isBooleanType()) {
                continue;
            }
            stb.append("/").append(valLabel.getLabel()).append("\r\n");
            for (KeyVal keyVal : valLabel.getListKeyVals()) {
                stb.append(keyVal.getKey()).append(" ").append("\"");
                stb.append(keyVal.getValue()).append("\"").append("\r\n");
            }
            stb.append("-1 \"Нет данных\"\r\n");
        }
        stb.append(".\r\n");

        //LOGGER.debug("stb = " + stb);
        return stb.toString();
    }

    public String getMissingVals() {
        final List<VariableLabel> colNamesAndComments = tableInfo.getColumnNamesAndComments();
        StringBuilder stb = new StringBuilder("MISSING VALUES\r\n");
        for (VariableLabel colNamesAndComment : colNamesAndComments) {
            if (colNamesAndComment.getColumnName().endsWith("_id")) {
                stb.append(colNamesAndComment.getColumnName()).append("\r\n");
            }
        }
        stb.append("(-1).\r\n");
        //LOGGER.debug("stb = " + stb);
        return stb.toString();
    }

    public void writeToFile() {
       
        String file = this.syntaxFolder + File.separator + this.tableInfo.getFileSyntaxName();
        StringBuilder stb = new StringBuilder();
        stb.append(getGetDataCommand());
        stb.append("\r\n");
        stb.append(getVarLabelCommand());
        stb.append("\r\n");
        stb.append(getValueLabelCommand());
        stb.append("\r\n");
        stb.append(getMissingVals());
        Util.writeToFile(file, stb.toString());
    }
}
