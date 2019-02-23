package com.sanzhar.spss.statforspss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class FilesWriter {

    final static Logger LOGGER = Logger.getLogger(FilesWriter.class);
    private String folder;
    private TableInfo info;

    public FilesWriter(String folder, TableInfo info) {
        this.folder = folder;
        this.info = info;
    }

    public String getVarLabelCommand() {
        final List<VariableLabel> colNamesAndComments = info.getColumnNamesAndComments();
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
        final List<ValueLabel> valLabels = info.getValueLabels();
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
        final List<VariableLabel> colNamesAndComments = info.getColumnNamesAndComments();
        StringBuilder stb = new StringBuilder("MISSING VALUES\r\n");
        for (VariableLabel colNamesAndComment : colNamesAndComments) {
            stb.append(colNamesAndComment.getColumnName()).append("\r\n");
        }
        stb.append("(-1).\r\n");
        //LOGGER.debug("stb = " + stb);
        return stb.toString();
    }
    
    public void writeToFile() {

        String file = this.folder + File.separator + this.info.getTable() + ".spss";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            StringBuilder stb = new StringBuilder();
            stb.append(getVarLabelCommand());
            stb.append("\r\n");
            stb.append(getValueLabelCommand());
            stb.append("\r\n");
            stb.append(getMissingVals());
            //String content = getVarLabelCommand();

            bw.write(stb.toString());

            // no need to close it.
            //bw.close();
            LOGGER.debug("Done");
        } catch (IOException e) {
            LOGGER.error("error", e);
        }

    }
    private static final String FILENAME = "c:\\temp\\filename.txt";

    public static void main(String[] args) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {

            String content = "This is the content to write into file\n";

            bw.write(content);

            // no need to close it.
            //bw.close();
            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
