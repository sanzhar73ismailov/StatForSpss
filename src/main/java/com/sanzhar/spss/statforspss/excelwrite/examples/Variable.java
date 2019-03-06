package com.sanzhar.spss.statforspss.excelwrite.examples;

import java.util.Arrays;

public class Variable {

    private String name;
    private double[] values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Variable{" + "name=" + name + ", value=" + Arrays.toString(values) + '}';
    }
    
    
}
