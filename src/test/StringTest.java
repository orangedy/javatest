package test;

import java.io.UnsupportedEncodingException;

public class StringTest {

    private static final String MESSAGE = "taobao";

    public static void main(String[] args) {
        String addTest = 123L + "123";
        System.out.println(addTest);
        String a = "tao" + "bao";
        String b = "tao";
        String c = "bao";
        System.out.println(a == MESSAGE);
        System.out.println((b + c) == MESSAGE);

        Counter counter = new Counter();
        System.out.println(counter.increment());
        System.out.println(counter.anotherIncrement());
        Counter counterb = new Counter();
        System.out.println(counterb.increment());
        System.out.println("***************测试编码长度***************");
        StringTest.getByteLength("我们ABC");

        System.out.println("*************test replace function");
        testReplace();

        String test = "1	拐/v	2.0969100130080562";
        String[] result = test.split("	");
        System.out.println(result.length);
    }

    public static void getByteLength(String str) {
        for (int i = 0; i < str.length(); i++) {
            byte[] b;
            try {
                b = (str.charAt(i) + "").getBytes("GBK");
                System.out.println(b.length);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void testReplace() {
        String testStr = "abcdbc";
        String source = "a.";
        String replace = "";
        testStr = testStr.replace(source, replace);
        System.out.println(testStr);
    }
}

class Counter {
    private static int count = 0;

    public int increment() {
        return count++;
    }

    public int anotherIncrement() {
        return ++count;
    }
}
