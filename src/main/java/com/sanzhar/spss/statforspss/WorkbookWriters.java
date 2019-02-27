package com.sanzhar.spss.statforspss;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class WorkbookWriters {
    public static void main(String[] args) throws SQLException {
        final List<List> data = DbWork.getData("ovarian_general_data");
        //System.out.println("data = " + data);
        for (int i = 0; i < data.size(); i++) {
            List row = data.get(i);
            for (Object cell : row) {
                System.out.print(cell + "<<>>");
            }
            System.out.println("");
            if(i == data.size()-2){
                System.out.println("rENNNNNNNNNNNNNND = ");
            }
            
        }
        System.out.println("--------- = ");
        
    }
}
