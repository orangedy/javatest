package httpclient;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 * 
 *AntispamHttpClientForOCR
 * @author yubaohong
 * @version 2014-5-14
 */
public class AntispamHttpClientForLofter {

    private static DefaultHttpClient antispamHttpClientForLofter;

    @SuppressWarnings("deprecation")
    public synchronized DefaultHttpClient getHttpClient() {
        if (antispamHttpClientForLofter == null) {
            HttpParams params = new BasicHttpParams();
            // 设置一些基本参数
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, AntispamHttpClientConst.CHARSET);

            // 设置我们的HttpClient支持HTTP和HTTPS两种模式
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

            PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
//            cm.setMaxTotal(AntispamHttpClientConst.MAX_TOTAL_CONNECTIONS);
//            cm.setDefaultMaxPerRoute(AntispamHttpClientConst.MAX_ROUTE_CONNECTIONS);
            // 设置连接超时时间
//            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, AntispamHttpClientConst.CONNECT_TIMEOUT);
//            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, AntispamHttpClientConst.READ_TIMEOUT_FOROCR);
//            params.setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
            antispamHttpClientForLofter = new DefaultHttpClient(cm, params);
            // Create a local instance of cookie store
            CookieStore cookieStore = new BasicCookieStore();
            // Bind custom cookie store to the local context
            antispamHttpClientForLofter.setCookieStore(cookieStore);
//            antispamHttpClientForLofter.getCookieSpecs().register("easy", AntispamHttpClientConst.csf);
//            antispamHttpClientForLofter.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
            antispamHttpClientForLofter.getCredentialsProvider()
                            .setCredentials(AuthScope.ANY,
                                            new UsernamePasswordCredentials("censorapi", "03ac9b8ce112c99de723b77225403286"));

        }
        return antispamHttpClientForLofter;
    }

}
