package net;

public class task1 extends Task {
	public static void main(String[] args) {
		ThreadPool threadPool = ThreadPool.getInstance();
		System.out.print(threadPool.getInfo());
		int numTasks = 100;
		for(int i = 0; i < numTasks; i++){
			task1 task = new task1();
			threadPool.addTask(task);
		}
		//threadPool.destroy();
	}
	protected boolean needExecuteImmediate(){
		return true;
	}
	public String info(){
		return "hello";
	}
}
