package test;

import java.io.File;
import java.io.IOException;

public class FileTest extends Thread {
	
	public int a;
	
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		
		File file = new File("1.txt");
		System.out.println(file.getAbsolutePath());
//		String dir = FileTest.class.getClassLoader().getResource("test/a.txt").toString();
		String dir = FileTest.class.getResource("a.txt").getFile();
		System.out.println(dir);
		File filea = new File(dir);
		System.out.println(filea.getAbsolutePath());
		System.out.println();
		FileTest test = new FileTest();
		test.test();
//		file.mkdir();
//		file.createNewFile();
//		System.out.println(new FileTest().getClass().getClassLoader());
//		System.out.println(new FileTest().getClass().getClasses());
//		System.out.println(new FileTest().getClass().getFields().length);
//		System.out.println(FileTest.class == new FileTest().getClass());
	}
	
	private class Inner{
		
	}
	
	public void test(){
		System.out.println(getClass() == FileTest.class);
	}
}
