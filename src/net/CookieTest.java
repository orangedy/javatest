package net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;


public class CookieTest {

	public DefaultHttpClient http;
	
	private int index = 1;
	
	public String type;
	
//	private String USERAGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.83 Safari/537.1";
	private String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; rv:19.0) Gecko/20100101 Firefox/19.0";
	
	public void init(){
		this.http = new DefaultHttpClient();
	}
	
	public void executeHttp(String url){
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = http.execute(httpGet);
			System.out.println(Thread.currentThread().getName() + response.getStatusLine());
			File file = new File("google/" + type + index++ + ".html");
			if(!file.exists()){
				try{
					file.createNewFile();
				}catch(IOException e){
					File parents = new File(file.getParent());
					parents.mkdirs();
					file.createNewFile();
				}
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			Iterator<Object> it = response.headerIterator();
			Iterator<Object> it1 = httpGet.headerIterator();
			while(it1.hasNext()){
				Header header = (Header) it1.next();
				writer.write(header.getName() + ":" + header.getValue() + "\n");
			}
			writer.write("\n");
			while(it.hasNext()){
				Header header = (Header) it.next();
				writer.write(header.getName() + ":" + header.getValue() + "\n");
				
			}
			writer.write("\n");
//			writer.write(EntityUtils.toString(response.getEntity(), "GBK"));
			System.out.println(EntityUtils.toString(response.getEntity(), "GBK"));
//			response.getEntity().writeTo(writer);
			writer.flush();
			response.getEntity().consumeContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
		}
	}
	
	public void configHttp(){
		//set params method
//		HttpParams params = new BasicHttpParams();
//		HttpProtocolParamBean protocolParam = new HttpProtocolParamBean(params);
//		protocolParam.setUserAgent(USERAGENT);
//		HttpProtocolParams.setUserAgent(params, USERAGENT);
//		this.http = new DefaultHttpClient(params);
		//set useagent
		this.http.getParams().setParameter("http.useragent", USERAGENT);
		//set cookie policy
//		this.http.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
		//set request header
//		List<Header> headers = new ArrayList<Header>();
//		headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
//		headers.add(new BasicHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
//		headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
//		this.http.getParams().setParameter("http.default-headers", headers);
		//add cookies
//		this.http.getCookieStore().addCookie(new BasicClientCookie("PREF", "ID=d268625b1fcefe1d:U=36d6291c61b0dbbe:FF=1:LD=zh-CN:NW=1:TM=1361761379:LM=1361784290:S=gT5kBt01xp6CGq3G"));
//		this.http.getCookieStore().addCookie(new BasicClientCookie("NID", "67=i91fML166XarUT_YWKtMvso1TBCC_8k2ICS8CxoBU7zE3_Q1CycUNt8rqZ95c1i4rTaDAa3QDgECxnvcpyKkZ1mRhb3p_ZeFSxT9OQoVBDMSss2Be8tp8fR2x1hld-gc"));
//		this.http.getCookieStore().addCookie(new BasicClientCookie("GDSESS", "ID=eaf8ab93f88fbba7:TM=1361784458:C=c:IP=119.96.125.230-:S=APGng0vliHp2QWfryh4X1CPydduy8oSJmg"));
		//set proxy
//		HttpHost proxy = new HttpHost("54.235.130.128", 8888);
//		this.http.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
	
	public static void main(String[] args) {
		String url1 = "http://www.google.com.hk/sorry/Captcha?continue=http%3A%2F%2Fwww.google.com.hk%2Fsearch%3Fhl%3Dzh-CN%26newwindow%3D1%26c2coff%3D1%26safe%3Dstrict%26q%3Dhttpclient%2Bcookie%26oq%3DHttpClient%26gs_l%3Dserp.1.3.0l10.904408.908122.4.911203.11.8.0.3.3.0.198.817.6j2.8.0...0.0...1c.4.4.serp.Pzfl550-FM0%26bav%3Don.2%2Cor.r_gc.r_pw.r_cp.%26bvm%3Dbv.42768644%2Cd.dGI%26biw%3D1440%26bih%3D774%26ech%3D1%26psi%3DMtgmUcuLLJGfkgXSpIHoBg.1361500241636.9%26emsg%3DNCSR%26noj%3D1%26ei%3DgtgmUaztFMXomAWCvIDwAQ&id=1706335489159388426&captcha=14135123&submit=%CC%E1%BD%BB";
		String url2 = "http://www.google.com.hk/search?hl=zh-CN&newwindow=1&c2coff=1&safe=strict&q=httpclient+cookie&oq=HttpClient&gs_l=serp.1.3.0l10.904408.908122.4.911203.11.8.0.3.3.0.198.817.6j2.8.0...0.0...1c.4.4.serp.Pzfl550-FM0&bav=on.2,or.r_gc.r_pw.r_cp.&bvm=bv.42768644,d.dGI&biw=1440&bih=774&ech=1&psi=MtgmUcuLLJGfkgXSpIHoBg.1361500241636.9&emsg=NCSR&noj=1&ei=gtgmUaztFMXomAWCvIDwAQ";
//		String url = "http://www.google.com.hk/search?hl=zh-CN&q=test";
//		String url3 = "http://www.google.com.hk/search?h1=zh-CN&q=test1";
//		String url1 = "http://www.baidu.com";
//		String url2 = "http://www.sogou.com";
//		CookieStore cookieStore = new BasicCookieStore();
//		CookieTest test = new CookieTest();
//		test.init();
//		test.configHttp();
//		test.http.setCookieStore(cookieStore);
//		test.executeHttp(url1);
//		
////		test.executeHttp(url3);
//		test.executeHttp(url1);
//		System.out.println(test.http.getCookieStore());
//		CookieTest test1 = new CookieTest();
//		test1.init();
//		test1.configHttp();
//		test1.executeHttp(url2);
//		System.out.println(test1.http.getCookieStore());
//		test1.http.getCookieStore().addCookie(test.http.getCookieStore().getCookies().get(0));
////		test1.http.setCookieStore(test.http.getCookieStore());
//		System.out.println(test1.http.getCookieStore());
////		test.executeHttp(url1);

		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				CookieTest test1 = new CookieTest();
				test1.type = "a";
				test1.init();
				test1.configHttp();
				for(int i = 0; i < 10000; i++){
					test1.executeHttp("http://www.google.com.hk/search?h1=zh-CN&q=testa" + i);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		Thread thread2 = new Thread(new Runnable(){
			public void run(){
				CookieTest test1 = new CookieTest();
				test1.type = "b";
				test1.init();
				test1.configHttp();
				for(int i = 0; i < 10000; i++){
					test1.executeHttp("http://www.google.com.hk/search?h1=zh-CN&q=testb" + i);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
//		thread1.start();
		thread2.start();
	}
}
