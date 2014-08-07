package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
	public static void main(String[] args) {
		String str = "aaaaaabb";
		Matcher m = Pattern.compile("(.)\\1+").matcher(str);
		while(m.find()){
			System.out.println(m.group(1));
			System.out.println(m.groupCount());
		}
		str = m.replaceAll("$1");              
		System.out.println(str);
	}
}
