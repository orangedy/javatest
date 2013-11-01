package serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Administrator
 * 测试点：序列化和反序列化过程
 * 反序列化时，是不需要调用构造函数来进行对象创建的，而是直接通过序列化协议来在内存中创建对象
 * 如果反序列化化的超类没有实现Serializable接口，那么则会调用超类的默认构造函数来先构造父类对象，如果没有则会出错
 */
class Base {
	public String s = "Base String";

	//必须要有无参构造函数，或者实现序列化接口
	public Base(){
		System.out.println(s);
	}
	
	public Base(int a) {
		System.out.println(s);
	}
}

public class Son extends Base implements Serializable {

	public String s = "Child String";

	public Son() {
		super();
		System.out.println(s);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Son s = new Son();
		System.out.println("----------------------");
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("t.tmp");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Son s1=null;
		try {
			fis = new FileInputStream(new File("t.tmp"));
			ois = new ObjectInputStream(fis);
		    s1 = (Son) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
