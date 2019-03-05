package com.sanzhar.spss.statforspss.excelwrite.examples;

/**
 *
 * @author admin
 */
public class StatResult {

    private String varName;
    private int n;
    private double mean;
    private double stDev;
    private double stErr;
    private double variance;
    private double min;
    private double max;
    private double sum;

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
    
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStDev() {
        return stDev;
    }

    public void setStDev(double stDev) {
        this.stDev = stDev;
    }

    public double getStErr() {
        return stErr;
    }

    public void setStErr(double stErr) {
        this.stErr = stErr;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "StatResult{" + "varName=" + varName + ", n=" + n + ", mean=" + mean + ", stDev=" + stDev + ", stErr=" + stErr + ", variance=" + variance + ", min=" + min + ", max=" + max + ", sum=" + sum + '}';
    }



}
