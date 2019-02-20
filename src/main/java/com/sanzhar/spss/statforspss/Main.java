package com.sanzhar.spss.statforspss;

import java.sql.Connection;
import java.sql.SQLException;



/**
 *
 * @author admin
 */
public class Main {

    public static void main(String[] args) throws SQLException {
       DbWork.selectColumnNamesAndComments("a01");
    }
}
