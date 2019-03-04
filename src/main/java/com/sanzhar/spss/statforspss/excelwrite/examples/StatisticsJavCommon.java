package com.sanzhar.spss.statforspss.excelwrite.examples;

import static java.lang.System.out;
import static org.apache.commons.math3.analysis.FunctionUtils.sample;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;

/**
 *
 * @author admin
 */
public class StatisticsJavCommon {

    static final double[] TEST_DATA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 8, 9, 0, 6, 5, 4, 5, 6};

    public static void main(String[] args) {
        kolmogor();
        //normalDistr();
        
    }
    
    static void kolmogor() {
        //double[] testData = {12, 10, 19};
        double[] testData = TEST_DATA;
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(testData);
        out.println("\nThe mean is " + descriptiveStatistics.getMean());
        out.println("The standard deviation is " + descriptiveStatistics.getStandardDeviation());
        out.println("The median is " + descriptiveStatistics.getPercentile(50));

        final NormalDistribution unitNormal = new NormalDistribution(descriptiveStatistics.getMean(), descriptiveStatistics.getStandardDeviation());
        final double kolmogorovSmirnovTest = 
                          TestUtils.kolmogorovSmirnovTest(unitNormal, testData, false);
        System.out.println("kolmogorovSmirnovTest, the p-value = " 
                + kolmogorovSmirnovTest);
        final double kolmogorovSmirnovStatistic = TestUtils.kolmogorovSmirnovStatistic(unitNormal, testData);
        System.out.println("kolmogorovSmirnovStatistic, the D-statistic.  = " 
                + kolmogorovSmirnovStatistic);

    }
    static void normalDistr(){
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
