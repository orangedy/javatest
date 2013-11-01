package test;

import java.util.Random;

/**
 * @author orangedy 这个test主要是测试，当某个对象需要被运用多次时，是把它变成属性，还是每次实例化然后被垃圾回收，哪个更好。
 *         从测试结果来看 ，速度上是属性更快，它的对象不用实例化多次，其次是定义在循环外的属性，效果更前者差不多，最慢的是定义在循环内的，
 *         性能差距很大。 
 *         从选择上来说，局部变量按尽量定义在里层，更快的垃圾回收，但是也要考虑执行测试，在循环外定义，执行多次，比在循环内定义，
 *         每次创建效果更好。 
 * 
 */
public class MemoryTest {

	private Random random = new Random();

	public static void main(String[] args) {
		MemoryTest test = new MemoryTest();
		Long start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			test.random.nextInt();
		}
		Long finish = System.currentTimeMillis();
		System.out.println(finish - start);

		Long start2 = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			Random ran = new Random();
			ran.nextInt();
		}
		Long finish2 = System.currentTimeMillis();
		System.out.println(finish2 - start2);

		Long start3 = System.currentTimeMillis();
		Random ran = new Random();
		for (int i = 0; i < 100000000; i++) {
			ran.nextInt();
		}
		Long finish3 = System.currentTimeMillis();
		System.out.println(finish3 - start3);
	}
}
