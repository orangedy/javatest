package thread;

public class ThreadTest  extends Thread implements Runnable{

//	public void run(){
//		System.out.println("run!");
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new ThreadTest()).start();
	}

}
