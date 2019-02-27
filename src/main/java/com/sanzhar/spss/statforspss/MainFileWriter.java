package com.sanzhar.spss.statforspss;

import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class MainFileWriter {

    final static Logger LOGGER = Logger.getLogger(MainFileWriter.class);
    Propers propers;
    private final TableInfo[] tableInfos;

    public MainFileWriter(Propers propers) {
        this.propers = propers;
        this.tableInfos = propers.getTableInfos();
        File syntaxFolderFile = new File(propers.getFolder());
        if(!syntaxFolderFile.exists()){
            syntaxFolderFile.mkdir();
        }
    }

    private void writeFile(TableInfo tableInfo) {
        FilesWriter filesWriter = new FilesWriter(propers.getXlsFileSrc(), propers.getFolder(), tableInfo);
        filesWriter.writeToFile();
    }

    public void writeMainFile() {
        StringBuilder stb = new StringBuilder();
        stb.append("CD '").append(Main.DATA_FOLDER).append("'.\r\n");
        for (TableInfo tableInfo : tableInfos) {
            stb.append("INSERT FILE = 'syntax\\").append(tableInfo.getFileSyntaxName()).append("'.\r\n");
        }
        Util.writeToFile(propers.getFolder() + "\\00_script_general.sps", stb.toString());
    }

    public void writeFiles() {
        for (TableInfo tableInfo : tableInfos) {
            writeFile(tableInfo);
        }
    }
}
