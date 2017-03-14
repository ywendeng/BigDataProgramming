package com.ywdeng.basic.lock;

/**
 * @author ywdeng
 * @date 2017年2月19日
 * @Title: MySyschronized.java
 * @Description: 
 */
/**
 * @author ywdeng 内置锁synchronized存在那些问题？
 *         1.在一个线程持有锁时，其它的线程只有一直等待下去,不能实现中断,也不能让申请锁的线程只等待一定的时间
 *         2.如果多个线程都只是进行读操作时，如果一个线程获取锁，其它的线程只有等待 synchronized 优势：
 *         会执行结束或者异常之后都会自动释放锁
 */
public class MySynchronized {
	/**
	 * 使用synchronized 需要注意两个问题 1.确保同步代码块，多个线程申请时，获取到的是同一个锁 2.为了更好的性能需要减小锁粒度
	 */
	public static void main(String[] args) {
		// 同时创建两个线程
		new Thread("Thread1") {
			@Override
			public void run() {
				// 使用Synchronized 来同步代码块
				synchronized (Object.class) {
					System.out.println(this.getName() + " start");
					try {
						Thread.sleep(10000);
						System.out.println(this.getName() + " end");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}.start();

		new Thread("Thread2") {
			@Override
			public void run() {
				// 使用Synchronized 来同步代码块
				synchronized (Object.class) {
					System.out.println(this.getName() + " start");
					try {
						Thread.sleep(10000);
						System.out.println(this.getName() + " end");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}.start();
	}
}
