/*
 * @(#) NumberTest.java 2015年5月15日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import java.text.DecimalFormat;

import org.junit.Test;

/**
 *
 * @author hzdingyong
 * @version 2015年5月15日
 */
public class NumberTest {

    @Test
    public void testNumFormat() {
        System.out.println(new DecimalFormat("00.00%").format((double) 10 / 11));
        DecimalFormat df = new DecimalFormat();
        df.setGroupingSize(4);
        System.out.println(df.format(150000005566L));
    }

    @Test
    public void testMath() {
        System.out.println(-20 / 10);
        System.out.println((1 << 24) - 1);
    }

    @Test

    public void testPlus() {
        int i = 0;

        System.out.println(i++);
    }

    @Test
    public void testDouble() {
        int i = -1;
        double ret = i / 100.0d;
        System.out.println(ret);
    }
}
