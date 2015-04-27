package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PatternTest {
    public static void main(String[] args) {
        String str = "aaaaaabb";
        Matcher m = Pattern.compile("(.)\\1+").matcher(str);
        while (m.find()) {
            System.out.println(m.group(1));
            System.out.println(m.groupCount());
        }
        str = m.replaceAll("$1");
        System.out.println(str);
    }

    @Test
    public void testMatch() {
        String testStr = "abc";
        System.out.println(testStr.matches("a.*"));

        String testStr2 = "2014/1/9";
        System.out.println(testStr2.matches("^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}"));
    }

    @Test
    public void testReplace() {
        String str = "<@ href=\"ntesfa://circleUserCenter?userId=10126\">@一个不存在 da fdf as dfsaf>";
        String str2 = "<@ href=\"ntesfa://circleUserCenter?userId=10159\">@urstest_lqd<@ href=\"ntesfa://circleUserCenter?userId=10115\">@ni<@ href=\"ntesfa://circleUserCenter?userId=10163\">@测试昵称<@ href=\"ntesfa://circleUserCenter?userId=10160\">@urstestb<@ href=\"ntesfa://circleUserCenter?userId=10126\">@一个不存在<@ href=\"ntesfa://circleUserCenter?userId=10150\">@qaperftest007393272<@ href=\"ntesfa://circleUserCenter?userId=10106\">@火了<@ href=\"ntesfa://circleUserCenter?userId=10111\">@51urstestm咯哦无聊";
        String str3 = "a<@xxxc>d";
        String str4 = "a b c";
        str = str.replaceAll("<@ href=[^>]+>", "");
        str2 = str2.replaceAll("<@ href=[^>]+>", "");
        str3 = str3.replaceAll("<@ href=[^>]+>", "");
        str4 = str4.replaceAll("[a-z] ", "a");
        System.out.println(str);
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(str4);
    }
}
