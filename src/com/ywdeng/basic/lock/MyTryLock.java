package com.ywdeng.basic.lock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ywdeng
 * @date 2017年2月19日
 * @Title: MyTryLock.java
 * @Description: 
 */
/**
 * @author ywdeng 使用lock 中的trylock方法，来获取锁(可以等待一定的时间)，如果获取到了琐则返回true 否则返回false
 */
public class MyTryLock {
	private static ArrayList<Integer> array = new ArrayList<>();
	private static Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		new Thread("Thread1") {
			public void run() {
				boolean th1=false;
				try {
					 th1 = lock.tryLock(20L, TimeUnit.MILLISECONDS);
					// 如果获取到锁则返回true
					if (th1) {
						System.out.println(this.getName()+" 线程获得了琐");
						for (int i = 0; i < 20; i++)
							array.add(i);
                        this.sleep(200);
					}
				} catch (InterruptedException e) {
					System.out.println("等待线程被中断");
				}finally{
					if (th1){
					lock.unlock();
					System.out.println(this.getName()+"  线程释放了琐");
					}
				}
			}
		}.start();
		new Thread("Thread2") {
			public void run() {
                boolean th2=false;
				try {
					 th2 = lock.tryLock(20L, TimeUnit.MILLISECONDS);
					// 如果获取到锁则返回true
					if (th2) {
						System.out.println(this.getName()+" 线程获得了琐");
						for (int i = 20; i < 20; i++)
							array.add(i);
                        this.sleep(200);
					}else {
						System.out.println("当前线程没有获取到锁");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					if (th2){
						lock.unlock();
						System.out.println(this.getName()+"  线程释放了琐");
						}
				}
			}
		}.start();
	}

}
