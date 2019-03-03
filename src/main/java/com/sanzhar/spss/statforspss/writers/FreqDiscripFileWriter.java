package com.sanzhar.spss.statforspss.writers;

import com.sanzhar.spss.statforspss.TableInfo;
import com.sanzhar.spss.statforspss.Util;
import com.sanzhar.spss.statforspss.VariableLabel;
import java.io.File;
import java.util.List;

/**
 *
 * @author admin
 */
public class FreqDiscripFileWriter extends SpssFileWriter {

    public FreqDiscripFileWriter(String xlsFile, String syntaxFolder, TableInfo tableInfo) {
        super(xlsFile, syntaxFolder, tableInfo);
    }

    private String getFrequenciesVariablesCommand() {
        final List<VariableLabel> colNamesAndComments = tableInfo.getColumnNamesAndComments();
        StringBuilder stb = new StringBuilder();
        stb.append("FREQUENCIES VARIABLES=\r\n");
        for (VariableLabel colNamesAndComment : colNamesAndComments) {
            String columnName = colNamesAndComment.getColumnName();
            if (isConvinientColumn(colNamesAndComment)) {
                stb.append(columnName).append("\r\n");
            }
        }
        stb.append("   /BARCHART=FREQ\r\n");
        stb.append("   /FORMAT=AVALUE\r\n");
        stb.append("   /ORDER=ANALYSIS.\r\n");
        return stb.toString();
    }

    @Override
    void writeToFile() {
        String file = this.syntaxFolder + File.separator + this.tableInfo.getFileQualDescrSyntaxName();
        StringBuilder stb = new StringBuilder();
        stb.append("OUTPUT NEW NAME =report_output.");
        stb.append("\r\n");
        stb.append(getFrequenciesVariablesCommand());
        stb.append("\r\n");
        stb.append(getOutputExportCommand());
        stb.append("\r\n");
        Util.writeToFile(file, stb.toString());
    }

    private boolean isConvinientColumn(VariableLabel colNamesAndComment) {
        String columnName = colNamesAndComment.getColumnName();
        if (columnName.endsWith("_id")) {
            return true;
        }
        return false;
    }

}
