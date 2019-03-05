package com.sanzhar.spss.statforspss.excelwrite.examples;

/**
 *
 * @author admin
 */
public class TestMain {
    public static void main(String[] args) {
        String str = "3,910E8";
       // str = str.replace(',', '.');
        double d2 = 1.234E2;
        System.out.println("d2 = " + d2);
        System.out.println("d2 = " + Double.parseDouble(str));
        System.out.println("d2 = " + Double.parseDouble(str)/100000000);
    }
}
