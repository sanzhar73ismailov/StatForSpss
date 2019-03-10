package com.sanzhar.spss.statforspss.excelwrite.examples;

import static com.sanzhar.spss.statforspss.excelwrite.examples.StatisticsJavCommon.*;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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


    //@Test
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
        }
    }
    
    @Test
    public void testCompareKolmogorSpssVsCommon() {
        final List<KolomogorStatResult> varResults = readSpssKolmogOutputFile100Vars();
        assertNotNull(varResults);
        assertTrue(varResults.size() > 0);
        final List<Variable> vars = readKolmogorSpssDataCsvFile();
        assertNotNull(vars);
        assertTrue(vars.size() > 0);
        double minDif = 0;
        double maxDif = 0;
        
        for (int i = 0; i < vars.size(); i++) {
            Variable variable = vars.get(i);
            assertNotNull(variable);
            String variableName = variable.getName();
            
            
            System.out.println("variableName = " + variableName);
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(variable.getValues());
            double mean = descriptiveStatistics.getMean();
            double stDev = descriptiveStatistics.getStandardDeviation();
            NormalDistribution unitNormal = new NormalDistribution(mean, stDev);
            final double kolmogorovSmirnovTest
                    = TestUtils.kolmogorovSmirnovTest(unitNormal, variable.getValues(), false);
            KolomogorStatResult kolomogorStatResult = getKolmogorResultByVarName(varResults, variableName);
            //double difAbs = Math.abs(kolmogorovSmirnovTest - kolomogorStatResult.getP());
            double procDif = procentDif(kolmogorovSmirnovTest, kolomogorStatResult.getP());
            if(i == 0){
                minDif = procDif;
            }
            
            if(procDif < minDif){
                minDif = procDif;
            }
            if(procDif > maxDif){
                maxDif = procDif;
            }
            
            boolean normalDistr = kolmogorovSmirnovTest > 0.01;
            System.out.println("normalDistr = " + normalDistr);
            System.out.println("kolomogorStatResult.isNormal() = " + kolomogorStatResult.isNormal());
            
            assertTrue(normalDistr == kolomogorStatResult.isNormal());
        }
        System.out.println("minDif = " + minDif);
        System.out.println("maxDif = " + maxDif);
    }

}
