/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.spss.statforspss.excelwrite.examples;

import static com.sanzhar.spss.statforspss.excelwrite.examples.StatisticsJavCommon.getResultByVarName;
import static com.sanzhar.spss.statforspss.excelwrite.examples.StatisticsJavCommon.readSpssDataCsvFile;
import static com.sanzhar.spss.statforspss.excelwrite.examples.StatisticsJavCommon.readSpssOutputFile;
import java.util.List;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class StatisticsJavCommonTest {

    public StatisticsJavCommonTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testCompareSppssVsCommon() {
        final List<StatResult> statResultsFromSpssOutputFile = readSpssOutputFile();
        assertNotNull(statResultsFromSpssOutputFile);
        assertTrue(statResultsFromSpssOutputFile.size() > 0);
        final List<Variable> varsFromScv = readSpssDataCsvFile();
        assertNotNull(varsFromScv);
        assertTrue(varsFromScv.size() > 0);
        for (Variable variable : varsFromScv) {
            assertNotNull(variable);
            String varName = variable.getName();
            assertNotNull(varName);
            if (varName.equals("id")) {
                continue;
            }
//            if (!variable.getName().equals("income_2011")) {
//                continue;
//            }
            

            StatResult statResult = getResultByVarName(statResultsFromSpssOutputFile, variable.getName());
            assertNotNull(statResult);
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(variable.getValues());

            assertEquals(statResult.getN(), descriptiveStatistics.getN());
            assertTrue(StatisticsJavCommon.isEqual(statResult.getMean(), descriptiveStatistics.getMean()));
            assertTrue(StatisticsJavCommon.isEqual(statResult.getMin(), descriptiveStatistics.getMin()));
            assertTrue(StatisticsJavCommon.isEqual(statResult.getMax(), descriptiveStatistics.getMax()));
            assertTrue(StatisticsJavCommon.isEqual(statResult.getStDev(), descriptiveStatistics.getStandardDeviation()));
            double stErr = descriptiveStatistics.getStandardDeviation() / Math.sqrt(descriptiveStatistics.getN());
            assertTrue(StatisticsJavCommon.isEqual(statResult.getStErr(), stErr));
            assertTrue(StatisticsJavCommon.isEqual(statResult.getVariance(), descriptiveStatistics.getVariance()));
   

            System.out.println("descriptiveStatistics = " + descriptiveStatistics);

            // System.out.println("variable = " + variable);
        }
    }

}
