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
 * java序列化测试
 * @author orangedy
 * @version 2013-9-16 下午5:38:34
 */
public class ObjectSerializer implements Serializable {

	//序列化和反序列化两端要一致，不然会失败
	//如果没有指定则会根据field自动生成（不同的虚拟机可能生成的id不一致，最好指定）
	private static final long serialVersionUID = 4802565404165990407L;
	public String name = "hello";
//	public int age = 24;
	public String address = "hn";
	
	public static void main(String[] args) {
		testCompatibility();
		
	}
	
	/**
	 * 如果存在该方法，则会被反射调用，取代默认的序列化方法，如何序列化可以自己掌控
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
	}
	
	//用于测试增加方法是否影响反序列化
	private void testAddMethod(){
		
	}
	
	/**
	 * 测试将对象序列化后，如果反序列化时，类已经做出了修改，那么是否能够兼容，是否能够成功反序列化
	 * 经过测试，增删属性是不会影响到反序列化的
	 * 增删方法也不影响两端匹配
	 * 修改访问权限也不影响
	 * 但是修改序列化id就会影响（不显示赋值，只要修改属性就会自动修改id）
	 */
	public static void testCompatibility(){
		//ObjectSerializer oldObj = new ObjectSerializer();
		//serialToFile(oldObj);
		//给类增加一个属性，再反序列化
		//给类删除一个属性，在反序列化
		derialFromFile();
	}
	
	public static void serialToFile(ObjectSerializer obj){
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(new File("object.txt")));
			ObjectSerializer testObj = new ObjectSerializer();
			output.writeObject(testObj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void derialFromFile(){
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(new File("object.txt")));
			ObjectSerializer testObj2 = (ObjectSerializer) input.readObject();
			System.out.println(testObj2.name);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
