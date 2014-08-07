package patternparser.wm;

public class Pattern { 
	public Pattern(String str) {
		this.str = str;
	}


	public String str;

	public String getStr() {
		return str;
	}
	

	public char charAtEnd(int index) {
		if (str.length() > index) {
			return str.charAt(str.length() - index - 1);
		} else
			return 0;
	}
}
