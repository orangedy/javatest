/*
 * @(#) AntispamHttpClient.java Feb 17, 2013
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 类AntispamHttpClient
 *
 * @author zhangyunyun
 * @version Feb 17, 2013
 */
public class AntispamHttpClientUtil {

    private static Logger logger = Logger.getLogger(AntispamHttpClientUtil.class);

    private AntispamHttpClientUtil() {
    }

    public static org.apache.commons.httpclient.HttpClient makeHttpClient() {
        return makeHttpClient(10000);
    }

    public static org.apache.commons.httpclient.HttpClient makeHttpClient(int soTimeout) {
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        manager.getParams().setConnectionTimeout(10000);
        manager.getParams().setSoTimeout(soTimeout);
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient(manager);
        client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        return client;
    }

    private static String executeRequest(HttpUriRequest request, String url, int type) {
        try {
            // 发送请求
            HttpClient client = new AntispamHttpClientForLofter().getHttpClient();

            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                request.abort();
                return null;
            }
            HttpEntity resEntity = response.getEntity();
            return (resEntity == null) ? null : EntityUtils.toString(resEntity, "utf-8");
        } catch (RuntimeException re) {
            logger.error(null, re);
            request.abort();
            return null;
        } catch (ClientProtocolException ce) {
            ce.printStackTrace();
            logger.error(null, ce);
            request.abort();
            return null;
        } catch (SocketTimeoutException se) {
            request.abort();
            logger.error("连接失败url=" + url);
            return null;
        } catch (IOException ie) {
            request.abort();
            ie.printStackTrace();
            logger.error("连接失败url=" + url, ie);
            return null;
        }
    }

    public static String post(String url, Map<String, String> paramMap, int type) {
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        for (Entry<String, String> entry : paramMap.entrySet()) {
            postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return doPost(url, postParams, type);

    }

    public static String post(String url, int type, NameValuePair... params) {
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        for (NameValuePair param : params) {
            postParams.add(param);
        }
        return doPost(url, postParams, type);

    }

    private static String doPost(String url, List<NameValuePair> postParams, int type) {
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, "utf-8");
            // 创建POST请求
            HttpPost request = new HttpPost(url);
            request.setEntity(entity);

            return executeRequest(request, url, type);
        } catch (UnsupportedEncodingException ue) {
            logger.info(null, ue);
            return null;
        }
    }

    public static String get(String url, int type, NameValuePair... params) {
        StringBuffer urlSb = new StringBuffer("");
        if (params.length > 0) {
            urlSb.append("?");
            for (NameValuePair param : params) {
                urlSb.append(param.getName()).append("=").append(param.getValue()).append("&");
            }
            urlSb.setLength(urlSb.length() - 1);
        }
        return doGet(url, urlSb.toString(), type);

    }

    public static String get(String url, Map<String, String> paramMap, int type) {
        StringBuffer paramStr = new StringBuffer("?");
        for (Entry<String, String> entry : paramMap.entrySet()) {
            paramStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        paramStr.setLength(paramStr.length() - 1);

        return doGet(url, paramStr.toString(), type);

    }

    private static String doGet(String url, String paramStr, int type) {
        HttpGet request = new HttpGet(url + paramStr);

        return executeRequest(request, url, type);
    }

    public static String getRedirectUrl(String url, int type) {
        HttpClient client = new AntispamHttpClientForLofter().getHttpClient();
        HttpPost request = new HttpPost(url);
        try {
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 300 || code == 301 || code == 302) {
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    if (header.getName().equalsIgnoreCase("location"))
                        url = header.getValue();
                }
            } else
                return url;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.abort();
        }
        return url;
    }

    public static void releaseConnection(int type) {
        new AntispamHttpClientForLofter().getHttpClient().getConnectionManager().shutdown();

    }

    public static void main(String[] args) {
        // String url = "http://t.cn/zYb6z6R";
        String url = "http://223.252.197.132:8181/api/loft/blog-del-sync.html";
        // String url = "http://163.fm/bW6PCiW";
        System.out.println(AntispamHttpClientUtil.post(url, new HashMap<String, String>(), 0));

    }

}