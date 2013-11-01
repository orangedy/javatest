package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListTest implements I1{
	
	public Object o;
	
	public void someMethod(){
		
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		// for(String str : list){
		// list.remove("str");
		// // list.add(str + str);
		// }
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String temp = it.next();
			System.out.println(temp);
			// list.remove(temp);
			it.remove();
		}
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(null == null);
		System.out.println(I1.NAME);
		
		Object[] array = new Object[4];
		System.out.println(array.getClass());
		System.out.println(Object.class);
		System.out.println(array.getClass().getClasses().length);
		System.out.println(ListTest.class.getClasses().length);
	}
}

interface I1 {
	String NAME = "codemonkeyism";
	
	void someMethod();
}
