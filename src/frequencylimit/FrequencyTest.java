/*
 * @(#) FrequencyTest.java 2014年9月17日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package frequencylimit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author hzdingyong
 * @version 2014年9月17日
 */

/** 
 * <p> 
 * Frequence Utils Test 
 * <p> 
 *  
 * <p>JUnit4 Test</p> 
 *  
 * @author Opencfg Software Foundation 
 * @since 0.0.1-SNAPSHOT 
 * @version $Id: FrequenceUtilsTest.java 2011-06-12 01:36:53 reymondtu $ 
 */
public class FrequencyTest {

    @Test
    public void testLimit() throws InterruptedException {
        FrequenceTestClass ftc = new FrequenceTestClass(1000, 10);
        for (int i = 0; i < 100; i++) {
            ftc.method(i);
        }
        assertTrue(true);
    }

    @Test
    public void testLimitMutiThreads() throws InterruptedException {
        Thread t1 = new Thread(new FrequenceTestClass(1000, 10));
        t1.start();

        Thread t2 = new Thread(new FrequenceTestClass(1000, 20));
        t2.start();

        Thread.sleep(10000);
    }

    static class FrequenceTestClass implements Runnable {
        final long limitTime;
        final int limitCount;

        FrequenceTestClass(final long limitTime, final int limitCount) {
            this.limitTime = limitTime;
            this.limitCount = limitCount;
        }

        public void method(int i) throws InterruptedException {
            FrequencyUtils.limit(limitTime, limitCount);
            System.out.println("tid:" + Thread.currentThread().getId() + ", i=" + i + ", time:" + System.currentTimeMillis());
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    method(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}