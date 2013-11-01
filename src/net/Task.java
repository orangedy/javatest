package net;
/** 任务接口类 **/
//package org.ymcn.util;
import java.io.*;
import java.util.Date;

/**
* 所有任务接口
* 其他任务必须继承访类
* 
* @author obullxl
*/
public abstract class Task implements Runnable {
    // private static Logger logger = Logger.getLogger(Task.class);
    /* 产生时间 */
    private Date generateTime = null;
    /* 提交执行时间 */
    private Date submitTime = null;
    /* 开始执行时间 */
    private Date beginExceuteTime = null;
    /* 执行完成时间 */
    private Date finishTime = null;

    private long taskId;

    public Task() {
        this.generateTime = new Date();
    }

    /**
    * 任务执行入口
    */
    public void run() {
        /**
        * 相关执行代码
        * 
        * beginTransaction();
        * 
        * 执行过程中可能产生新的任务 subtask = taskCore();
        * 
        * commitTransaction();
        * 
        * 增加新产生的任务 ThreadPool.getInstance().batchAddTask(taskCore());
        */
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	StringBuffer s = new StringBuffer();
    	this.setFinishTime(new Date());
    	s.append(this.taskId + " generateTime:"
    			+ this.getGenerateTime() + "submitTime:"
    			+ this.getSubmitTime() + "beginExceuteTime:"
    			+ this.getBeginExceuteTime() + "finishTime:"
    			+ this.getFinishTime());
    	String s1 = new String(s);
    	try {
			BufferedWriter f1 = new BufferedWriter(new FileWriter("dy.txt", true));
			f1.write(s1, 0, s1.length());
			f1.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			//f1.close();
		}
    	//BufferedWriter log = new BufferedWriter(new FileWrite(dy.txt, true));
    }

    /**
    * 所有任务的核心 所以特别的业务逻辑执行之处
    * 
    * @throws Exception
    */
    //public abstract Task[] taskCore() throws Exception;

    /**
    * 是否用到数据库
    * 
    * @return
    */
    //protected abstract boolean useDb();

    /**
    * 是否需要立即执行
    * 
    * @return
    */
    protected abstract boolean needExecuteImmediate();

    /**
    * 任务信息
    * 
    * @return String
    */
    public abstract String info();

    public Date getGenerateTime() {
        return generateTime;
    }

    public Date getBeginExceuteTime() {
        return beginExceuteTime;
    }

    public void setBeginExceuteTime(Date beginExceuteTime) {
        this.beginExceuteTime = beginExceuteTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

}
