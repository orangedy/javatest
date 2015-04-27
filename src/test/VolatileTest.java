/*
 * @(#) VolatileTest.java 2015年1月28日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import org.junit.Test;

/**
 * volatile关键词测试类
 * volatile的作用是确保可见性，即在一个线程中所作的修改，在另一个线程中是立即可见的，而普通变量则不保证
 * 同时volatile能防止编译器优化造成执行顺序乱序的问题
 * @author hzdingyong
 * @version 2015年1月28日
 */
public class VolatileTest {
    public static int count = 0;
    public int visibility = 0;
    private long time1;
    private long time2;
    // 这里加不加volatile关键词的差别比较大，不加可能线程键修改不可见
    boolean boolValue = true;

    public static void inc() {

        // 这里延迟1毫秒，使得结果明显
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//        }
        count = count + 1;
    }

    public static void main(String[] args) {

        // 同时启动1000个线程，去进行i++计算，看看实际结果

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VolatileTest.inc();
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:VolatileTest.count=" + VolatileTest.count);
    }

    @Test
    public void testVisibility() {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("t1 before visibility change:" + visibility);
                System.out.println("t1 after change:" + visibility);
            }

        }, "t1");
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("t2 before visibility change:" + visibility);
                visibility = 1;
                System.out.println("t2 after change:" + visibility);
            }

        }, "t2");
        t1.start();
        t2.start();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * volatile反例验证，正常情况下使用volatile会防止编译器优化，否则可能会t1中的修改t2不可见
     */
    @Test
    public void testMain() {
        int size = 1;
        VolatileTest[] array = new VolatileTest[size];
        for (int i = 0; i < size; i++) {
            VolatileTest test = new VolatileTest();
            array[i] = test;
            try {
                test.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        long timeSum = 0;
        for (int i = 0; i < size; i++) {// 统计出，所有线程从boolValue变为false到while(boolValue)跳出所花时间的总和
            timeSum += array[i].time2 - array[i].time1;
            System.out.print(i + "\t" + array[i].time2 + '\t' + array[i].time1 + '\t' + (array[i].time2 - array[i].time1)
                            + '\n');
        }
        System.out.println("响应时间总和(毫微秒)：" + timeSum);
        long time1, time2;
        time1 = System.nanoTime();
        time2 = System.nanoTime();
        System.out.println(time2 - time1);// 顺序执行两条语句的时间间隔，供参考
    }

    public void test() throws InterruptedException {
        Thread t2 = new Thread() {
            public void run() {
                while (boolValue)
                    ;
//                    System.out.println("do nothing");
                time2 = System.nanoTime();
            }
        };
        Thread t1 = new Thread() {
            public void run() {
                time1 = System.nanoTime();
                boolValue = false;
            }
        };
        t1.start();
        t2.start();
        Thread.yield();
        t1.join();// 保证一次只运行一个测试，以此减少其它线程的调度对 t2对boolValue的响应时间 的影响
        t2.join();
    }

}
