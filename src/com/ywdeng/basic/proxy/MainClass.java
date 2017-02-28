package com.ywdeng.basic.proxy;

/**
 * @author ywdeng
 * @date 2017年2月24日
 * @Title: MainClass.java
 * @Description: 
 */
public class MainClass {
  public static void main(String[] args) {
	RealSubject realSubject=new RealSubject();
	//获得一个代理对象
	MyProxy proxy= new MyProxy();
	Subject  subject=(Subject) proxy.bland(realSubject);
	subject.sailBook();
}
}
