package httpclient;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


public class HttpClientTest {

	/**
	 * @param args
	 */
	//String url;
	public static void main(String[] args) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://www.baidu.com");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        File html = new File("baidu.html");
        File rsfile;
        FileOutputStream fos = new FileOutputStream(html);
        entity.writeTo(fos);
        StringBuffer sb = new StringBuffer();
        String content;
        BufferedReader br = new BufferedReader(new FileReader(html));
        while((content = br.readLine()) != null){
        	sb.append(content);
        }
        content = new String(sb);
        Pattern p = Pattern.compile("<[^<>]*\\s*src\\s*=\\s*([\"|'])(.+?)\\1[^<>]*/?\\s*>");
        Matcher m = p.matcher(content);
        while(m.find()){
        	System.out.println(m.group(2));
        	httpget = new HttpGet(m.group(2));
        	response = httpclient.execute(httpget);
        	entity = response.getEntity();
        	rsfile = new File("baidu" , m.group(2).split("/")[m.group(2).split("/").length-1]);
        	fos = new FileOutputStream(rsfile);
        	entity.writeTo(fos);
        }
        System.out.println("Login form get: " + response.getStatusLine());
        

        /*HttpPost httpost = new HttpPost("https://portal.sun.com/amserver/UI/Login?" +
                "org=self_registered_users&" +
                "goto=/portal/dt&" +
                "gotoOnFail=/portal/dt?error=true");

        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("IDToken1", "username"));
        nvps.add(new BasicNameValuePair("IDToken2", "password"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        response = httpclient.execute(httpost);
        entity = response.getEntity();

        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
            entity.consumeContent();
        }

        System.out.println("Post logon cookies:");
        cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }*/

	}

}
