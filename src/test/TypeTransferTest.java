package test;

/**
 * @author orangedy
 * @version 2013-5-7 上午12:06:25
 * 
 * 基本类型转换测试类
 * 
 * 基本原则是，小范围到大范围，自动转换，不需要强制转换
 * 大范围到小范围，需要强制转换
 * char类型转换特殊
 * boolean与其他类型不能自动转换
 * 
 * 这里要理解范围大小，而不是类型的位的大小
 */
public class TypeTransferTest {

	public static void main(String[] args) {
		byte b = 1;
		short s = 2;
		int i = 3;
		long l = 4;
		
		char c = 'a';
		
		float f = 1.1f;
		double d = 2.2;
		
		boolean bl = true;
		
		//同一类型中，小范围赋给大范围自动转换，大范围赋给小范围需要强制转换
		l = b;
		l = s;
		l = i;
		
		b = (byte) s;
		b = (byte) i;
		b = (byte) l;
		
		//所有的类型在赋值给char时，都需要强制转化，这里需要注意的是，想byte，short这种比char范围小的也要强制转换
		//而将char赋给其他类型时，遵循大范围到自动转换到小范围，小范围强制转换到大范围的原则，浮点型也一样
		c = (char) b;
		c = (char) s;
		c = (char) i;
		c = (char) l;
		c = (char) f;
		c = (char) d;
		
		b = (byte) c;
		s = (short) c;
		i = c;
		l = c;
		f = c;
		d = c;
		
		//浮点型赋值给整型时，需要强制转换，即使将float赋给l也一样，这里的范围不应该理解为长度，而是数的范围
		//其他类型赋给浮点型，不需要强制转换
		b = (byte) f;
		s = (short) f;
		i = (int) f;
		l = (long) f;
		
		f = b;
		f = s;
		f = i;
		f = l;
		
		b = (byte) d;
		s = (short) d;
		i = (int) d;
		l = (long) d;
		
		d = b;
		d = s;
		d = i;
		d = l;
		
		//浮点型之间的转换
		f = (float) d;
		d = f;
		
		//boolean类型不能与其他类型转换
//		bl = b;
//		bl = s;
//		bl = i;
//		bl = l;
		
//		b = bl;
		//八进制和十六进制表示
		int eight = -010;
		int sixteen = -0x10;
		System.out.println(eight);
		System.out.println(sixteen);
		
	}
}
