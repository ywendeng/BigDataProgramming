package com.ywdeng.basic.netty.sendobject.bean;

import java.io.Serializable;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: Person.java
 * @Description: Java bean对象,需要实现序列化,从而便于在网络之间的数据传输
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String sex;
	private int age;

	public String toString() {
		return "name:" + name + " sex:" + sex + " age:" + age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
