package com.sanzhar.spss.statforspss;

import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class Main {

    final static Logger LOGGER = Logger.getLogger(Main.class);
    final static String DATA_FOLDER = "c:\\temp\\spssTest";
    final static String SYNTAX_FOLDER = DATA_FOLDER + "\\syntax";
    final static String XLS_FILE_SRC = DATA_FOLDER + "\\data.xls";

    public static void main(String[] args) {

        LOGGER.debug("START");

        String[] tables = {
            "ovarian_general_data",
            "ovarian_instrument"
        };
        TableInfo[] tableInfos = new TableInfo[tables.length];
        for (int i = 0; i < tableInfos.length; i++) {
            tableInfos[i] = new TableInfo(tables[i], i + 1);
        }

        Propers propers = new Propers(XLS_FILE_SRC, SYNTAX_FOLDER, tableInfos);

        MainFileWriter mainFileWriter = new MainFileWriter(propers);
        mainFileWriter.writeMainFile();
        mainFileWriter.writeFiles();

        // final TableInfo tableInfo = new TableInfo("ovarian_general_data");
        // final List<VariableLabel> columnNamesAndComments = tableInfo.getColumnNamesAndComments();
        //System.out.println("columnNamesAndComments = " + columnNamesAndComments);
        // final List<ValueLabel> valueLabels = tableInfo.getValueLabels();
        //System.out.println("valueLabels = " + valueLabels);
        //System.out.println("str = " + str);
        LOGGER.debug("FINISH");

    }
}
