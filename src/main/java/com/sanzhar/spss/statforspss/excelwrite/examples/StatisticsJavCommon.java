package com.sanzhar.spss.statforspss.excelwrite.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.ArrayStack;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;

/**
 *
 * @author admin
 */
public class StatisticsJavCommon {

    static final double[] TEST_DATA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 8, 9, 0, 6, 5, 4, 5, 6};
    static final double PROC_DIFF_MAX = 0.05D;
    private static final String FILE_NAME = "S:\\NetBeansProjects\\StatForSpss\\testdata\\freelancerDescrReport.txt";
    private static final String CSV_FILE_NAME = "S:\\NetBeansProjects\\StatForSpss\\testdata\\freelancers.csv";

    private static final String KOLMOGOR_FILE_NAME = "S:\\NetBeansProjects\\StatForSpss\\testdata\\vars100KolmogorReport.txt";
    private static final String CSV_FILE_NAME_100_VARS = "S:\\NetBeansProjects\\StatForSpss\\testdata\\data100vars_for_kolmogor.csv";

    public static void main(String[] args) {
        //kolmogor();
        //normalDistr();
        //readSpssOutputFile();
        //final List<Variable> readSpssDataCsvFile = readSpssDataCsvFile();
        //compareSppssVsCommon();
        final List<KolomogorStatResult> readSpssKolmogOutputFile = readSpssKolmogOutputFile(KOLMOGOR_FILE_NAME);
        for (KolomogorStatResult kolomogorStatResult : readSpssKolmogOutputFile) {
            System.out.println("kolomogorStatResult = " + kolomogorStatResult);
        }
//        double x1 = 2548200d;
//        double x2 = 2548177.92175;
//        boolean isEq = isEqual(x1, x2);
//        System.out.println("isEq = " + isEq);
    }

    static void compareSppssVsCommon() {
        //final List<StatResult> statResultsFromSpssOutputFile = readSpssOutputFile();
        final List<Variable> readSpssDataCsvFile = readSpssDataCsvFile();
        for (Variable variable : readSpssDataCsvFile) {
            if (variable.getName().equals("id")) {
                continue;
            }
            if (!variable.getName().equals("income_2010")) {
                //continue;
            }
            System.out.println("*****variable = " + variable);

            //StatResult result = getResultByVarName(statResultsFromSpssOutputFile, FILE_NAME);
            DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(variable.getValues());

            //System.out.println("descriptiveStatistics = " + descriptiveStatistics);
            double mean = descriptiveStatistics.getMean();
            double stDev = descriptiveStatistics.getStandardDeviation();
            NormalDistribution unitNormal = new NormalDistribution(mean, stDev);
            final double kolmogorovSmirnovTest
                    = TestUtils.kolmogorovSmirnovTest(unitNormal, variable.getValues(), false);

            //System.out.println("kolmogorovSmirnovTest=" + Math.round(kolmogorovSmirnovTest,));
            // System.out.println("variable = " + variable);
        }
    }

    public static List<Variable> readSpssDataCsvFile() {
        return readSpssDataCsvFile(CSV_FILE_NAME);
    }
    
    public static List<Variable> readKolmogorSpssDataCsvFile() {
        return readSpssDataCsvFile(CSV_FILE_NAME_100_VARS);
    }
    
    public static List<KolomogorStatResult> readSpssKolmogOutputFile100Vars(){
        return readSpssKolmogOutputFile(KOLMOGOR_FILE_NAME);
    }
    
    

    public static List<Variable> readSpssDataCsvFile(String fileName) {
        List<Variable> dataVars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String sCurrentLine;
            int rowNum = 0;
            List<String> fields = new ArrayList<>();
            List<String> rows = new ArrayList<>();
            while ((sCurrentLine = br.readLine()) != null) {
                rowNum++;

                if (rowNum == 1) {
                    final String[] vals = sCurrentLine.split(";");
                    for (String fieldName : vals) {
                        fields.add(fieldName.trim());
                    }
                    continue;
                }
                rows.add(sCurrentLine);
            }

            for (int i = 0; i < fields.size(); i++) {
                String field = fields.get(i);
                Variable v = new Variable();
                v.setName(field);
                double[] data = new double[rows.size()];
                for (int j = 0; j < rows.size(); j++) {
                    String row = rows.get(j);
                    final String[] vals = row.split(";");
                    data[j] = convertToDouble(vals[i]);
                }
                v.setValues(data);
                dataVars.add(v);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataVars;

    }

    public static List<StatResult> readSpssOutputFile() {
        List<StatResult> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String sCurrentLine;
            int rowNum = 0;
            /*
            1 - N	
            2 - Минимум	
            3 - Максимум	
            4 - Сумма	
            5 - Среднее	
            6 - Стд. ошибка
            7 - Стд. отклонение	
            8 - Дисперсия
             */
            while ((sCurrentLine = br.readLine()) != null) {
                rowNum++;
                if (rowNum < 3) {
                    continue;
                }
                if (sCurrentLine.trim().isEmpty()) {
                    break;
                }
                StatResult result = new StatResult();
                final String[] vals = sCurrentLine.split("\t");

                result.setVarName(vals[0]);
                result.setN(Integer.parseInt(vals[1]));
                result.setMin(convertToDouble(vals[2]));
                result.setMax(convertToDouble(vals[3]));
                result.setSum(convertToDouble(vals[4]));
                result.setMean(convertToDouble(vals[5]));
                result.setStErr(convertToDouble(vals[6]));
                result.setStDev(convertToDouble(vals[7]));
                result.setVariance(convertToDouble(vals[8]));
                results.add(result);
                //System.out.println("result = " + result);
                //System.out.println(sCurrentLine);
                //System.out.println("vals = " + Arrays.toString(vals));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;

    }

    public static List<KolomogorStatResult> readSpssKolmogOutputFile(String fileName) {
        List<KolomogorStatResult> kolmogorResults = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String sCurrentLine;
            int rowNum = 0;
            List<String> varNames = new ArrayList<>();
            List<Double> rowsStat = new ArrayList<>();
            List<Double> rowsP = new ArrayList<>();
            while ((sCurrentLine = br.readLine()) != null) {
                rowNum++;
                StatResult result = new StatResult();
                if (sCurrentLine.trim().isEmpty()) {
                    continue;
                }
                sCurrentLine = sCurrentLine.trim();

                
                if (varNames.isEmpty()) {
                    final String[] vals = sCurrentLine.split("\t");
                    for (String val : vals) {
                        varNames.add(val);
                    }
                    continue;
                } else if (sCurrentLine.startsWith("Статистика Z Колмогорова-Смирнова")) {
                    sCurrentLine = sCurrentLine.replace("Статистика Z Колмогорова-Смирнова", "").trim();
                    final String[] vals = sCurrentLine.split("\t");
                    for (String val : vals) {
                        rowsStat.add(convertToDouble(val));
                    }
                } else if (sCurrentLine.startsWith("Асимпт. знч. (двухсторонняя)")) {
                    sCurrentLine = sCurrentLine.replace("Асимпт. знч. (двухсторонняя)", "").trim();
                    final String[] vals = sCurrentLine.split("\t");
                    for (String val : vals) {
                        rowsP.add(convertToDouble(val));
                    }
                }
            }
            for (int i = 0; i < varNames.size(); i++) {
                String varName = varNames.get(i);
                KolomogorStatResult statResult = new KolomogorStatResult();
                kolmogorResults.add(statResult);
                statResult.setVarName(varName);
                statResult.setZ(rowsStat.get(i));
                statResult.setP(rowsP.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return kolmogorResults;

    }

    static StatResult getResultByVarName(List<StatResult> readSpssOutputFile, String varName) {
        for (StatResult statResult : readSpssOutputFile) {
            if (statResult.getVarName().equals(varName)) {
                return statResult;
            }
        }
        return null;
    }
    
    static KolomogorStatResult getKolmogorResultByVarName(List<KolomogorStatResult> readSpssOutputFile, String varName) {
        for (KolomogorStatResult statResult : readSpssOutputFile) {
            if (statResult.getVarName().equals(varName)) {
                return statResult;
            }
        }
        return null;
    }

    static double convertToDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new NumberFormatException();
        }
        text = text.trim().replace(',', '.');
        return Double.parseDouble(text);

    }

    static void kolmogor() {
        //double[] testData = {12, 10, 19};
        double[] testData = TEST_DATA;
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(testData);
        out.println("\nThe mean is " + descriptiveStatistics.getMean());
        out.println("The standard deviation is " + descriptiveStatistics.getStandardDeviation());
        out.println("The median is " + descriptiveStatistics.getPercentile(50));

        final NormalDistribution unitNormal = new NormalDistribution(descriptiveStatistics.getMean(), descriptiveStatistics.getStandardDeviation());
        final double kolmogorovSmirnovTest
                = TestUtils.kolmogorovSmirnovTest(unitNormal, testData, false);
        System.out.println("kolmogorovSmirnovTest, the p-value = "
                + kolmogorovSmirnovTest);
        final double kolmogorovSmirnovStatistic = TestUtils.kolmogorovSmirnovStatistic(unitNormal, testData);
        System.out.println("kolmogorovSmirnovStatistic, the D-statistic.  = "
                + kolmogorovSmirnovStatistic);

    }

    static void normalDistr() {
        double mean = 10;
        double sd = 1.2;
        NormalDistribution obj = new NormalDistribution(mean, sd);
        System.out.println("obj.getMean = " + obj.getMean());
        System.out.println("obj.getNumericalMean = " + obj.getNumericalMean());
        System.out.println("obj.getStandardDeviation = " + obj.getStandardDeviation());
        System.out.println("obj.getSupportUpperBound = " + obj.getSupportUpperBound());
        System.out.println("obj.getSupportLowerBound = " + obj.getSupportLowerBound());
        System.out.println("obj.sample() = " + obj.sample());

        //11.047341134912415
    }

    static boolean isEqual(double x, double y) {
        return PROC_DIFF_MAX > procentDif(x , y);
    }
    
    static double procentDif(double x, double y) {
        double diff = Math.abs(x - y);
        //System.out.println("diff = " + diff);
        double proc = (diff * 100) / Math.max(x, y);
        //System.out.println("proc = " + proc);
        return proc;
    }

}
