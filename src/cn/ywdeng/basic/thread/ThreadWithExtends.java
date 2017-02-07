package cn.ywdeng.basic.thread;

import java.util.Random;

/**
 * @author ywdeng
 * @date 2017年2月6日
 * @Title: ThreadWithExtends.java
 * @Description: 使用继承的方式创建多线程
 */
public class ThreadWithExtends extends Thread {
	//设置一个变量
	private String flag=null;
	public ThreadWithExtends(String flag){
		this.flag=flag;
	}
	
	public void run(){
	 String threadName=Thread.currentThread().getName();
	 System.out.println(threadName+"线程的run 方法被调用.....");
	 Random random=new Random();
	 for (int i =0;i<=20; i++){
		 //使线程随机睡眠一段时间
		 try {
			Thread.sleep(random.nextInt(10)*100);
			System.out.println(threadName+"  "+flag);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
	 
	}
   public static void main(String[] args) {
  ThreadWithExtends	thread1=new ThreadWithExtends("线程1");
  ThreadWithExtends thread2=new  ThreadWithExtends("线程2");
  //启动线程
  thread1.start();
  thread2.start();
}
}
