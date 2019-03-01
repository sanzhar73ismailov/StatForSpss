package com.sanzhar.spss.statforspss;

import com.sanzhar.spss.statforspss.excelwrite.examples.Contact;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
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

    public void writeSheet(TableInfo tableInfo, Sheet sheet) throws SQLException {
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
//                if(value != null){
//                   row.createCell(column++).setCellValue(value.toString());
//                }else{
//                    row.createCell(column++).setCellValue("");
//                }
                //Cell cell = row.createCell(column++);
                String dbType = variableLabel.getDbDataType();
                if (dbType.equals("int") || dbType.equals("double")) {
                    //cell.setCellType(CellType.NUMERIC);
                    if(valAsString.trim().isEmpty()){
                        valAsString = "-1";
                    }
                    row.createCell(column++, CellType.NUMERIC).setCellValue(Double.parseDouble(valAsString));
                    //row.createCell(column++, CellType.NUMERIC).setCellValue(valAsString);

                } else {
                    //cell.setCellType(CellType.STRING
                    //       cell.setCellValue(valAsString);
                    row.createCell(column++, CellType.NUMERIC).setCellValue(valAsString);
                }

            }
        }
        //System.out.println("--------- = ");
    }

    public void mainTemp(String[] args) throws IOException, InvalidFormatException {
        /*
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contacts");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with contacts data
        int rowNum = 1;

        for (Contact contact : contacts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contact.firstName);
            row.createCell(1).setCellValue(contact.lastName);
            row.createCell(2).setCellValue(contact.email);
            row.createCell(3).setCellValue(contact.dateOfBirth);
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("contacts.xlsx");
        workbook.write(fileOut);
        fileOut.close();
         */
    }
}
