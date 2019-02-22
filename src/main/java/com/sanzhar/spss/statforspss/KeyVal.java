package com.sanzhar.spss.statforspss;

/**
 *
 * @author admin
 */
public class KeyVal {

    private String key;
    private String value;

    public KeyVal() {
    }

    public KeyVal(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyVal{" + "key=" + key + ", value=" + value + '}';
    }

}
