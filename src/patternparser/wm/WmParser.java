package patternparser.wm;

import java.util.Vector;


public class WmParser {
	WmParser() {
	}

	private boolean initFlag = false;
	private UnionPatternSet unionPatternSet = new UnionPatternSet();
	private int maxIndex = (int) java.lang.Math.pow(2, 16);
	private int shiftTable[] = new int[maxIndex];
	public Vector<AtomicPattern> hashTable[] = new Vector[maxIndex];
	private UnionPatternSet tmpUnionPatternSet = new UnionPatternSet();

	public static void main(String args[]) {
		WmParser filterEngine = new WmParser();
		filterEngine.addFilterKeyWord("aaa bbb", 1);
		filterEngine.addFilterKeyWord("aaa bbb", 1);
		filterEngine.addFilterKeyWord("你好 我好 大家好", 1);
		filterEngine.addFilterKeyWord("ts", 1);
		filterEngine.addFilterKeyWord("tz", 1);
		filterEngine.addFilterKeyWord("聪明", 1);
		filterEngine.addFilterKeyWord("明慧", 1);
		Vector<Integer> levelSet = new Vector<Integer>();
		filterEngine.parse("你好风大卡我好发电量撒大家好", levelSet);
		System.out.println("http://www.webkaifa.com test:levelSet.size="
				+ levelSet.size());

		levelSet.clear();
		filterEngine.parse("aa×a ×b×bb", levelSet);
		System.out.println("http://www.webkaifa.com test:levelSet.size="
				+ levelSet.size());

		levelSet.clear();
		filterEngine.parse("聪明", levelSet);
		System.out.println("http://www.webkaifa.com test:聪明 levelSet.size="
				+ levelSet.size());

		/* 清除算法引擎，带来的后果是引擎中将找不到任何的字符，测试代码，用来重载关键字时使用！ */
		filterEngine.clear();
		levelSet.clear();
		filterEngine.parse("你好风大卡我好发电量撒大家好", levelSet);
		System.out.println("http://www.webkaifa.com test:levelSet.size="
				+ levelSet.size());// 测试正确结果是找不到匹配字符

		levelSet.clear();
		filterEngine.parse("ts fdsa 你幅度撒堕落", levelSet);
		System.out.println("http://www.webkaifa.com test:tz levelSet.size="
				+ levelSet.size());// 测试正确结果是找不到匹配字符*/
	}

	private boolean addFilterKeyWord(String keyWord, int level) {
		if (initFlag == true)
			return false;
		UnionPattern unionPattern = new UnionPattern();
		String strArray[] = keyWord.split(" ");
		for (int i = 0; i < strArray.length; i++) {
			Pattern pattern = new Pattern(strArray[i]);
			AtomicPattern atomicPattern = new AtomicPattern(pattern);
			unionPattern.addNewAtomicPattrn(atomicPattern);
			unionPattern.setLevel(level);
			atomicPattern.setBelongUnionPattern(unionPattern);
		}
		tmpUnionPatternSet.addNewUnionPattrn(unionPattern);
		return true;
	}

	private boolean isValidChar(char ch) {
		if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z')
				|| (ch >= 'a' && ch <= 'z'))
			return true;
		if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
			return true;
		return false;
	}

	public int parse(String content, Vector<Integer> levelSet) {
		if (initFlag == false)
			init();
		Vector<AtomicPattern> aps = new Vector<AtomicPattern>();
		String preContent = preConvert(content);
		for (int i = 0; i < preContent.length();) {
			char checkChar = preContent.charAt(i);
			if (shiftTable[checkChar] == 0) {
				Vector<AtomicPattern> tmpAps = new Vector<AtomicPattern>();
				tmpAps = findMathAps(preContent.substring(0, i + 1),
						hashTable[checkChar]);
				aps.addAll(tmpAps);
				i++;
			} else
				i = i + shiftTable[checkChar];
		}
		parseAtomicPatternSet(aps, levelSet);
		return 0;
	}

	private void parseAtomicPatternSet(Vector<AtomicPattern> aps,
			Vector<Integer> levelSet) {
		while (aps.size() > 0) {
			AtomicPattern ap = aps.get(0);
			UnionPattern up = ap.belongUnionPattern;
			if (up.isIncludeAllAp(aps) == true) {
				levelSet.add(new Integer(up.getLevel()));
			}
			aps.remove(0);
		}
	}

	private Vector<AtomicPattern> findMathAps(String src,
			Vector<AtomicPattern> destAps) {
		Vector<AtomicPattern> aps = new Vector<AtomicPattern>();
		for (int i = 0; i < destAps.size(); i++) {
			AtomicPattern ap = destAps.get(i);
			if (ap.findMatchInString(src) == true)
				aps.add(ap);
		}
		return aps;
	}

	private String preConvert(String content) {
		String retStr = new String();
		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);
			if (this.isValidChar(ch) == true) {
				retStr = retStr + ch;
			}
		}
		return retStr;
	}

	// shift table and hash table of initialize
	private void init() {
		initFlag = true;
		for (int i = 0; i < maxIndex; i++)
			hashTable[i] = new Vector<AtomicPattern>();
		shiftTableInit();
		hashTableInit();
	}

	public void clear() {
		tmpUnionPatternSet.clear();
		initFlag = false;
	}

	private void shiftTableInit() {
		for (int i = 0; i < maxIndex; i++)
			shiftTable[i] = 2;
		Vector<UnionPattern> upSet = tmpUnionPatternSet.getSet();
		for (int i = 0; i < upSet.size(); i++) {
			Vector<AtomicPattern> apSet = upSet.get(i).getSet();
			for (int j = 0; j < apSet.size(); j++) {
				AtomicPattern ap = apSet.get(j);
				Pattern pattern = ap.getPattern();
				if (shiftTable[pattern.charAtEnd(1)] != 0)
					shiftTable[pattern.charAtEnd(1)] = 1;
				if (shiftTable[pattern.charAtEnd(0)] != 0)
					shiftTable[pattern.charAtEnd(0)] = 0;
			}
		}
	}

	private void hashTableInit() {
		Vector<UnionPattern> upSet = tmpUnionPatternSet.getSet();
		for (int i = 0; i < upSet.size(); i++) {
			Vector<AtomicPattern> apSet = upSet.get(i).getSet();
			for (int j = 0; j < apSet.size(); j++) {
				AtomicPattern ap = apSet.get(j);
				Pattern pattern = ap.getPattern();
				if (pattern.charAtEnd(0) != 0) {
					hashTable[pattern.charAtEnd(0)].add(ap);
				}
			}
		}
	}
}
