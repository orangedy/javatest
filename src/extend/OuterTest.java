package extend;

/**
 * 静态内部类相当于外部类的一个静态属性，它可以访问到外部类的静态属性，而不能访问非静态属性
 * 它的实例化也不需要先实例化外部类
 * 与之相对的是成员内部类，相对于外部类的成员属性
 * 
 * @author Administrator
 *
 */
class Outer{
	static int i = 10;
	int j = 11;
    static class Inner{
    	public void useOuter(){
    		System.out.println(i);
    		//System.out.println(j);  语法报错
    	}
    }
}

public class OuterTest {
    public static void main(String[] args){
        Outer.Inner in = new Outer.Inner();
        in.useOuter();
        System.out.println("for test");
    }
} 