package com.ywdeng.basic.pool;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ywdeng
 * @date 2017年2月21日
 * @Title: ThreadPoolwithCallable.java
 * @Description: 
 */
public class ThreadPoolwithCallable {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//创建一个数组来存储任务线性的执行状态
	   ArrayList<Future<String>> futures=new ArrayList<Future<String>>();
	   // 创建一个调度线程池
	   ScheduledExecutorService pool=Executors.newScheduledThreadPool(8);
	   for(int i =0; i<12;i++){
		 Future<String> future=pool.schedule(new TaskCallable(10), 10, TimeUnit.SECONDS);
		 futures.add(future);
	   }
	   // 主线程获取任务线程的返回结果
	   for(Future<String> f:futures){
		   boolean done=f.isDone();
		   System.out.println(done?"任务执行完成":"任务没有执行完成");// 判断任务执行完成状态
		   System.out.println("任务线程返回结果"+f.get());//如果未完成时主线程会被阻塞
	   }
	   //关闭线程池
	   pool.shutdown();
	}
}
