package text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BigWordCounter extends WordCounter {
	
	public List<Word> getTopWords(File file, int length, int freq, int top){
		
		Map<String,Word> map = new HashMap<String,Word>();
		
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			while((line=br.readLine())!=null) {
				processWords(line, map, length);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(br != null)
					br.close();
				if(fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Collection<Word> values = map.values();
		List<Word> wordList = new ArrayList<Word>();
		for(Word w : values){
			wordList.add(w);
		}
		List<Word> result = this.sort(wordList, length, freq, top);
		this.printResult(result);
		
		return result;
	}
	
	private void processWords(String content, Map<String,Word> map, int length){
		String[] rawWords = this.splitWord(content);
		countWordFreq(rawWords, map, length);
	}
	
	
	
	private void countWordFreq(String[] rawWords,Map<String,Word> map, int length) {

		for (String word : rawWords) {
			if(word.length() < length){
				continue;
			}
			if(map.containsKey(word)){
				map.get(word).increase();
			} else {
				map.put(word, new Word(word, 1));
			}
		}

	}

	public static void main(String[] args) {
		BigWordCounter counter = new BigWordCounter();
		counter.getTopWords(new File("bigtext.txt"), 7, 7, 500);

	}

}
