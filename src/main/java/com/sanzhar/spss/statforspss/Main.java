package com.sanzhar.spss.statforspss;

import com.sanzhar.spss.statforspss.writers.MainFileWriter;
import com.sanzhar.spss.statforspss.writers.WorkbookWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class Main {

    final static Logger LOGGER = Logger.getLogger(Main.class);
    public final static String DATA_FOLDER = "c:\\temp\\spssTest";
    final static String SYNTAX_FOLDER = DATA_FOLDER + "\\syntax";
    public final static String REPORT_FOLDER = DATA_FOLDER + "\\reportdir";
    public final static String XLS_FILE_SRC = DATA_FOLDER + "\\data.xlsx";

    public static void main(String[] args) throws Exception {

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

        WorkbookWriter workbookWriter = new WorkbookWriter(tableInfos);
        if (1 == 0) {
            //записыаем excel file с данными  - data.xlsx
            workbookWriter.writeWorkbook();
        }

        if (1 == 1) {
            //return;
        }

        MainFileWriter mainScriptFileWriter = new MainFileWriter(propers);
        mainScriptFileWriter.writeMainFile(); //записываем главный скрипт spss
        mainScriptFileWriter.writeVariableFiles(); //записываем файлы с описанием переменных для каждой таблицы
        mainScriptFileWriter.writeDescrQuantFiles();//записываем файлы с описат. статистикой для колич. показателей
        mainScriptFileWriter.writeDescrFreqFiles();
        


        LOGGER.debug("FINISH");

    }
}
