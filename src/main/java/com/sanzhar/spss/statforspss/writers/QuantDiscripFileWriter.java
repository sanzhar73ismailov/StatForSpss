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
public class QuantDiscripFileWriter extends SpssFileWriter {

    public QuantDiscripFileWriter(String xlsFile, String syntaxFolder, TableInfo tableInfo) {
        super(xlsFile, syntaxFolder, tableInfo);
    }

    private String getDescriptiveVarCommand() {
        final List<VariableLabel> colNamesAndComments = tableInfo.getColumnNamesAndComments();
        StringBuilder stb = new StringBuilder();
        stb.append("DESCRIPTIVES VARIABLES=\r\n");
        for (VariableLabel colNamesAndComment : colNamesAndComments) {
            String columnName = colNamesAndComment.getColumnName();
            if (isConvinientColumn(colNamesAndComment)) {
                stb.append("     ").append(columnName).append("\r\n");
            }
        }
        stb.append("  /STATISTICS=MEAN STDDEV MIN MAX SEMEAN.\r\n");
        return stb.toString();
    }

    private String getKolmogSmirnovCommand() {
        final List<VariableLabel> colNamesAndComments = tableInfo.getColumnNamesAndComments();
        StringBuilder stb = new StringBuilder();
        stb.append("NPAR TESTS\r\n");
        stb.append("  /K-S(NORMAL)=\r\n");
        for (VariableLabel colNamesAndComment : colNamesAndComments) {
            String columnName = colNamesAndComment.getColumnName();
            if (isConvinientColumn(colNamesAndComment)) {
                stb.append("     ").append(columnName).append("\r\n");
            }
        }
        stb.append("  /MISSING ANALYSIS.\r\n");
        return stb.toString();
    }

    

    @Override
    void writeToFile() {
        String file = this.syntaxFolder + File.separator + this.tableInfo.getFileQuantDescrSyntaxName();
        StringBuilder stb = new StringBuilder();
        stb.append("OUTPUT NEW NAME =report_output.");
        stb.append("\r\n");
        stb.append(getDescriptiveVarCommand());
        stb.append("\r\n");
        stb.append(getKolmogSmirnovCommand());
        stb.append("\r\n");
        stb.append(getOutputExportCommand());
        stb.append("\r\n");
        Util.writeToFile(file, stb.toString());
    }

    boolean idNumericColumn(String dbType) {
        return dbType.equals("int")
                || dbType.equals("double");
    }

    private boolean isConvinientColumn(VariableLabel colNamesAndComment) {
        String columnName = colNamesAndComment.getColumnName();
        String dbType = colNamesAndComment.getDbDataType();
        boolean numericColumn = dbType.equals("int") || dbType.equals("double");
        if (numericColumn && !columnName.endsWith("_id") && !columnName.equals("id")) {
            return true;
        }
        return false;
    }

}
