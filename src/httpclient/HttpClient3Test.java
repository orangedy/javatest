/*
 * @(#) HttpClient3Test.java 2014年11月4日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

/**
 *
 * @author hzdingyong
 * @version 2014年11月4日
 */
public class HttpClient3Test {
    private static final Logger logger = Logger.getLogger(HttpClient3Test.class);

    private static HttpClient httpClient;

    @BeforeClass
    public static void setUp() {
        MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        httpConnectionManager.getParams().setConnectionTimeout(10000);
        httpConnectionManager.getParams().setSoTimeout(15000);
        httpClient = new HttpClient(httpConnectionManager);
    }

    public void testGetMethod(long keepAliveTime) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            logger.info("new request start\n");
            StopWatch stopWatch = new Log4JStopWatch("HttpClient3Test.testGetMethod");
            String url = "http://www.baidu.com";
            NameValuePair[] nameValuePairs = null;
            GetMethod get = new GetMethod(url);
            if (nameValuePairs != null) {
                get.setQueryString(nameValuePairs);
            }
            try {
                int status = httpClient.executeMethod(get);
                String response = get.getResponseBodyAsString(Integer.MAX_VALUE);
                if (status != HttpStatus.SC_OK) {
                    throw new IllegalStateException("错误的接口返回状态值, status=" + status + ", response="
                                    + StringUtils.abbreviate(response, 1024));// 精简一下log输出
                }
                logger.info(response.length());
            } catch (Exception e) {
                String msg = String.format("http接口调用出错url=%s, params=%s", url, ObjectUtils.toString(nameValuePairs));
                logger.error(msg, e);
            } finally {
                get.releaseConnection();
                // 这里设置连接保留的时间，超时则自动关闭连接, http://blog.sina.com.cn/s/blog_8f02ae76010144hu.html
                httpClient.getHttpConnectionManager().closeIdleConnections(keepAliveTime);
                stopWatch.stop();
            }
//            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testKeepAliveTime() throws InterruptedException {
        testGetMethod(1000000);
        testGetMethod(0);
    }
}
