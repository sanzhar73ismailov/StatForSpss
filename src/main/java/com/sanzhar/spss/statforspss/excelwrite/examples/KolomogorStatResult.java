package com.sanzhar.spss.statforspss.excelwrite.examples;

/**
 *
 * @author admin
 */
public class KolomogorStatResult {

    private String varName;
    private double z;
    private double p;

    public KolomogorStatResult(String varName, double z, double p) {
        this.varName = varName;
        this.z = z;
        this.p = p;
    }

    public KolomogorStatResult() {
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public boolean isNormal() {
        return p > 0.01;
    }
    

    @Override
    public String toString() {
        return "KolomogorStatResult{" + "varName=" + varName + ", z=" + z + ", p=" + p + '}';
    }
}
