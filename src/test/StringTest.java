package test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class StringTest {

    private static final String MESSAGE = "taobao";

    public static void main(String[] args) {
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
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReplaceAll() {
        String originStr = "abc211";
        String newsStr = originStr.replaceAll(".", "*");
        System.out.println(newsStr);
    }

    @Test
    public void testStringLength() {
        String str1 = "";
        System.out.println(str1.length());
        System.out.println(str1 == "");
        System.out.println(str1.equals(""));
    }

    public static void testReplace() {
        String testStr = "abcdbc";
        String source = "a.";
        String replace = "";
        testStr = testStr.replace(source, replace);
        System.out.println(testStr);
    }

    @Test
    public void testSplit() {
        String str = "abc";
        String[] result = str.split("a");
        System.out.println(result.length);

        String addTest = 123L + "123";
        System.out.println(addTest);
    }

    @Test
    public void testStringAdd() {
        String str1 = null;
        String str2 = "a";
        System.out.println(str1 + str2);
    }

    @Test
    public void testEquals() {
        String a = "a";
        String b = null;
        System.out.println(a.equals(b));
    }

    @Test
    public void testNewString() throws UnsupportedEncodingException {
        String str = new String(new byte[0], "utf8");
        System.out.println("result:" + str);
    }

    @Test
    public void testEmoji() {
        String str = "😃";
        String str2 = "😄";
        try {
            byte[] bytes = str.getBytes("UTF-8");
            byte[] bytes2 = str2.getBytes("UTF-8");
            printArray(bytes);
            printArray(bytes2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void printArray(byte[] array) {
        for (byte o : array) {
            System.out.println((o & 0xF0) >> 4);
            System.out.println(o & 0x0F);
        }

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
