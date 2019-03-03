package com.sanzhar.spss.statforspss.writers;

import com.sanzhar.spss.statforspss.Main;
import com.sanzhar.spss.statforspss.Propers;
import com.sanzhar.spss.statforspss.TableInfo;
import com.sanzhar.spss.statforspss.Util;
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
        File syntaxFolderFile = new File(propers.getSyntaxFolder());
        if(!syntaxFolderFile.exists()){
            syntaxFolderFile.mkdir();
        }
        File reportFolder = new File(Main.REPORT_FOLDER);
        if(!reportFolder.exists()){
            reportFolder.mkdir();
        }
        
    }

    private void writeVariableFile(TableInfo tableInfo) {
        SpssFileWriter filesWriter = new VariableFilesWriter(propers.getXlsFileSrc(), propers.getSyntaxFolder(), tableInfo);
        filesWriter.writeToFile();
    }
    
    private void writeQuantFile(TableInfo tableInfo) {
        SpssFileWriter filesWriter = new QuantDiscripFileWriter(propers.getXlsFileSrc(), propers.getSyntaxFolder(), tableInfo);
        filesWriter.writeToFile();
    }
    
    private void writeFreqFile(TableInfo tableInfo) {
        SpssFileWriter filesWriter = new FreqDiscripFileWriter(propers.getXlsFileSrc(), propers.getSyntaxFolder(), tableInfo);
        filesWriter.writeToFile();
    }

    public void writeMainFile() {
        StringBuilder stb = new StringBuilder();
        stb.append("CD '").append(Main.DATA_FOLDER).append("'.\r\n");
        for (TableInfo tableInfo : tableInfos) {
            stb.append("INSERT FILE = 'syntax\\").append(tableInfo.getFileSyntaxName()).append("'.\r\n");
        }
        for (TableInfo tableInfo : tableInfos) {
            //DATASET ACTIVATE PatientData.
            stb.append("\r\n");
            stb.append("DATASET ACTIVATE ").append(tableInfo.getTable()).append(".\r\n");
            //FILE HANDLE xls_file /NAME='reportdir\02_пациенты_частоты.xls'.
            String reportFile = "reportdir\\" + tableInfo.getTable() + "_quant.xls";
            stb.append(String.format("FILE HANDLE xls_file /NAME='%s'.\r\n", reportFile));
            stb.append("INSERT FILE = 'syntax\\").append(tableInfo.getFileQuantDescrSyntaxName()).append("'.\r\n");
        }
        for (TableInfo tableInfo : tableInfos) {
            stb.append("\r\n");
            String reportFile = "reportdir\\" + tableInfo.getTable() + "_qual.xls";
            stb.append(String.format("FILE HANDLE xls_file /NAME='%s'.\r\n", reportFile));
            stb.append("DATASET ACTIVATE ").append(tableInfo.getTable()).append(".\r\n");
            stb.append("INSERT FILE = 'syntax\\").append(tableInfo.getFileQualDescrSyntaxName()).append("'.\r\n");
        }
            
        Util.writeToFile(propers.getSyntaxFolder() + "\\00_script_general.sps", stb.toString());
    }

    public void writeVariableFiles() {
        for (TableInfo tableInfo : tableInfos) {
            writeVariableFile(tableInfo);
        }
    }
    
    public void writeDescrQuantFiles() {
        for (TableInfo tableInfo : tableInfos) {
            writeQuantFile(tableInfo);
        }
    }
    
    public void writeDescrFreqFiles() {
        for (TableInfo tableInfo : tableInfos) {
            writeFreqFile(tableInfo);
        }
    }
}
