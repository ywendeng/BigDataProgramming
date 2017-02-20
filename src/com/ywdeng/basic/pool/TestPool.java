package com.ywdeng.basic.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ywdeng
 * @date 2017年2月20日
 * @Title: TestPool.java
 * @Description: 
 */
/**
 * @author ywdeng 如果不使用线程池，在多线程的环境下存在哪些问题？ 1. 线程的创建和销毁开销非常高 2.
 *         消耗资源---占用内存，大量闲置的线程占用内存的同时还会给垃圾回收器带来压力 3. 稳定性
 *         线程池的位于java.util.concurrent.Executor接口下 |-----ExecutorService
 *         子接口新增了一些线程池声明周期的管理方法 |------ThreadPoolExecutor 主要的实现类
 *         |------ScheduleThreadExecutorService 线程的创建和调度接口
 *         |-----scheduleThreadPoolExecutor 线程的创建和调度实现类
 *         在创建线程通常是使用工具类Executors来创建
 */
public class TestPool {

	public static void main(String[] args) {
		int cpuNum = getHandlerNum();
		// 创建一个线程池，并初始化线程的个数
		ExecutorService pool = Executors.newFixedThreadPool(cpuNum);
		// 多个线程共同处理一份资源
		for (int i = 0; i < 12; i++) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println("thread name"+ Thread.currentThread().getName());
					try {
						//让线程睡上1000毫秒
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});
		}
		//线程池需要关闭
		pool.shutdown();
	}

	// 获取当前机器的处理器个数
	private static int getHandlerNum() {
		return Runtime.getRuntime().availableProcessors();
	}
}
