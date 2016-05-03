/*
 * @(#) PerformanceTest.java 2015年7月23日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 *
 * @author hzdingyong
 * @version 2015年7月23日
 */
public class PerformanceTest {
    class MyException extends Exception {
        private static final long serialVersionUID = -5258338411286775421L;

        public MyException() {
            super();
        }

        public MyException(String msg) {
            super(msg);
        }

        public MyException(String msg, Throwable t) {
            super(msg, t);
        }
    }

    public String method() {
        // do something and return the result
        int flag = 0;
        int[] testArray = new int[500];
        for (int i = 0; i < 10000; i++) {
            flag = RandomUtils.nextInt(1000);
        }
        if (flag > 500) {
            return "success";
        } else {
            return "fail";
        }
    }

    public String methodWithException() throws MyException {
        // do something, throw a exception when something is wrong
        int flag = 0;
        int[] testArray = new int[500];
        for (int i = 0; i < 10000; i++) {
            flag = RandomUtils.nextInt(1000);
        }
        if (flag < 500) {
            try {
                int o = testArray[flag];
            } catch (Exception e) {
                throw new MyException("fail", e);
            }
        }
        return "success";
    }

    @Test
    public void testException() {
        PerformanceTest test = new PerformanceTest();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
//            System.out.println(test.method());
            test.method();
        }
        long elapse1 = (System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            try {
//                System.out.println(test.methodWithException());
                test.methodWithException();
            } catch (Exception e) {
//                System.out.println(e.getMessage());
            }
        }
        long elaspe2 = (System.currentTimeMillis() - start);
        System.out.println(elapse1 + ":" + elaspe2);
    }
}
