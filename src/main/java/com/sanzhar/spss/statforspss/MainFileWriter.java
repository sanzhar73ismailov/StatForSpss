package com.sanzhar.spss.statforspss;

import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class MainFileWriter {

    final static Logger LOGGER = Logger.getLogger(MainFileWriter.class);
    Propers propers;
    private TableInfo[] tableInfos;

    public MainFileWriter(Propers propers) {
        this.propers = propers;
    }

    private void writeFile(TableInfo tableInfo) {
        FilesWriter filesWriter = new FilesWriter(propers.getXlsFileSrc(), propers.getFolder(), tableInfo);
        filesWriter.writeToFile();
    }

    public void writeMainFile() {

    }

    public void writeFiles() {
        for (TableInfo info : propers.getTableInfos()) {
            writeFile(info);
        }
    }
}
