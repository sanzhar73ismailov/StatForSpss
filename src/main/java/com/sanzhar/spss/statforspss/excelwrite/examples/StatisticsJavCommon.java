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

    public static void main(String[] args) {
        double[] testData = {12, 10, 19};
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(testData);
        out.println("\nThe mean is " + descriptiveStatistics.getMean());
        out.println("The standard deviation is " + descriptiveStatistics.getStandardDeviation());
        out.println("The median is " + descriptiveStatistics.getPercentile(50));
        
        final NormalDistribution unitNormal = new NormalDistribution(0d, 1d);
        final double kolmogorovSmirnovTest = TestUtils.kolmogorovSmirnovTest(unitNormal, testData, false);
        System.out.println("kolmogorovSmirnovTest = " + kolmogorovSmirnovTest);
        final double kolmogorovSmirnovStatistic = TestUtils.kolmogorovSmirnovStatistic(unitNormal, testData);
        System.out.println("kolmogorovSmirnovStatistic = " + kolmogorovSmirnovStatistic);

    }

}
