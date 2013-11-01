package test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author orangedy
 * @version 2013-4-21 下午11:52:58
 * 
 * DataInputStream读取时，并没有进行字符编码处理，如果输入的流不是采用UTF-16编码，那么读取时会出错
 * 其实现只是按照类型的长度拼凑出一个基本类型，没有考虑编码
 */
public class EncodingTest {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("encodingtest.txt");
		InputStream in = new FileInputStream(file);
		DataInputStream dataIn = new DataInputStream(in);
		try {
			while(true)
				System.out.print(dataIn.readChar());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("");
			e.printStackTrace();
		}
	}
	
	//内部类可以使用public, protected, default, private
	protected class Inner{
		
	}
}
