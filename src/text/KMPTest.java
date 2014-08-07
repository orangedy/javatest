package text;

public class KMPTest {

	public static void main(String[] args) {
		String a = "aaaab";
		int next[] = new int[a.length() + 1];
		KMPTest.getNext(a, next);
		int next2[] = new int[a.length()];
		KMPTest.getNextInRecursive(a, next2);
		int next3[] = KMPTest.getNextArray(a);
		for(int i = 1; i < next.length; i++){
			System.out.print(next[i] - 1 + " ");
		}
		System.out.println();
		for(int i = 0; i < next2.length; i++){
			System.out.print(next2[i] + " ");
		}
		System.out.println();
		for(int i = 0; i < next3.length; i++){
			System.out.print(next3[i] + " ");
		}
	}
	
	public static void getNext(String T, int next[]){
		int i = 1;
		next[1] = 0;
		int j = 0;
		while(i < T.length()){
			if(j == 0 || T.charAt(i - 1) == T.charAt(j - 1)){
				++i;
				++j;
				next[i] = j;
			}else{
				j = next[j];
			}
		}
	}
	
	public static void getNextInJava(String T, int next[]){
		next[0] = 0;
		int i = 1;
		int preNext = next[i - 1];
		
		while(i < T.length()){
			if(preNext == 0){
				next[i] = 1;
				i++;
				preNext = 1;
			}else if(T.charAt(i - 1) == T.charAt(preNext - 1)){
				next[i] = preNext + 1;
				i++;
				preNext = next[i - 1];
			}else{
				preNext = next[preNext - 1];
			}
		}
	}
	
	public static int[] getNextArray(String T){
		int length = T.length();
		int next[] = new int[length];
		next[0] = -1;
		int preNextValue = -1;
		for(int i = 1; i < length; i++){
			while(preNextValue != -1 && T.charAt(i - 1) != T.charAt(preNextValue)){
				preNextValue = next[preNextValue];
			}
			preNextValue++;
			if(T.charAt(i) == T.charAt(preNextValue)){
				next[i] = next[preNextValue];
			}else{
				next[i] = preNextValue;
			}
		}
		return next;
	}
	
	public static void getNextInRecursive(String T, int next[]){
		int i = 0;
//		next[0] = -1;
		for(; i < next.length; i++){
			next[i] = getMaxMatch(T.substring(0, i + 1));
		}
	}
	
	public static int getMaxMatch(String T){
		int length = T.length();
		if(length == 1){
			return -1;
		}else{
			int preNext = getMaxMatch(T.substring(0, length - 1));
			
			while(preNext > -1 && T.charAt(length - 2) != T.charAt(preNext)){
				preNext = getMaxMatch(T.substring(0, preNext + 1));
			}
			return preNext + 1;
		}
	}
}
