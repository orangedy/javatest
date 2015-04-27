/*
 * @(#) HttpClient4Utils.java 2014年11月21日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package httpclient;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author hzdingyong
 * @version 2014年11月21日
 */
public class HttpClient4Utils {
    private static final Log logger = LogFactory.getLog(HttpClient4Utils.class);

    private static class HttpClientHolder {
        private static HttpClient httpClientInstance = createHttpClientInstance();
    }

    /**
     * lazy load and thread safe
     * @return
     */
    public static HttpClient getHttpClient() {
        return HttpClientHolder.httpClientInstance;
    }

    /**
     * @param maxTotal max connection for all route
     * @param maxPerRoute default max connection for each route
     * @return
     */
    public static HttpClient createHttpClientInstance(int maxTotal, int maxPerRoute) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        return httpClient;
    }

    public static HttpClient createHttpClientInstance() {
        return createHttpClientInstance(10, 2);
    }

    public static void closeHttpClient(HttpClient httpClient) {
        CloseableHttpClient closedHttpClient = (CloseableHttpClient) httpClient;
        try {
            closedHttpClient.close();
        } catch (IOException e) {
            // ignore
            logger.info("HttpClient close failed, msg=", e);
        }
    }

    public static String sendPost(HttpClient httpClient, String url, Map<String, String> params, Charset encoding)
                    throws ApiNotAvailableException {
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParams, encoding);
            httpPost.setEntity(postEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                // 返回状态错误，直接抛异常，记录时精简一下log输出
                throw new IllegalStateException(String.format("错误的接口返回状态值, status=%d, response=%s", status, StringUtils
                                .abbreviate(EntityUtils.toString(response.getEntity(), encoding), 1024)));
            }
            // EntityUtils is not recommended
            return EntityUtils.toString(response.getEntity(), encoding);
        } catch (Exception e) {
            String msg = String.format("http接口调用出错url=%s, params=%s", url, params);
            throw new ApiNotAvailableException(msg, e);
        } finally {
            closeQuietly(response);
        }
    }

    public static String sendPostByJson(HttpClient httpClient, String url, String json, Charset encoding)
                    throws ApiNotAvailableException {
        HttpPost httpPost = new HttpPost(url);
        if (StringUtils.isNotEmpty(json)) {
            StringEntity stringEntity = new StringEntity(json, ContentType.create("application/json", encoding));
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                // 返回状态错误，直接抛异常，记录时精简一下log输出
                throw new IllegalStateException(String.format("错误的接口返回状态值, status=%d, response=%s", status, StringUtils
                                .abbreviate(EntityUtils.toString(response.getEntity(), encoding), 1024)));
            }
            // EntityUtils is not recommended
            return EntityUtils.toString(response.getEntity(), encoding);
        } catch (Exception e) {
            String msg = String.format("http接口调用出错url=%s, params=%s", url, json);
            throw new ApiNotAvailableException(msg, e);
        } finally {
            closeQuietly(response);
        }
    }

    public static String sendPost(HttpClient httpClient, String url, Map<String, String> params)
                    throws ApiNotAvailableException {
        return sendPost(httpClient, url, params, Consts.UTF_8);
    }

    public static String sendPost(String url, Map<String, String> params) throws ApiNotAvailableException {
        return sendPost(getHttpClient(), url, params, Consts.UTF_8);
    }

    public static String sendGet(String url) throws ApiNotAvailableException {
        return sendGet(url, null);
    }

    public static String sendGet(String url, Map<String, String> params) throws ApiNotAvailableException {
        return sendGet(getHttpClient(), url, params);
    }

    public static String sendGet(HttpClient httpClient, String url, Map<String, String> params) throws ApiNotAvailableException {
        return sendGet(httpClient, url, params, null);
    }

    public static String sendGet(HttpClient httpClient, String url, Map<String, String> params, HttpContext context)
                    throws ApiNotAvailableException {
        HttpGet httpGet = null;
        if (MapUtils.isNotEmpty(params)) {
            final List<NameValuePair> qparams = new LinkedList<NameValuePair>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            httpGet = new HttpGet(url + "?" + URLEncodedUtils.format(qparams, "UTF-8"));
        } else {
            httpGet = new HttpGet(url);
        }
        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpGet, context);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                // 返回状态错误，直接抛异常，记录时精简一下log输出
                throw new IllegalStateException(String.format("错误的接口返回状态值, status=%d, response=%s", status, StringUtils
                                .abbreviate(EntityUtils.toString(response.getEntity()), 1024)));
            }
            // EntityUtils is not recommended
            return EntityUtils.toString(response.getEntity()); // 该方法会释放连接。。。
        } catch (Exception e) {
            String msg = String.format("http接口调用出错url=%s, params=%s", url, params);
            throw new ApiNotAvailableException(msg, e);
        } finally {
            closeQuietly(response);
        }
    }

    /**
     * 关闭response
     * @param response
     */
    public static void closeQuietly(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * 定时清理过期的连接
     *
     * @author hzdingyong
     * @version 2014年11月21日
     */
    public static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    /**
     * 设置重试机制
     * @param retryCount
     * @return
     */
    public static HttpRequestRetryHandler setRetryHandler(final int retryCount) {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= retryCount) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };
        return myRetryHandler;
    }

    public static void main(String[] args) throws ApiNotAvailableException {
        HttpClient4Utils.sendGet(HttpClient4Utils.getHttpClient(), "http://www.baidu.com", null);
    }
}
