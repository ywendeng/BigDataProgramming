package com.ywdeng.basic.netty.sendobject.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: ByteObjConverter.java
 * @Description: 实现byte[]到Object对象之间的相互转换
 */
public class ByteObjConverter {

	/**
	 * @param obj
	 * @return byte[]
	 *  将对象转换为byte 数组
	 */
	public static byte[] objectToByte(Object obj){
		byte[] bytes=null;
		//在内存中创建缓冲区。所有送往"流"的数据都要放置在此缓冲区中
	    ByteArrayOutputStream bo=new ByteArrayOutputStream();
	    ObjectOutputStream oo=null;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
		    bytes=bo.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		  //需要将流关闭
		  try {
			oo.close();
			bo.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}	
		}
		return bytes;
	}
	/**
	 * 将byte[]转换为 object对象
	 */
	public static Object byteToObject(byte[] dist){
		Object obj=null;
		ByteArrayInputStream bi=new ByteArrayInputStream(dist);
		ObjectInputStream oi=null;
		try {
			oi=new ObjectInputStream(bi);
		    obj=oi.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		  try{
			  oi.close();
			  bi.close();
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		}
		
		return obj;
		
	}

}
