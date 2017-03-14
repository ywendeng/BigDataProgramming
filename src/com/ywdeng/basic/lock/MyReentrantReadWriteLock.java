package com.ywdeng.basic.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ywdeng
 * @date 2017年2月19日
 * @Title: MyReentrantReadWriteLock.java
 * @Description: 
 */
public class MyReentrantReadWriteLock {
	//注意ReentrantReadWriteLock 是一个独立接口
	 private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
     
	    public static void main(String[] args)  {
	        final MyReentrantReadWriteLock test = new MyReentrantReadWriteLock();
	         
	        new Thread(){
	            @Override
				public void run() {
	                test.get();
	                test.write();
	            };
	        }.start();
	         
	        new Thread(){
	            @Override
				public void run() {
	                test.get();
	                test.write();
	            };
	        }.start();
	         
	    }  
	    
	    public void get() {
	    	// 获取读锁-----实现读写分离,读是共享的
	        rwl.readLock().lock();
	        try {
	            long start = System.currentTimeMillis();
	             
	            while(System.currentTimeMillis() - start <= 1) {
	                System.out.println(Thread.currentThread().getName()+"正在进行读操作");
	            }
	            System.out.println(Thread.currentThread().getName()+"读操作完毕");
	        } finally {
	            rwl.readLock().unlock();
	        }
	    }
	    public void write() {
	        rwl.writeLock().lock();;
	        try {
	            long start = System.currentTimeMillis();
	             
	            while(System.currentTimeMillis() - start <= 1) {
	                System.out.println(Thread.currentThread().getName()+"正在进行写操作");
	            }
	            System.out.println(Thread.currentThread().getName()+"写操作完毕");
	        } finally {
	            rwl.writeLock().unlock();
	        }
	    }
}

