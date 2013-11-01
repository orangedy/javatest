package text;

public class Word {
	private String word;
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	private int freq;
	
	public void increase(){
		this.freq++;
	}

	public Word(String word, int freq) {
		this.word = word;
		this.freq = freq;
	}
	
	public String toString(){
		return word + "-" + freq;
	}
}
