package thread;

public class ThreadTest extends Thread implements Runnable {

//	public void run(){
//		System.out.println("run!");
//	}

    /**
     * @param args
     */
    public static void main(String[] args) {
//        new Thread(new ThreadTest()).start();
        System.out.println("father thread start");
        new SonThread().start();
        System.out.println("father thread exit");
    }

}

class SonThread extends Thread {
    private String name;

    public SonThread() {

    }

    public SonThread(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
            System.out.println("do some thing");
        }
    }
}