package text;

import java.util.Comparator;


public class WordComparator implements Comparator<Word> {

	public int compare(Word o1, Word o2) {
		Word word1 = (Word) o1;
		Word word2 = (Word) o2;

		if (word1.getFreq() < word2.getFreq()) {
			return 1;
		} else if (word1.getFreq() > word2.getFreq()) {
			return -1;
		} else {

			int len1 = word1.getWord().trim().length();
			int len2 = word2.getWord().trim().length();

			String min = len1 > len2 ? word2.getWord() : word1.getWord();
			String max = len1 > len2 ? word1.getWord() : word2.getWord();

			for (int i = 0; i < min.length(); i++) {
				if (min.charAt(i) < max.charAt(i)) {
					return 1;
				}
				// System.out.print("misson 3 completed");
			}

			return 1;

		}
	}

}
