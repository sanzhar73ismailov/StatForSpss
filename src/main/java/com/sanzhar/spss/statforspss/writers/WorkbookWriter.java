package com.sanzhar.spss.statforspss.writers;

import com.sanzhar.spss.statforspss.DbWork;
import com.sanzhar.spss.statforspss.Main;
import com.sanzhar.spss.statforspss.TableInfo;
import com.sanzhar.spss.statforspss.VariableLabel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin
 */
public class WorkbookWriter {

    private TableInfo[] tableInfos;

    public WorkbookWriter(TableInfo[] tableInfos) {
        this.tableInfos = tableInfos;
    }

    public void writeWorkbook() throws Exception {

        Workbook workbook = new XSSFWorkbook();

        for (TableInfo tableInfo : tableInfos) {

            Sheet sheet = workbook.createSheet(tableInfo.getTable());

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            final List<VariableLabel> columnNamesAndComments = tableInfo.getColumnNamesAndComments();
            for (int i = 0; i < columnNamesAndComments.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNamesAndComments.get(i).getColumnName());
                cell.setCellStyle(headerCellStyle);
            }

            writeSheet(tableInfo, sheet);
        }
        FileOutputStream fileOut = new FileOutputStream(Main.XLS_FILE_SRC);
        workbook.write(fileOut);
        fileOut.close();

    }

    public void writeSheet(TableInfo tableInfo, Sheet sheet) throws Exception {
        //final List<List> data = DbWork.getDataFromTable("ovarian_general_data");
        final List<List> data = DbWork.getDataFromTable(tableInfo.getTable());
        // Create Other rows and cells with contacts data
        int rowNum = 1;
        //System.out.println("data = " + data);
        final List<VariableLabel> columnNamesAndComments = tableInfo.getColumnNamesAndComments();
        for (int i = 0; i < data.size(); i++) {
            List recordFromDb = data.get(i);
            Row row = sheet.createRow(i + 1);

            int column = 0;
            for (Object value : recordFromDb) {
                final VariableLabel variableLabel = columnNamesAndComments.get(column);
                //System.out.print("<" + cell + ">");
                String valAsString = "";
                if (value != null) {
                    valAsString = value.toString();
                }
                String dbType = variableLabel.getDbDataType();
                if (dbType.equals("int") || dbType.equals("double")) {
                    //cell.setCellType(CellType.NUMERIC);
                    if (valAsString.trim().isEmpty()) {
                        valAsString = "-1";
                    }
                    row.createCell(column++, CellType.NUMERIC).setCellValue(Double.parseDouble(valAsString));
                    if (dbType.equals("date")) {
                        valAsString = formatDate(valAsString);
                    } else if (dbType.equals("timestamp")) {
                        valAsString = formatDate(valAsString);
                    }
                    row.createCell(column++, CellType.NUMERIC).setCellValue(valAsString);
                }
            }
            //System.out.println("--------- = ");
        }

    }

    /**
     * @param mysqlDate - String with format yyyy-MM-dd
     * @return - String with format dd.MM.yyyy
     * @throws ParseException
     */
    static String formatDate(String mysqlDate) throws ParseException {
        if (mysqlDate == null || mysqlDate.trim().isEmpty()) {
            return "";
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(mysqlDate);
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    /**
     *
     * @param mysqlTimeStamp - String with format yyyy-MM-dd hh:mm:ss
     * @return - String with format dd.MM.yyyy
     * @throws ParseException
     */
    static String formatTimeStamp(String mysqlTimeStamp) throws ParseException {
        //2017-04-02 05:33:11.0
        if (mysqlTimeStamp == null || mysqlTimeStamp.trim().isEmpty()) {
            return "";
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(mysqlTimeStamp);
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

}
