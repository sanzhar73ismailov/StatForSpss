package com.sanzhar.spss.statforspss.excelwrite.examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class CreateExcelDemo {

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static void main(String[] args) throws IOException, ParseException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees sheet");

        List<Employee> list = EmployeeDAO.listEmployees();

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // EmpNo
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("EmpNo");
        cell.setCellStyle(style);
        // EmpName
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("EmpNo");
        cell.setCellStyle(style);
        // Salary
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Salary");
        cell.setCellStyle(style);
        // Grade
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Grade");
        cell.setCellStyle(style);
        // Bonus
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Bonus");
        cell.setCellStyle(style);
        // DateBirth
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("DateBirth");
        cell.setCellStyle(style);

        // Data
        for (Employee emp : list) {
            rownum++;
            row = sheet.createRow(rownum);

            // EmpNo (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(emp.getEmpNo());
            // EmpName (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(emp.getEmpName());
            // Salary (C)
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue(emp.getSalary());
            // Grade (D)
            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(emp.getGrade());
            // Bonus (E)
            String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
            cell = row.createCell(4, CellType.FORMULA);
            cell.setCellFormula(formula);
            // DateBirth (F)
            //cell = row.createCell(5);

            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            /*            
//String formatJava = "dd.MM.yyyy";
            //String formatJava = "yyyy-MM-dd";
            String formatJava = "dd.MM.yyyy";
            //String formatExcel = "yyyy-MM-dd";
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(formatJava));
            cell = row.createCell(5);
            
            String strDate = new SimpleDateFormat(formatJava).format(new Date());
            
            //cell.setCellValue(new SimpleDateFormat(formatJava).parse(strDate));
            
            //cell.setCellValue(new java.sql.Date(2001, 10, 23));
            //Calendar.getInstance().
            cell.setCellValue("18.04.2010");
            cell.setCellStyle(cellStyle);
             */

//CellStyle cellStyle = wb.createCellStyle();
//CreationHelper createHelper = wb.getCreationHelper();
/*
cellStyle.setDataFormat(
    createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
cell = row.createCell(5);
cell.setCellValue(new Date());
cell.setCellStyle(cellStyle);
             */
            cell = row.createCell(5);
            CellStyle cellStyle11 = workbook.createCellStyle();
            cellStyle.setDataFormat((short) 14);
            cell.setCellStyle(cellStyle11);
            cell.setCellValue(new Date());
            // createHelper.createDataFormat().getFormat("dd/MM/yyyy h:mm"));
            //cell.setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            //cell.setCellValue(new Date());
        }
        File file = new File("C:\\temp\\spssTest\\employee.xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());

    }
}
