/*
 * @(#) EnumType.java 2015年6月11日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

/**
 *
 * @author hzdingyong
 * @version 2015年6月11日
 */
public enum EnumType {
    Value1(1, "value1"),
    Value2(2, "value2");

    private int value;
    private String description;

    EnumType(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
