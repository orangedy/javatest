package net;

import java.net.*;

import java.io.*;

import java.util.Date;
import java.util.Properties;

import java.util.Enumeration;

/**
 * Http�ͻ��˳����Ѽ�����Java�����У�����ͨ��URLConnection����á��ź���
 * 
 * �ǣ�����SUNû�й���Http�ͻ������Դ�룬��ʵ�ֵ�ϸ������һ���ա����ĸ���HTTP
 * 
 * Э��淶����Java.net.Socket��ʵ��һ��HTTPЭ��ͻ��˳���.
 * 
 * 
 * <pre>
 * 
 * 
 *  1.Socket��:
 * 
 *  �˽�TCP/IPЭ�鼯ͨ�ŵĶ���֪����Э����ͨ����ͨ��Socket��ɵġ���
 * 
 *  Java.net���У�Socket����Ƕ�Socket�ľ���ʵ�֡���ͨ�����ӵ������󣬷���һ��
 * 
 *  I/O����ʵ��Э������Ϣ������
 * 
 * 
 *  2 . HTTPЭ��
 * 
 *  HTTPЭ��ͬ����TCP/IPЭ�鼯�е�Э��һ��������ѭ�ͻ�/������ģ�͹����ġ���
 * 
 *  ���˷�������˵���Ϣ��ʽ����:
 * 
 *  ------------------------------
 * 
 *  ���󷽷� URL HTTPЭ��İ汾��
 * 
 *  �ύ��Ԫ��Ϣ
 * 
 * *����**
 * 
 *  ʵ��
 * 
 *  ------------------------------
 * 
 *  ���󷽷��Ƕ�������ӹ�����˵����ĿǰHTTPЭ���Ѿ���չ��1.1�棬������GET��
 * 
 *  HEAD��POST��DELETE��OPTIONS��TRACE��PUT���֡�Ԫ��Ϣ�ǹ��ڵ�ǰ�������Ϣ��ͨ
 * 
 *  ������Ԫ��Ϣ�����Լ��ʵ�������Ƿ����������չ����Ƿ���������Ƿ�ƥ��ȡ�Ԫ
 * 
 *  ��Ϣ������ʹHTTPЭ��ͨ�Ÿ������׿ɿ���ʵ��������ľ������ݡ�
 * 
 *  ���������ķ���Web������������ɹ���Ӧ���ʽ����:
 * 
 *  --------------------------------
 * 
 *  HTTPЭ��İ汾�� Ӧ��״̬�� Ӧ��״̬��˵��
 * 
 *  ���յ�Ԫ��Ϣ
 * 
 * *����**
 * 
 *  ʵ��
 * 
 *  --------------------------------
 * 
 *  ���ϱ��ķ���ͻ��ˣ����ҽ��ճɹ����˴˼�ر����ӣ����һ�����֡�
 * 
 *  ��������õ�GET��������˵������ı���Ӧ��
 * 
 *  ----------------------------------
 * 
 *  GET http://www.youhost.com HTTP/1.0
 * 
 *  accept: www/source; text/html; image/gif; image/jpeg; *��*
 * 
 *  User_Agent: myAgent
 * 
 * *����**
 * 
 *  -----------------------------------
 * 
 *  �����������www.youhost.com��������һ��ȱʡHTML�ĵ����ͻ���HTTPЭ��汾
 * 
 *  ����1.0�棬Ԫ��Ϣ�����ɽ��յ��ļ���ʽ���û�����ÿһ��֮���ûس����з���
 * 
 *  ���������һ�����н�������������������ִ�й����������������������´���:
 * 
 *  ------------------------------------
 * 
 *  HTTP/1.1 200 OK
 * 
 *  Date: Tue, 14 Sep 1999 02:19:57 GMT
 * 
 *  Server: Apache/1.2.6
 * 
 *  Connection: close
 * 
 *  Content-Type: text/html
 * 
 * *����**
 * 
 *  <html><head>...</head><body>...</body></html>
 * 
 *  ------------------------------------
 * 
 *  HTTP/1.1��ʾ���HTTP��������1.1�棬200�Ƿ������Կͻ������Ӧ��״̬�룬OK
 * 
 *  �Ƕ�Ӧ��״̬��Ľ��ͣ�֮��������ĵ���Ԫ��Ϣ���ĵ����ġ�(���Ӧ��״̬���Ԫ
 * 
 *  ��Ϣ�Ľ��������Inetrnet��׼�ݰ�:RFC2616)��
 * 
 * 
 *  ע: ������ֻʵ��GET��HEAD��POST���ַ��������������򲻳�ʹ�ã����Һ��ԡ�
 * </pre>
 */

public class Http {
	public static void main(String[] args) {
		Http temp = new Http("http://www.baidu.com/");
		int a = -1;
		try {
			while ((a = temp.getInputStream().read()) != -1) {
				System.out.print((char) a);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected Socket client;

	protected BufferedOutputStream sender;

	protected BufferedInputStream receiver;

	protected ByteArrayInputStream byteStream;

	protected URL target;

	private int responseCode = -1;

	private String responseMessage = "";

	private String serverVersion = "";

	private Properties header = new Properties();

	public Http() {
	}

	public Http(String url) {

		GET(url);

	}

	/* GET��������URL���������ļ������ݿ��ѯ������������н���ȶ������� */

	public void GET(String url) {

		try {

			System.out.print(new Date());
			System.out.print("\n");
			checkHTTP(url);

			openServer(target.getHost(), target.getPort());
			System.out.print(new Date());
			System.out.print("\n");

			String cmd = "GET " + getURLFormat(target) + " HTTP/1.1\r\n"
					+ "Host: " +

					target.getHost() + "\r\n" + getBaseHeads() + "\r\n";

			sendMessage(cmd);
			System.out.print(new Date());
			System.out.print("\n");

			receiveMessage();
			System.out.print(new Date());
			System.out.print("\n");

		}

		catch (ProtocolException p) {

			p.printStackTrace();

			return;

		}

		catch (UnknownHostException e) {

			e.printStackTrace();

			return;

		}

		catch (IOException i) {

			i.printStackTrace();

			return;

		}

	}

	/*
	 * 
	 * HEAD����ֻ����URL��Ԫ��Ϣ��������URL���������ɱ����ͷ������ϵ�
	 * 
	 * �ļ���ͬ��������������������Ч��
	 */

	public void HEAD(String url) {

		try {

			checkHTTP(url);

			openServer(target.getHost(), target.getPort());

			String cmd = "HEAD " + getURLFormat(target) + " HTTP/1.0\r\n" +

			getBaseHeads() + "\r\n";

			sendMessage(cmd);

			receiveMessage();

		}

		catch (ProtocolException p) {

			p.printStackTrace();

			return;

		}

		catch (UnknownHostException e) {

			e.printStackTrace();

			return;

		}

		catch (IOException i) {

			i.printStackTrace();

			return;

		}

	}

	/*
	 * 
	 * POST��������������������ݣ��Ա������������Ӧ�Ĵ���������ҳ�ϳ��õ�
	 * 
	 * �ύ���
	 */

	public void POST(String url, String content) {

		try {

			checkHTTP(url);

			openServer(target.getHost(), target.getPort());

			String cmd = "POST " + getURLFormat(target) + " HTTP/1.0\r\n" +

			getBaseHeads();

			cmd += "Content-type: application/x-www-form-urlencoded\r\n";

			cmd += "Content-length: " + content.length() + "\r\n\r\n";

			cmd += content + "\r\n";

			sendMessage(cmd);

			receiveMessage();

		}

		catch (ProtocolException p) {

			p.printStackTrace();

			return;

		}

		catch (UnknownHostException e) {

			e.printStackTrace();

			return;

		}

		catch (IOException i) {

			i.printStackTrace();

			return;

		}

	}

	protected void checkHTTP(String url) throws ProtocolException {

		try {

			URL target = new URL(url);

			if (target == null ||

			!target.getProtocol().toUpperCase().equals("HTTP")) {

				throw new ProtocolException("�ⲻ��HTTPЭ��");

			}

			this.target = target;

		}

		catch (MalformedURLException m) {

			throw new ProtocolException("Э���ʽ����");

		}

	}

	/*
	 * 
	 * ��Web���������ӡ����Ҳ���Web��������InetAddress������UnknownHostException
	 * 
	 * �쳣����Socket����ʧ�ܣ�������IOException�쳣��
	 */

	protected void openServer(String host, int port)
			throws UnknownHostException,

			IOException {

		header.clear();

		responseMessage = "";

		responseCode = -1;

		if (client != null) {

			closeServer();

		}

		if (byteStream != null) {

			byteStream.close();

			byteStream = null;

		}

		// �˴��ɽ�DNS���棬���ж�ĳhost��Ӧ��IP�Ƿ���ڣ�ͨ����ͬ�ķ�ʽ����InetAddress����
		InetAddress address = InetAddress.getByName(host);

		client = new Socket(address, port == -1 ? 80 : port);

		client.setSoTimeout(200000);

		sender = new BufferedOutputStream(client.getOutputStream());

		receiver = new BufferedInputStream(client.getInputStream());

	}

	/* �ر���Web������������ */

	protected void closeServer() throws IOException {

		if (client == null) {

			return;

		}

		try {

			client.close();

			sender.close();

			receiver.close();

		}

		catch (IOException i) {

			throw i;

		}

		client = null;

		sender = null;

		receiver = null;

	}

	protected String getURLFormat(URL target) {

		String spec = "http://" + target.getHost();

		if (target.getPort() != -1) {

			spec += ":" + target.getPort();

		}

		return target.getFile();

	}

	/* ��Web�������������� */

	protected void sendMessage(String data) throws IOException {

		sender.write(data.getBytes(), 0, data.length());

		sender.flush();

	}

	/* ��������Web������������ */

	protected void receiveMessage() throws IOException {

		byte data[] = new byte[1024];

		int count = 0;

		int word = -1;

		// ������һ��

		while ((word = receiver.read()) != -1) {

			if (word == '\r' || word == '\n') {

				word = receiver.read();

				if (word == '\n') {

					word = receiver.read();

				}

				break;

			}

			if (count == data.length) {

				data = addCapacity(data);

			}

			data[count++] = (byte) word;

		}

		String message = new String(data, 0, count);

		int mark = message.indexOf(32);

		serverVersion = message.substring(0, mark);

		while (mark < message.length() && message.charAt(mark + 1) == 32) {

			mark++;

		}

		responseCode = Integer.parseInt(message.substring(mark + 1, mark += 4));

		responseMessage = message.substring(mark, message.length()).trim();

		// Ӧ��״̬��ʹ�����������

		switch (responseCode) {

		case 400:

			throw new IOException("��������");

		case 404:

			throw new FileNotFoundException(getURLFormat(target));

		case 503:

			throw new IOException("������������");

		}

		if (word == -1) {

			throw new ProtocolException("��Ϣ�����쳣��ֹ");

		}

		int symbol = -1;

		count = 0;

		// ����Ԫ��Ϣ

		while (word != '\r' && word != '\n' && word > -1) {

			if (word == '\t') {

				word = 32;

			}

			if (count == data.length) {

				data = addCapacity(data);

			}

			data[count++] = (byte) word;

			parseLine: {

				while ((symbol = receiver.read()) > -1) {

					switch (symbol) {

					case '\t':

						symbol = 32;

						break;

					case '\r':

					case '\n':

						word = receiver.read();

						if (symbol == '\r' && word == '\n') {

							word = receiver.read();

							if (word == '\r') {

								word = receiver.read();

							}

						}

						if (word == '\r' || word == '\n' || word > 32) {

							break parseLine;

						}

						symbol = 32;

						break;

					}

					if (count == data.length) {

						data = addCapacity(data);

					}

					data[count++] = (byte) symbol;

				}

				word = -1;

			}

			message = new String(data, 0, count);

			mark = message.indexOf(':');

			String key = null;

			if (mark > 0) {

				key = message.substring(0, mark);

			}

			mark++;

			while (mark < message.length() && message.charAt(mark) <= 32) {

				mark++;

			}

			String value = message.substring(mark, message.length());

			header.put(key, value);

			count = 0;

		}

		System.out.print(new Date());
		System.out.print("header\n");

		// �����������

		int contentlength = Integer.parseInt((String) header
				.get("Content-Length"));
		while ((word = receiver.read()) != -1) {

			System.out.print(new Date());
			System.out.print("first\n");

			if (count == data.length) {

				data = addCapacity(data);

			}

			data[count++] = (byte) word;

			if (count == contentlength) {
				break;
			}

		}
		System.out.print(new Date());
		System.out.print(count + "\n");

		if (count > 0) {

			byteStream = new ByteArrayInputStream(data, 0, count);
			System.out.print(new Date());
			System.out.print("body\n");

		}

		data = null;

		closeServer();

	}

	public String getResponseMessage() {

		return responseMessage;

	}

	public int getResponseCode() {

		return responseCode;

	}

	public String getServerVersion() {

		return serverVersion;

	}

	public InputStream getInputStream() {

		return byteStream;

	}

	public synchronized String getHeaderKey(int i) {

		if (i >= header.size()) {

			return null;

		}

		Enumeration enumss = header.propertyNames();

		String key = null;

		for (int j = 0; j <= i; j++) {

			key = (String) enumss.nextElement();

		}

		return key;

	}

	public synchronized String getHeaderValue(int i) {

		if (i >= header.size()) {

			return null;

		}

		return header.getProperty(getHeaderKey(i));

	}

	public synchronized String getHeaderValue(String key) {

		return header.getProperty(key);

	}

	protected String getBaseHeads() {

		// String inf =
		// "User-Agent: ZealHttp/1.0\r\nAccept: www/source; text/html; image/gif; */*\r\n";
		String inf = "User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n";

		return inf;

	}

	private byte[] addCapacity(byte rece[]) {

		byte temp[] = new byte[rece.length + 1024];

		System.arraycopy(rece, 0, temp, 0, rece.length);

		return temp;

	}

}