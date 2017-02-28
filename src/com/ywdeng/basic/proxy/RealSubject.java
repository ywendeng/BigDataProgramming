package com.ywdeng.basic.proxy;
/**
 * @author ywdeng
 * @date 2017年2月24日
 * @Title: RealSubject.java
 * @Description: 
 */
public class RealSubject implements Subject {

	@Override
	public void sailBook() {
		System.out.println("计算机图灵");
		
	}

}
