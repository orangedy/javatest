package test;

import java.util.Random;

/**
 * @author orangedy ���test��Ҫ�ǲ��ԣ���ĳ��������Ҫ�����ö��ʱ���ǰ���������ԣ�����ÿ��ʵ����Ȼ���������գ��ĸ����á�
 *         �Ӳ��Խ������ ���ٶ��������Ը��죬���Ķ�����ʵ������Σ�����Ƕ�����ѭ��������ԣ�Ч����ǰ�߲�࣬�������Ƕ�����ѭ���ڵģ�
 *         ���ܲ��ܴ� 
 *         ��ѡ������˵���ֲ�������������������㣬������������գ�����ҲҪ����ִ�в��ԣ���ѭ���ⶨ�壬ִ�ж�Σ�����ѭ���ڶ��壬
 *         ÿ�δ���Ч�����á� 
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
