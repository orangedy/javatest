/*
 * @(#) SetTest.java 2016年6月6日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.junit.Test;

/**
 *
 * @author hzdingyong
 * @version 2016年6月6日
 */
public class SetTest {

    @Test
    public void testTreeSet() {
        Set<String> set = new TreeSet<String>();
        set.add("2");
        set.add("1");
        set.add("3");
        for (String item : set) {
            if (item.equals("2")) {
                set.remove(item);
                // return; 如果这里直接返回不会抛java.util.ConcurrentModificationException

            }
        }
    }

    @Test
    public void testCopyOnSet() {
        Set<String> set = new CopyOnWriteArraySet<>();
        set.add("2");
        set.add("1");
        set.add("3");
        for (String item : set) {
            if (item.equals("2")) {
                set.remove(item);
            }
        }
    }

    @Test
    public void testConcurrent() {
        final Set<String> set = new ConcurrentSkipListSet<String>();
        set.add("3");
        set.add("1");
        set.add("2");
        Thread r1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (String item : set) {
                    System.out.println(item);
                }

            }
        });

        Thread r2 = new Thread(new Runnable() {

            @Override
            public void run() {
                set.add("4");
                set.remove("3");
            }
        });
        r1.start();
        r2.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String item : set) {
            System.out.println(item);
        }
    }

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            if (entry.getKey().equals("2")) {
                iter.remove();
            } else {
                System.out.println(entry);
            }
        }

        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("2")) {
                map.remove(entry.getKey());
            } else {
                System.out.println(entry);
            }
        }
    }

    @Test
    public void testConcurrentMap() {
        final Map<String, String> map = new ConcurrentHashMap<String, String>();
        map.put("3", "3");
        map.put("1", "1");
        map.put("2", "2");
        Thread r1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (Map.Entry<String, String> item : map.entrySet()) {
                    System.out.println(item);
                }

            }
        });

        Thread r2 = new Thread(new Runnable() {

            @Override
            public void run() {
                map.put("4", "4");
                map.remove("3");
            }
        });
        r1.start();
        r2.start();
        try {
            Thread.sleep(10000);
            System.out.println("wake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> item : map.entrySet()) {
            System.out.println(item);
        }
    }
}
