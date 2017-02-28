package com.ywdeng.basic.lock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ywdeng
 * @date 2017年2月19日
 * @Title: MyInterrupted.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 可中断锁，如果一个线程在一定的时间中获取不到锁，则中断
 */
public class MyInterruptibly {
	private static MyInterruptibly test= new MyInterruptibly();
	private Lock lock= new ReentrantLock();
    public static void main(String[] args) {
    	Interruputibly th1=test.new Interruputibly("Thread1");
    	Interruputibly th2=test.new Interruputibly("Thread2");
    	th1.start();
    	th2.start();
    	try{
    	Thread.sleep(200);
    	Thread.currentThread().getName();
    	}catch(Exception e){
    	System.out.println(e.getMessage());
    	}
       // 判断线程th2 是否获取到了琐，如果没有则th2发出一个中断信号
    	th2.interrupt();
	}
	private class Interruputibly extends Thread{
		//调用父类方法来实例化线程
		private  Interruputibly(String name){
			super(name);
		}
		public void run(){
			try {
				test.interrupted();
			} catch (InterruptedException e) {
				System.out.println(this.getName()+" 线程被中断");
			}
		}
	}
	public void interrupted() throws InterruptedException{
		lock.lockInterruptibly();// 等待获取锁的线程可以响应中断
		try{
		Long startTime=System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName()+ "线程获取到了同步锁");
		while(true){
			if (System.currentTimeMillis()-startTime>300)
				break;
		}
		}finally{
			lock.unlock();
		     System.out.println(Thread.currentThread().getName()+" 释放了琐");
		}
	}
	
}
