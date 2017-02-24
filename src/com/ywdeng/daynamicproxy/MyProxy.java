package com.ywdeng.daynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ywdeng
 * @date 2017年2月24日
 * @Title: MyProxy.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 实现一个代理类
 */
public class MyProxy implements InvocationHandler {
	//根据用户传入的类创建一个代理类
	Object obj;
	public Object bland(Object object){
	    this.obj=object;
	    //获取输入类对象的加载器和接口的主要目的是为了使得代理对象和有相同的接口方法
		return Proxy.newProxyInstance(object.getClass().getClassLoader()
				, object.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result;
		System.out.println("文状元书店开业,欢迎大家光临......");
		    result=method.invoke(obj,args); 
		System.out.println("感谢你下次光临，请慢走......");
		return result;
	}
 
}
