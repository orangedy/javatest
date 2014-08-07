package test;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.zip.DataFormatException;


import javax.xml.stream.XMLStreamException;

public class ExceptionTest {
	
	public static void throwsExcepiton(){
		System.out.println("hello");
		//throw new Exception("error");
		try{
			FileInputStream file = new FileInputStream("aaa");
			DateFormat a = DateFormat.getInstance();
			a.parse(null);
		} catch(Exception e){
			//e.printStackTrace();
			//throw new XMLStreamException("error here\r\n");
			System.out.println("catch here");
		} finally{
			System.out.println("finally excuted");
		}
		System.out.println("is continue\r\n");
	}

	public static void main(String[] args) {
		try {
			ExceptionTest.throwsExcepiton();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("continue");
	}
}
