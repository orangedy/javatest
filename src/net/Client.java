package net;

import java.io.*;
import java.net.*;

/**
 * �򵥵�Socket�ͻ��� ����Ϊ�������ַ�����Hello�����������ˣ�����ӡ���������˵ķ���
 */
public class Client {
	public static void main(String[] args) {
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		// ��������IP��ַ
		String serverIP = "127.0.0.1";
		// �������˶˿ں�
		int port = 10000;
		// ��������
		String data = "Hello";
		try {
			// ��������
			socket = new Socket(serverIP, port);
			// ��������
			os = socket.getOutputStream();
			os.write(data.getBytes());
			// ��������
			is = socket.getInputStream();
			byte[] b = new byte[1024];
			int n = is.read(b);
			// �����������
			System.out.println("������������" + new String(b, 0, n));
		} catch (Exception e) {
			e.printStackTrace(); // ��ӡ�쳣��Ϣ
		} finally {
			try {
				// �ر���������
				is.close();
				os.close();
				socket.close();
			} catch (Exception e2) {
			}
		}
	}
}