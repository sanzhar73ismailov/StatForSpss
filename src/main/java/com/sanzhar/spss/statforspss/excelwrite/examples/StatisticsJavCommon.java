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

    private static final String FILE_NAME = "S:\\NetBeansProjects\\StatForSpss\\testdata\\freelancerDescrReport.txt";
    private static final String CSV_FILE_NAME = "S:\\NetBeansProjects\\StatForSpss\\testdata\\freelancers.csv";

    public static void main(String[] args) {
        //kolmogor();
        //normalDistr();
        //readSpssOutputFile();
        final List<Variable> readSpssDataCsvFile = readSpssDataCsvFile();
        for (Variable variable : readSpssDataCsvFile) {
            System.out.println("variable = " + variable);
        }

    }

    public static List<Variable> readSpssDataCsvFile() {
        List<Variable> dataVars = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_NAME))) {
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
                System.out.println("result = " + result);
                //System.out.println(sCurrentLine);
                //System.out.println("vals = " + Arrays.toString(vals));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;

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

}
