package com.sanzhar.spss.statforspss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DbWork {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "ovarian";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] argv) {
        try {
             List<VariableLabel> listValueLabels = selectColumnNamesAndComments("a01");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<VariableLabel> selectColumnNamesAndComments(String table) throws SQLException {
        List<VariableLabel> listValueLabels = new ArrayList<>();

        Connection dbConnection = null;
        Statement statement = null;

        String sql = "SELECT column_name, column_comment "
                + " FROM information_schema.columns "
                + " WHERE table_schema = '%s' AND TABLE_NAME = '%s'";

        String query = String.format(sql, DB_NAME, table);
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(query);
            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                VariableLabel label = new VariableLabel();
                label.columnName = rs.getString("column_name");
                label.columnComment = rs.getString("column_comment");
                System.out.println("label : " + label);
                listValueLabels.add(label);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return listValueLabels;
    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    private static void selectRecordsFromDbUserTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
