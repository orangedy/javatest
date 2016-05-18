package test;

import java.util.List;

import org.junit.Test;

public class TypeTest {

    public static void main(String[] args) {

        testDivision();
    }

    static void testNum() {
        int i = 0;
        boolean b = false;
//      b = (boolean)i;
//      i = b;

        short s1 = 1;
//      s1 = s1 + 1;

        float f = 3.4f;
        float f2 = (float) 1.1;

        double d = 3.4;

        // null可以转换为任何引用类型
        Object obj = null;
        List str = (List) obj;
        System.out.println(str);

        // 取余测试
        int num = -1;
        System.out.println(num % 10);

    }

    static void testDivision() {
        // 测试整出
        double a = 40;
        double b = 20;
        double c = a / b;
        System.out.println("c===>" + c); // 1.75
        System.out.println("c===>" + Math.ceil(c)); // 2.0
        System.out.println(Math.floor(c)); // 1.0
    }

    @Test
    public void testTime() {
        long current = System.currentTimeMillis();
        System.out.println(current - current % 86400000);
    }
}
