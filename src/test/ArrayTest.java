package test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class ArrayTest {

    public static void main(String[] args) {
        String[][] a = new String[10][];
        String[] b = new String[10];
        a[0] = new String[5];
        a[1] = new String[6];
        System.out.println("a.length=" + a.length + " a[1].length=" + a[1].length);

        // array init test
        char[] ch = new char[5];
        System.out.println(ch[1]);

        int[] in = new int[5];
        System.out.println(in[1]);

        String[] str = new String[5];
        System.out.println(str[1]);

        final Object[] obj = new Object[5];
        obj[0] = new Object();
        System.out.println(obj[1]);

        // String store test
        String literal = "abc";
        String newStr = new String("abc");
        System.out.print("literal == newStr:");
        System.out.println(literal == newStr);

        String intern = (literal + "d").intern();
        String literal2 = (newStr + "d").intern();
        System.out.println(intern == literal2);

        int x = 1;
        int y = 1;
        System.out.println(x == y);

        System.out.println(tryTest());

    }

    // finally return value test
    public static Date tryTest() {
        Date a = new Date(1, 1, 1);
        try {
            System.out.println("d");
            return a;
        } finally {
            a.setDate(2);
        }
    }

    @Test
    public void testToString() {
        int[] intArr = new int[] {1, 2, 3};
        System.out.println(intArr);
        List<Integer> intList = new LinkedList<Integer>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        System.out.println(intList);
    }

    @Test
    public void newArray() {
        String[] array = {"a", "b"};
        String result = StringUtils.join(new String[] {"a", "b", "c"});
        System.out.println(result);
    }
}
