package com.ywdeng.basic.pool;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * @author ywdeng
 * @date 2017年2月21日
 * @Title: TaskCallable.java
 * @Description: 
 */
/**
 * @author ywdeng
 * Callable 和Runnable接口的区别:
 * Callable 的call方法能够返回值，主线程在获取任务线程的返回值时被阻塞，需要等到任务线程返回结果，
 * 任务线程的执行状态返回结果保存为Future
 * Runnable 的run 方法没有返回值，所以主线程不能获取任务线程的返回值
 * 
 */
public class TaskCallable implements Callable<String>{
   private int s;
   private Random random=new Random();
   public TaskCallable(int s) {
   this.s =s ;
}
	@Override
	public String call() throws Exception {
		String name=Thread.currentThread().getName();
		long currentenTime = System.currentTimeMillis();
		System.out.println(name +" 线程启动时间"+ currentenTime/1000);
		int reint =random.nextInt(3);
		try {
			Thread.sleep(reint*1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return s+"";
	}




}
