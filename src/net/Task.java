package net;
/** ����ӿ��� **/
//package org.ymcn.util;
import java.io.*;
import java.util.Date;

/**
* ��������ӿ�
* �����������̳з���
* 
* @author obullxl
*/
public abstract class Task implements Runnable {
    // private static Logger logger = Logger.getLogger(Task.class);
    /* ����ʱ�� */
    private Date generateTime = null;
    /* �ύִ��ʱ�� */
    private Date submitTime = null;
    /* ��ʼִ��ʱ�� */
    private Date beginExceuteTime = null;
    /* ִ�����ʱ�� */
    private Date finishTime = null;

    private long taskId;

    public Task() {
        this.generateTime = new Date();
    }

    /**
    * ����ִ�����
    */
    public void run() {
        /**
        * ���ִ�д���
        * 
        * beginTransaction();
        * 
        * ִ�й����п��ܲ����µ����� subtask = taskCore();
        * 
        * commitTransaction();
        * 
        * �����²��������� ThreadPool.getInstance().batchAddTask(taskCore());
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
    * ��������ĺ��� �����ر��ҵ���߼�ִ��֮��
    * 
    * @throws Exception
    */
    //public abstract Task[] taskCore() throws Exception;

    /**
    * �Ƿ��õ����ݿ�
    * 
    * @return
    */
    //protected abstract boolean useDb();

    /**
    * �Ƿ���Ҫ����ִ��
    * 
    * @return
    */
    protected abstract boolean needExecuteImmediate();

    /**
    * ������Ϣ
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
