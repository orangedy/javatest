/*
 * @(#) FrequencyUtils.java 2014年9月17日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package frequencylimit;

/**
 *
 * @author hzdingyong
 * @version 2014年9月17日
 */
import org.apache.commons.lang.time.StopWatch;

/** 
 * <p> 
 * Frequence Utils 
 * <p> 
 *  
 * <p>#ThreadSafe#</p> 
 *  
 * @author Opencfg Software Foundation 
 * @since 0.0.1-SNAPSHOT 
 * @version $Id: FrequenceUtils.java 2011-06-11 23:58:53 reymondtu $ 
 */
public final class FrequencyUtils {

    /** 
     * <p> 
     * Limit call count in split time 
     * </p> 
     *  
     * @param limitSplitTime 
     * @param limitCount 
     * @throws InterruptedException 
     */
    public static void limit(final long limitSplitTime, final int limitCount) throws InterruptedException {
        FrequenceUnit funit = threadLocal.get();
        funit.limitSplitTime = limitSplitTime;
        funit.limitCount = limitCount;
        funit.watch.split();
        long diffTime = funit.limitSplitTime - funit.watch.getSplitTime();
        if (diffTime >= 0) {
            if (funit.realCount >= funit.limitCount) {
                funit.watch.suspend();
                Thread.sleep(diffTime);
                funit.watch.resume();
                funit.realCount = 0;
            }
        }
        funit.realCount++;
    }

    /** 
     * FrequenceUnit 
     */
    private static class FrequenceUnit {
        FrequenceUnit() {
            this.watch = new StopWatch();
        }
        long limitSplitTime;
        int limitCount;
        StopWatch watch;
        int realCount = 0;
    }

    private static ThreadLocal<FrequenceUnit> threadLocal = new ThreadLocal<FrequenceUnit>() {
        protected synchronized FrequenceUnit initialValue() {
            FrequenceUnit funit = new FrequenceUnit();
            funit.watch.start();
            return funit;
        }
    };

}
