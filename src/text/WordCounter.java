package text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class WordCounter {
	
	// 淇濆瓨鏂囩珷鐨勫唴瀹�
	String content;

	// 淇濆瓨缁熻鍚庣殑鍗曡瘝闆嗗悎
	String[] words;
	// 淇濆瓨鍗曡瘝瀵瑰簲鐨勮瘝棰�
	int[] wordFreqs;
	
	private final char SPACE = ' ';
	
	public WordCounter(){
		
	}
	
	public List<Word> getTopWords(File file, int length, int freq, int top){
		String text = readWhole(file);
		return getTopWords(text, length, freq, top);
	}
	
	public List<Word> getTopWords(String text, int length, int freq, int top){
		
		// 淇濆瓨鍒嗗壊鍚庣殑鍗曡瘝闆嗗悎
		String[] rawWords = this.splitWord(text);
		List<Word> wordList = this.countWordFreq(rawWords);
		List<Word> result = this.sort(wordList, length, freq, top);
		this.printResult(result);
		
		return result;
	}
	
	public static String readWhole(File file) {
		return readWhole(file, "gbk");
	}

	public static String readWhole(File file, String encoding){
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			byte[] byteBuf = new byte[inStream.available()];
			inStream.read(byteBuf);
			return new String(byteBuf, encoding);
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	protected String[] splitWord(String content) {

//		content = content.replace('\'', SPACE).replace(',', SPACE)
//				.replace('.', SPACE);
//		content = content.replace('(', SPACE).replace(')', SPACE)
//				.replace('-', SPACE);
//		String[] rawWords = content.split("\\s+");
		// System.out.print("misson 3 completed");
		return content.split("\\W+");
	}

	// 缁熻璇嶏紝閬嶅巻鏁扮粍
	protected List<Word> countWordFreq(String[] rawWords) {
		//
		Set<String> set = new TreeSet<String>();

		for (String word : rawWords) {
			set.add(word);
		}

		Iterator<String> ite = set.iterator();

		List<String> wordsList = new ArrayList<String>();
		List<Integer> freqList = new ArrayList<Integer>();

		while (ite.hasNext()) {
			String word = (String) ite.next();

			int count = 0;
			// 缁熻鐩稿悓瀛楃涓茬殑涓暟
			for (String str : rawWords) {
				if (str.equals(word)) {
					count++;
				}
			}

			wordsList.add(word);
			freqList.add(count++);
		}

		// 瀛樺叆鏁扮粍褰撲腑
		words = wordsList.toArray(new String[0]);

		wordFreqs = new int[freqList.size()];
		for (int i = 0; i < freqList.size(); i++) {
			wordFreqs[i] = freqList.get(i);
		}
		
		List<Word> wordList = new ArrayList<Word>();

		for (int i = 0; i < words.length; i++) {
			wordList.add(new Word(words[i], wordFreqs[i]));
		}
		
		return wordList;
	}
	
	protected List<Word> sort(List<Word> wordList, int length, int freq, int top) {
		Collections.sort(wordList, new WordComparator());

		List<Word> result = new ArrayList<Word>();
		for (int i = 0, m = 1; i < wordList.size() && m <= top; i++) {
			Word word = wordList.get(i);
			if (isFilter(word, length, freq)){
				result.add(word);
				m++;
			}

		}
		return result;
	}
	
	private boolean isFilter(Word word, int length, int freq){
		return (freq > 0 && word.getFreq() >= freq ) && (length > 0 && word.getWord().length() >= length ); 
	}
	
	
	// 灏嗘帓搴忕粨鏋滆緭鍑�
	public void printResult(List<Word> wordList) {

		int index = 0;
		for(Word word : wordList){
			System.out.println((++index) + "# " + word.toString());
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WordCounter counter = new WordCounter();
		counter.getTopWords(new File("bigtext.txt"), 7, 7, 50);

	}

}
