package com.sanzhar.spss.statforspss;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
public class Util {

    final static Logger LOGGER = Logger.getLogger(Main.class);

    public static void writeToFile(String file, String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(text);
            LOGGER.debug("Done");
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
    }
}
