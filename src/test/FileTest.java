package test;

import java.io.File;
import java.io.IOException;

public class FileTest extends Thread {
	
	public int a;
	
	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		
		//java io接口相对路径是相对于user.dir的，通常是项目根目录
		//一般部署后单独存储的文件可以用这种方式或者绝对路径读取
		File file = new File("1.txt");
		System.out.println(file.getAbsolutePath());
//		String dir = FileTest.class.getClassLoader().getResource("test/a.txt").toString();
		//classloader加载的相对路径是相对于classpath的，通常是项目bin目录或者target/classes目录即编译后文件目录
		//一般同代码一起打包的文件通过这种方式读取
		String dir = FileTest.class.getResource("/").getFile();
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
