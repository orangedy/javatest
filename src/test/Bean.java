/*
 * @(#) Bean.java 2015年6月3日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package test;

import java.util.List;

/**
 *
 * @author hzdingyong
 * @version 2015年6月3日
 */
public class Bean {
    int a;
    String b;
    List<Bean> c;

    public Bean() {
        // TODO Auto-generated constructor stub
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public List<Bean> getC() {
        return c;
    }

    public void setC(List<Bean> c) {
        this.c = c;
    }
}