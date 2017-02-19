package com.ywdeng.basic.reflect;

import java.io.Serializable;

/**
 * @author ywdeng
 * @date 2017年2月18日
 * @Title: Person.java
 * @Description:声明一个JavaBean类，同时定义了多种构造方法
 */
public class Person implements Serializable {
	private Long id;
	public String name;

	public Person() {
		this.id = 100L;
		this.name = "afsdfasd";
	}

	public Person(Long id, String name) {
		// super();
		this.id = id;
		this.name = name;
	}

	public Person(Long id) {
		super();
		this.id = id;
	}

	@SuppressWarnings("unused")
	private Person(String name) {
		super();
		this.name = name + "=======";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}

	private String getSomeThing(String s) {
		return "sdsadasdsasd";
	}

	private void testPrivate() {
		System.out.println("this is a private method");
	}
}
