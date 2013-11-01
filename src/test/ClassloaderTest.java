package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassloaderTest {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args)  throws Exception{  
        //调用class加载器，system classloader
        ClassLoader cl = ClassloaderTest.class.getClassLoader(); 
        System.out.println(cl);  
        System.out.println(cl == ClassLoader.getSystemClassLoader());
        for(URL url : ((URLClassLoader) cl).getURLs()){
        	System.out.println(url);
        }
        
        
        //c1的classloader，ext classloader
        ClassLoader c2 = cl.getParent();
        System.out.println(c2);
        for(URL url : ((URLClassLoader) c2).getURLs()){
        	System.out.println(url);
        }
        
        //调用上一层Class加载器，boot classloader
        ClassLoader c3 = c2.getParent();
        ClassLoader c1Loader = cl.getClass().getClassLoader();
        ClassLoader c2Loader = c2.getClass().getClassLoader();  
        System.out.println(c1Loader);  
        System.out.println(c2Loader);
        System.out.println(c3);
        
        //自定义类加载器
        MyClassLoader myClassLoader = new MyClassLoader();
        Class otherClass = myClassLoader.loadClass("httpclient.AutoVote");
        System.out.println(otherClass.getClassLoader());
        
        //通过双亲委派由system classloader加载
        Class baseClass = myClassLoader.loadClass("test.Base");
        System.out.println(baseClass.getClassLoader());
        Object base = baseClass.newInstance();
        
        //不通过双亲委派，自己加载
        MyClassLoader1 myClassLoader1 = new MyClassLoader1();
        Class baseClass1 = myClassLoader1.loadClass("test.Base");
        
        //通过Class.forName加载，区别在于该方式会多一个link的过程，或检查该类的依赖，进而加载依赖的类
        //而使用ClassLoader只是直接加载指定类，其他类并没有加载（这里也不能这么说，应该也是会加载其他类，只不过没有这么全）
        //注意这里的initialize参数应该为true才会执行link过程
        Class baseClass2 = Class.forName("test.Base", true, myClassLoader1);
        System.out.println(baseClass2.getClassLoader());
        
        System.out.println(baseClass1.getClassLoader());
        //这里会执行link过程，因为依赖Sub类，所以需要加载Sub类
        Object base1 = baseClass1.newInstance();
        base = (Base)base1;
    }  
}

/**
 * 自定义类加载器，符合双亲委派规则，默认仍会先通过父加载器加载
 * 效果在于可以加载不在父加载器范围类的class，即非classpath下的class
 * 注意只是加载的class的name不能与classpath下有同名的类，内容是否相同没关系，只要名字相同就会优先加载classpath下的类
 * @author orangedy
 * @version 2013-9-18 下午5:26:33
 */
class MyClassLoader extends ClassLoader{
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException{
		File file = new File(name.split("\\.")[1] + ".class");
		byte[] classData = new byte[1024*10];
		int length = 0;
		try {
			FileInputStream in = new FileInputStream(file);
			length = in.read(classData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(length == 0){
			throw new ClassNotFoundException();
		}
		return defineClass(name, classData, 0, length);
	}
}

/**
 * 自定义类加载器，覆盖了双亲委托机制，直接由自己加载指定的类，不委托给父加载器
 * 
 * @author orangedy
 * @version 2013-9-18 下午5:53:18
 */
class MyClassLoader1 extends ClassLoader{
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		if(name.contains("test.")){
			File file = new File(name.split("\\.")[1] + ".class");
			byte[] classData = new byte[1024*10];
			int length = 0;
			try {
				FileInputStream in = new FileInputStream(file);
				length = in.read(classData);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(length == 0){
				throw new ClassNotFoundException();
			}
			return defineClass(name, classData, 0, length);
		}else{
			return super.loadClass(name);
		}
	}
}


