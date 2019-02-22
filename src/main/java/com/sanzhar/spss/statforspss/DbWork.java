package com.sanzhar.spss.statforspss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<VariableLabel> getColumnNamesAndComments(String table) throws SQLException {
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
                label.setColumnName(rs.getString("column_name"));
                label.setColumnComment(rs.getString("column_comment"));
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

    private static List<KeyVal> getKeyVals(String varName) throws SQLException {
        List<KeyVal> listKeyVals = new ArrayList<>();
        Connection dbConnection = null;
        Statement statement = null;
        String sql = " SELECT dv.value_id, dv.value_name \n"
                + " FROM %s_dic_val dv \n"
                + " WHERE dv.dic_list_id='%s' \n"
                + " ORDER BY dv.dic_list_id, dv.value_id";

        String query = String.format(sql, DB_NAME, varName);
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(query);
            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                KeyVal keyVal = new KeyVal();
                keyVal.setKey(rs.getString("value_id"));
                keyVal.setValue(rs.getString("value_name"));
                listKeyVals.add(keyVal);
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
        return listKeyVals;
    }

    public static List<ValueLabel> getValueLabels(String table) throws SQLException {
        List<ValueLabel> listValueLabels = new ArrayList<>();

        List<VariableLabel> listVarLabels = getColumnNamesAndComments(table);
        for (VariableLabel varLabel : listVarLabels) {
            if (varLabel.getColumnName().endsWith("_id")) {
                ValueLabel valLabel = new ValueLabel(varLabel.getColumnName());
                if (varLabel.getColumnName().endsWith("_yes_no_id")) {
                    valLabel.setBooleanType(true);
                    List keyValYesNo = Arrays.asList(new KeyVal("0", "Нет"), new KeyVal("1", "Да"));
                    valLabel.setListKeyVals(keyValYesNo);
                } else {
                    List<KeyVal> listKeyVals = getKeyVals(varLabel.getColumnName());
                    valLabel.setListKeyVals(listKeyVals);
                }
                listValueLabels.add(valLabel);
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



//    private static List<KeyVal> getListKeyVals(String columnName) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}
