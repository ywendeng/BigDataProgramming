package cn.ywdeng.basic.thread;
/**
 * @author ywdeng
 * @date 2017年2月6日
 * @Title: ThreadWithImpliment.java
 * @Description: 使用实现接口的方式来实现多线程
 */
public class ThreadWithImpliment implements Runnable{

	@Override
	public void run() {
	  String threadName=Thread.currentThread().getName();
	  System.out.println("线程 "+threadName+" 调用run()方法");
	  for(int i=0;i<20;i++){
		  System.out.println(threadName+" "+i);
		  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	  }
	}
 public static void main(String[] args) {
	//在创建线程对象的时候和继承Thread方法不同=------使用的静态代理模式
	 Thread th1=new Thread(new ThreadWithImpliment(),"线程1");
	 Thread th2=new Thread(new ThreadWithImpliment(),"线程2");
	 th1.start();
	 th2.start();
}
}
