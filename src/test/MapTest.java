package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author orangedy
 * 集合遍历测试，当遍历过程中需要更改集合结构是，必须使用遍历器的remove方法，而不能直接用集合的remove方法,需先调用next()方法
 * 但是如果要增加元素怎么办？用CopyOnWriteArrayList，它在遍历前保存一份快照，即iterator能改变的，此时不能用iter.remove()方法
 *
 */
public class MapTest {
	public static void main(String[] args) {
		//for map test
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		System.out.println(map.toString());

		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
//			map.put("1", "奇数"); // ConcurrentModificationException
//			map.remove("2"); // ConcurrentModificationException
			it.remove(); // OK
		}
		System.out.println(map.toString());

		// for list test
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		System.out.println(list.toString());
		Iterator<String> itList = list.iterator();
		while(itList.hasNext()){
			String temp = itList.next();
//			list.add("3");  // ConcurrentModificationException
//			list.remove(temp);  //不报异常，但是结果不对
			itList.remove();  //OK
		}
		System.out.println(list.toString());
		
		// for CopyOnWriteArrayList test
		List<String> listC = new CopyOnWriteArrayList<String>();
		listC.add("1");
		listC.add("2");
		System.out.println(listC.toString());
		Iterator<String> itListC = listC.iterator();
		while(itListC.hasNext()){
			String temp = itListC.next();
			listC.add("3");  //OK,但是增加元素，但是iterator不会改变
//			itListC.remove();//error:UnsupportedOperationException
			listC.remove(temp);//OK
		}
		System.out.println(listC.toString());
	}
}
