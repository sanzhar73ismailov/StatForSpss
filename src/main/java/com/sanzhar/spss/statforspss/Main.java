package com.sanzhar.spss.statforspss;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;



/**
 *
 * @author admin
 */
public class Main {
    final static Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        
        LOGGER.debug("START");
        
        final TableInfo tableInfo = new TableInfo("ovarian_general_data");
       // final List<VariableLabel> columnNamesAndComments = tableInfo.getColumnNamesAndComments();
        //System.out.println("columnNamesAndComments = " + columnNamesAndComments);
       // final List<ValueLabel> valueLabels = tableInfo.getValueLabels();
        //System.out.println("valueLabels = " + valueLabels);
        FilesWriter filesWriter = new FilesWriter("c:/temp/spssTest", tableInfo);
        //final String str = filesWriter.getVarLabelCommand();
        final String str = filesWriter.getValueLabelCommand();
        filesWriter.writeToFile();
        //System.out.println("str = " + str);
        
         LOGGER.debug("FINISH");
        
     
    
    }
}