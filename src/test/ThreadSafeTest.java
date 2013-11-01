package test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author orangedy
 * 集合线程安全测试类
 * Collections.synchronizedMap提供有限的线程安全，即多个线程对集合进行增删操作时不会出错，但是其中一个对集合进行遍历时就有问题
 * 故如果有遍历操作需要手动加synchronized关键字进行同步
 * 全局锁的使用
 * 
 * ConcurrentHashMap保证线程安全
 *
 */
public class ThreadSafeTest {

	public static void main(String[] args) throws InterruptedException {
		Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
//		Map<String, String> map = new ConcurrentHashMap<String, String>();
//		Map<String, String> map = new HashMap<String, String>();

		ThreadA threadA = new ThreadA(map);
		threadA.start();

		while (true) {
			synchronized (map) {
				map.put("delete", "0");
				map.put("delete1", "1");
				map.put("delete2", "2");
				map.put("delete3", "3");
				System.out.println("fatherThread" + map.toString());
			}
			Thread.sleep(100);
		}

	}

}

class ThreadA extends Thread {
	Map<String, String> map;

	public ThreadA(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public void run() {
		while (true) {
			// map.remove("delete");
			// map.remove("delete1");
			// map.remove("delete2");
			// map.remove("delete3");
			// System.out.println("childThread" + map.toString());
			synchronized (map) {
				Iterator<Entry<String, String>> i = map.entrySet().iterator(); // Must be in synchronized
				while (i.hasNext()) {
					i.next();
					i.remove();
				}
				System.out.println("childThread" + map.toString());
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class S {
	public static final Object o = new Object();
}
