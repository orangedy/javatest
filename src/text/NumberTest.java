package text;

public class NumberTest {

	public static void numberConvesion(int n, int b){
		int remainder, value = n;
		while(value != 0){
			remainder = value%b;
			value = value/b;
			System.out.println(remainder);
		}
	}
	
	public static void main(String[] args) {
		int n = 121;
		int b = 2;
		NumberTest.numberConvesion(n, b);
	}
}
