package com.advanced.demo.mainPage;

/**
 * @author by morton_ws on 2017/8/11.
 */

public class MainItemBean {
    public Class clazz;
    public String itemName;
    public String extraKey;
    public String extraValue;

    public MainItemBean(Class clazz, String itemName) {
        this.clazz = clazz;
        this.itemName = itemName;
    }

    public MainItemBean(Class clazz, String itemName, String extraKey, String extraValue) {
        this.clazz = clazz;
        this.itemName = itemName;
        this.extraKey = extraKey;
        this.extraValue = extraValue;
    }
}
