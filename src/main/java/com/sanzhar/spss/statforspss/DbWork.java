package com.sanzhar.spss.statforspss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

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

    private final static Logger LOGGER = Logger.getLogger(DbWork.class);

    public static List<List> getDataFromTable(String table) throws SQLException {
        //LOGGER.debug("START");
        List<List> data = new ArrayList<>();
        Connection dbConnection = null;
        Statement statement = null;
        String sql = "SELECT * FROM %s";

        String query = String.format(sql, table);
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            //logger.debug(query);
            ResultSet rs = statement.executeQuery(query);
            int columnCount = rs.getMetaData().getColumnCount();
            int r = 0;
            Set set = new HashSet();
            while (rs.next()) {
                List row = new ArrayList();
                int columnUndex = 0;
                while (columnUndex < columnCount) {
                    final String columnTypeName = rs.getMetaData().getColumnTypeName(columnUndex + 1);
                    //set=[DATE, TIMESTAMP, VARCHAR, DOUBLE, INT]
                    /*
                    Object val = null;
                    
                    switch(columnTypeName){
                        case "INT" : 
                            val = rs.getInt(columnUndex + 1);
                            break;
                        case "DOUBLE"  :
                            val = rs.getInt(columnUndex + 1)
                    }
                    if (r == 0) {
                        //LOGGER.debug("columnTypeName=" + columnTypeName);
                        set.add(columnTypeName);
                    }
*/
                    row.add(rs.getString(columnUndex + 1));
                    columnUndex++;
                    //Object nextElement = columnUndex.nextElement();
                }
                r++;
                data.add(row);
            }
            LOGGER.debug("set=" + set);
            
        } catch (SQLException e) {
            LOGGER.error("error in getColumnNamesAndComments", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        //LOGGER.debug("FINISH");
        return data;
    }

    public static List<VariableLabel> getColumnNamesAndComments(String table) throws SQLException {
        //LOGGER.debug("START");

        List<VariableLabel> listValueLabels = new ArrayList<>();
        Connection dbConnection = null;
        Statement statement = null;
        String sql = "SELECT column_name, column_comment, data_type "
                + " FROM information_schema.columns "
                + " WHERE table_schema = '%s' AND TABLE_NAME = '%s' "
                + " ORDER BY ORDINAL_POSITION";

        String query = String.format(sql, DB_NAME, table);
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            //logger.debug(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                VariableLabel label = new VariableLabel();
                label.setColumnName(rs.getString("column_name"));
                label.setColumnComment(rs.getString("column_comment"));
                label.setDbDataType(rs.getString("data_type"));
                //logger.debug("label : " + label);
                listValueLabels.add(label);
            }
        } catch (SQLException e) {
            LOGGER.error("error in getColumnNamesAndComments", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        //LOGGER.debug("FINISH");
        return listValueLabels;
    }

    private static List<KeyVal> getKeyVals(String varName) throws SQLException {
        //LOGGER.debug("START");
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
            //logger.debug(query);
            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                KeyVal keyVal = new KeyVal();
                keyVal.setKey(rs.getString("value_id"));
                keyVal.setValue(rs.getString("value_name"));
                listKeyVals.add(keyVal);
            }
        } catch (SQLException e) {
            LOGGER.error("error in getKeyVals", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        // LOGGER.debug("FINISH");
        return listKeyVals;
    }

    public static List<ValueLabel> getValueLabels(String table) throws SQLException {
        LOGGER.debug("START, table: " + table);
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
        LOGGER.debug("FINISH");
        return listValueLabels;
    }

    private static Connection getDBConnection() {
        //LOGGER.debug("START");
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("error", e);
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            LOGGER.error("error", e);
        }
        LOGGER.debug("FINISH");
        return dbConnection;
    }

}
