package patternparser.wm;


public class AtomicPattern {

	public AtomicPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	private Pattern pattern;
	public UnionPattern belongUnionPattern;

	public UnionPattern getBelongUnionPattern() {
		return belongUnionPattern;
	}

	public void setBelongUnionPattern(UnionPattern belongUnionPattern) {
		this.belongUnionPattern = belongUnionPattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public boolean findMatchInString(String str) {
		if (this.pattern.str.length() > str.length())
			return false;
		int beginIndex = str.length() - this.pattern.str.length();
		String eqaulLengthStr = str.substring(beginIndex);
		if (this.pattern.str.equalsIgnoreCase(eqaulLengthStr))
			return true;
		return false;
	}
}
