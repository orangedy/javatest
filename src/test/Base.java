package test;

import java.util.HashMap;
import java.util.Map;


public class Base{
	
	private void doSomething(){
		System.out.println("base");
	}
	
	public void test(){
		doSomething();
	}
	
	public static void main(String[] args) {
		Base a  = new Sub();
//		a.doSomething();
		a.test();
		System.out.println(a.getClass().getName());
		
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("1", 1);
		Integer value = map.get("1");
		value++;
//		map.put("1", i);
		System.out.println(map.get("1"));
		
	}
}

class Sub extends Base{
//	@Override
	private void doSomething(){
		System.out.println("sub");
	}
	
	public void test(){
		doSomething();
	}
	
//	public int doSomething(int a){
//		return 0;
//	}
}