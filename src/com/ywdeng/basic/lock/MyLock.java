package com.ywdeng.basic.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ywdeng
 * @date 2017年2月19日
 * @Title: MyLock.java
 * @Description: 
 */
/**
 *显示锁 lock接口。主要实现类reentrentLock 
 * 但是需要注意的是在使用显示锁时，必须要手动释放锁
 */
public class MyLock {
	private static ArrayList<Integer> array= new ArrayList<Integer>();
	//声明一个线程共享锁
	private static Lock lock=new ReentrantLock();
	
	public static void main(String[] args) {
		new Thread("Thread1"){
			public void run(){
		    try{
		     lock.lock();
		     for(int i= 0;i<=5; i++)
		        array.add(i);
				this.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
		      //就算在try模块中的出现异常仍然会释放锁
		      lock.unlock();
		      System.out.println(this.getName()+" 锁释放");
		    }
			}
		}.start();
		
		new Thread("Thread2"){
			public void run(){
		    try{
		     lock.lock();
		     for(int i= 5;i<=10; i++)
		        array.add(i);
				this.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
		      //就算在try模块中的出现异常仍然会释放锁
		      lock.unlock();
		      System.out.println(this.getName()+" 锁释放");
		    }
			}
		}.start();
	}
	

}
