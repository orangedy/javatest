package test;

public class RegexTest {
	
	public static void main(String[] args) {
		testBackslash();
	}

	/**
	 * 反斜杠不能直接出现在源代码中，因为反斜杠被当做java的转义字符，它后面必须要跟一个的字符搭配表示一个特殊的字符
	 * 合法的包括：valid ones are \b \t \n \f \r \" \' \\ ，其他的搭配会报错，(但是\0-7是可以的)，这里是\加8进制
	 */
	public static void testBackslash(){
		
		String backSlash = "\\";
		String singleQuotes = "\'";
		String doubleQuotes = "\"";
		char cBackSlash = '\\';
		char slash = '/';
		char dot = '.';
		System.out.println(cBackSlash + ", " + backSlash);
		
		char other = '\101';
		System.out.println(other + " is " + (int)other);
		
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
	public static void testSlashRegex(){
		String backslash = "a\\b";
		String[] subs = backslash.split("\\\\");
		for(String sub : subs){
			System.out.println(sub);
		}
	}
}
