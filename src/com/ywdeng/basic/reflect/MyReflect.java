package com.ywdeng.basic.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * @author ywdeng
 * @date 2017年2月18日
 * @Title: MyReflect.java
 * @Description:反射机制 
 */
/**
 * @author ywdeng
 * 1.反射机制是什么？
 *  反射机制是指在运行状态中，对任意的一个类都能够知道这个类的属性和方法，对任意一个对象都能够调用它的
 *  方法和属性，对于这种动态获取信息和动态调用对象的方法称为反射机制
 * 2.反射机制能够做什么？
 *  a.在运行时构造一个类的对象
 *  b.在运行时调用任意一个对象的方法
 *  c.生成动态代理
 */
public class MyReflect {

	public static void main(String[] args) throws Exception {
		 /**
		  * 0.通过一个对象获取完整的类名
		  */
		Person person=new Person();
		//获取对象所属的类的类名
		System.out.println(person.getClass().getName());
		/**
		 * 1.实例化class 类对象
		 */
		Class<?> cl1=null;
		//使用  类名.class 或者 Class.forname的两种方式都是同时获得类对象
	    cl1=Class.forName("com.ywdeng.basic.reflect.Person");
	    System.out.println(cl1);
	    System.out.println(MyReflect.class);
	    /**
	     * 2.获取对象的父类或者实现接口
	     */
	    getSuperAndInterface(cl1);
	    /**
	     * 3. 创建一个类对象对应class文件表示的实例对象，底层默认会调用空构造函数
	     */
	    Object obj=getNewInstance(cl1);
	    System.out.println(obj);
	    /**
	     * 4.获取class实例对象对应class文件中构造函数,并通过获取的构造函数来创建对象实例
	     * 与Class类对象直接 创建的实例对象不同之处在于，通过获取带参数甚至是私有的构造器来创建
	     * 实例对象
	     */
	    getConstructor(cl1);
	    /**
	     * 5.访问变量值
	     */
	    getAvariable(cl1);
	    /**
	     * 6.访问反射类对象中的方法
	     */
	    getMethod(cl1);
	    /**
	     * 7. 其它常用的反射方法
	     */
	    otherReflect(cl1);
	    
	}
	
	private static void otherReflect(Class<?> cl1) {
		//判断该Class类实例对象是否是接口
		System.out.println(cl1.isInterface());
		/**
		 * getResourceAsStream这个方法可以获取到一个输入流，
		 * 这个输入流会关联到name所表示的那个文件上。
		 * path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下获取。
		 * 其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。
		 */
	     System.out.println(cl1.getResourceAsStream("log4j.properties"));
	}

	private static void getMethod(Class<?> cl1) throws Exception{
		Constructor<?> cs1=cl1.getConstructor(Long.class,String.class);
		Object obj=cs1.newInstance(24L,"DataMining");
		Method m1=cl1.getMethod("toString");
		// 实现函数调用
		System.out.println(m1.invoke(obj));
		//调用私有方法
		Method m2=cl1.getDeclaredMethod("getSomeThing",String.class);
		m2.setAccessible(true);
		System.out.println(m2.invoke(obj,"ss"));
		
		
	}

	private static void getAvariable(Class<?> cl1) throws Exception{
		/**
		 *成员变量是在对应的类的对象下的，每个类对象都有一份自己的成员变量，
		 *因此，访问成员变量时需要指定相应的对象
		 */
		Constructor<?> cs1=cl1.getConstructor(Long.class,String.class);
		Object obj=cs1.newInstance(24L,"DataMining");
		Field f1=cl1.getField("name");
		//访问对象的实例变量值
		System.out.println(f1.get(obj));
		//设置对象的实例变量值
		f1.set(obj, "python analysis");
		System.out.println(f1.get(obj));
		//访问私用变量
		Field  f2=cl1.getDeclaredField("id");
		f2.setAccessible(true);
		System.out.println(f2.get(obj));
		
	}
	private static void getConstructor(Class<?> cl1) throws Exception {
		Constructor<?> cs1=cl1.getConstructor(Long.class);
		Object  obj =cs1.newInstance(78L);
		System.out.println(obj);
		Constructor<?> cs2=cl1.getDeclaredConstructor(String.class);
		//获取私有的需要set设置访问权限为true才能访问
		cs2.setAccessible(true);
		Object obj2=cs2.newInstance("小强");
		System.out.println(obj2);
		
	}
	private static Object getNewInstance(Class<?> cl1) throws Exception {
		
		return cl1.newInstance();
	}
	@SuppressWarnings("unused")
	private static void getSuperAndInterface(Class<?> cl1){
	     //获得父类
	    Class<?> father=cl1.getSuperclass();
	    System.out.println(father);
	    //获得列接口
	    Class<?> inters[]=cl1.getInterfaces();
	    for(Class inter:inters)
	     System.out.println(inter);
	}
	
}
