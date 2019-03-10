package com.sanzhar.spss.statforspss.excelwrite.examples;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class TestMain {
    public static void main(String[] args) {
        longVsLong();
    }
    static void parseDouble() {
        
        String str = "3,910E8";
       // str = str.replace(',', '.');
        double d2 = 1.234E2;
        System.out.println("d2 = " + d2);
        System.out.println("d2 = " + Double.parseDouble(str));
        System.out.println("d2 = " + Double.parseDouble(str)/100000000);
    }
    
    static void longVsLong (){
        long varPr = 10;
        Long varObjFromPr = new Long(varPr);
        System.out.println("varObjFromPr.hashCode = " + varObjFromPr.hashCode());
        //Long varObj = new Long(10);
        Long varObj = null;
        
        boolean res111 = (varPr == varObj);
        if(1==1) return;
        
       // System.out.println("varObj.hashCode = " + varObj.hashCode());
        boolean res = (varObjFromPr == varObj);
        System.out.println("res of '==' is " + res);
        System.out.println("res of 'equals' is " + (varObjFromPr.equals(varObj)));
         boolean res2 = (Objects.equals(varObjFromPr, varObj));
         System.out.println("Objects.equals = " + res2);
        
        
    }
}
