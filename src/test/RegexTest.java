package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

    public static void main(String[] args) {
        testBackslash();
    }

    /**
     * 反斜杠不能直接出现在源代码中，因为反斜杠被当做java的转义字符，它后面必须要跟一个的字符搭配表示一个特殊的字符
     * 合法的包括：valid ones are \b \t \n \f \r \" \' \\ ，其他的搭配会报错，(但是\0-7是可以的)，这里是\加8进制
     */
    public static void testBackslash() {

        String backSlash = "\\";
        String singleQuotes = "\'";
        String doubleQuotes = "\"";
        char cBackSlash = '\\';
        char slash = '/';
        char dot = '.';
        System.out.println(cBackSlash + ", " + backSlash);

        char other = '\101';
        System.out.println(other + " is " + (int) other);

        char zero = 48;
        int num = 48;
        char a = 97;
        char A = 65;
        char chinese = '汉';
        System.out.println("char 48 is " + zero + ", and int 48 is " + num + ", and char == int is " + (zero == num));
    }

    /**
     * 正则表达式中要匹配到\符合，则必须要用\\\\，首先编译结束后其实剩下\\，这个在正则表达式中对\进行转义
     * 所以这里存在两次转义，一是java本身的字符转义（java本身的），而是正则表达式的转义（所以语言通用的）
     */
    public static void testSlashRegex() {
        String backslash = "a\\b";
        String[] subs = backslash.split("\\\\");
        for (String sub : subs) {
            System.out.println(sub);
        }
    }

    /**
     * pattern.matches()只有当完全匹配是才会为true，略坑
     */
    @Test
    public void testPattern() {
        String testStr = " 2014-12-31 14:00:13,507 10143818 [ajp-nio-8009-exec-264] (FlyGameFrontendController.java:268) INFO  $$$$ IP_ADDR [24.212.219.209] URS [475365656@qq.com] RESULT [0]";
        Pattern pattern = Pattern.compile("IP_ADDR\\s?\\[([0-9\\.]*)\\]");
        Matcher matcher = pattern.matcher(testStr);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }

        System.out.println("a".matches("."));
        Pattern pattern1 = Pattern.compile(".");

        Matcher matcher1 = pattern1.matcher("a");
        Matcher matcher2 = pattern1.matcher("ab");
        System.out.println(matcher1.matches()); // return true
        System.out.println(matcher2.matches()); // return false

//        String str1 = "\r\n" + "\t\t\t\t" + "                            阿正的艺人档案" + "\r\n";
        String str1 = "         \r\n                   阿正的艺人档案";
        System.out.println(str1.matches("[\\s\\S]*"));
    }

    @Test
    public void testNullAndBlack() {
        String test = "abc";
        Pattern pattern = Pattern.compile("^\\s*$|^null$");
        Matcher matcher1 = pattern.matcher(test);
        System.out.println(matcher1.matches());
    }

    @Test
    public void testMultiMatch() {
        Pattern pattern = Pattern.compile("(\\[.*?\\]\\[(.*?)\\]\\s?)");
        String testStr = "match:[3178][射] [3179][家伙]";
        Matcher matcher = pattern.matcher(testStr);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }
}
