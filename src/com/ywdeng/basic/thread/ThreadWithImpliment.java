package com.ywdeng.basic.thread;

import java.util.Random;

/**
 * @author ywdeng
 * @date 2017年2月6日
 * @Title: ThreadWithImpliment.java
 * @Description: 使用实现接口的方式来实现多线程-----使用多线程来100售票
 */
public class ThreadWithImpliment implements Runnable {
	private int tickets = 100;
	// 此处flag作为状态标志，在sychronized 中一个线程对其的修改能够保证另一个线程的可见性
	private boolean flag = true;

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();

		while (flag) {
			synchronized (this) {
				// 如果还有剩余的票，则就售票
				if (tickets > 0) {
					System.out.println("线程 " + threadName + " 出售了第"
							+ (100 - tickets + 1) + " 张票");
					--tickets;
					System.out.println("当前系统还剩余" + tickets + "张票");
				} else
					// 如果票已经售完之后跳出循环
					flag = false;
			}

			try {
				// 使得当前线程随机睡上一定时间
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// 创建一个Runnnable接口实现类对象，并将其传入Thread类中创建多个线程，实现多个
		// 线程对同一份资源的共享
		ThreadWithImpliment t = new ThreadWithImpliment();
		Thread th1 = new Thread(t, "线程1");
		Thread th2 = new Thread(t, "线程2");
		th1.start();
		th2.start();
	}
}
