package com.ywdeng.basic.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * @author ywdeng
 * @date 2017年2月24日
 * @Title: TestBuffer.java
 * @Description: 
 */
/**
 * @author ywdeng
 * nio 非阻塞式IO
 * 1.缓冲区(buffer):在Java Nio 中负责数据的存取-------其底实现为数组
 * 根据数据类型的不同提供了相应类型的缓冲区
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 上述缓冲区的管理方法几乎一致，通过allocate()获取缓冲区
 * 2.从缓冲区中存取数据的两个核心方法 
 * put(): 将数据放入到缓冲区中
 * get(): 从缓冲区中获取数据
 * 3.缓冲中的四个核心属性
 * capacity:容量
 * limit: 在读或写模式下可以访问的区域
 * position: 指针位置
 * mark: 用于标当前location的位置，通过reset()恢复到mark位置
 * 0<= mark <= location <= limit <= capacity
 * 4.缓冲区的类型
 *  - 非直接缓冲区 ：通过allocate 分别缓冲区，将缓冲区建立在JVM的内存中
 *  - 直接缓冲你区： 通过allocateDirct 分别缓冲区，将缓冲区建立在物理内存中，可以提高效率
 * 
 */

public class TestBuffer {
	@Test
	public void test2(){
		String str="abcd";
		ByteBuffer b=ByteBuffer.allocate(1024);
	    b.put(str.getBytes());
	    //声明一个byte数组,用于存储从缓冲区中取出的数组
	    TestBuffer.toString(b);
	    //必须要设置读模式
	    b.flip();
	    byte[] dist=new byte[b.limit()];
	    b.get(dist,0,2);
	    //使用mark 来标记当前的position的位置，便于读取之后，使得position的位置重新回到起点
	    System.out.println(b.position());
	    b.mark();
	    b.get(dist,2,2);
	    System.out.println(new String(dist));
	    System.out.println(b.position());
	    //使用reset使得position恢复到之前的mark标记的位置
	    b.reset();
	    System.out.println(b.position());
	   //判断缓冲区中是否还有剩余的元素,如果有则获取剩余原始的数量
	    if(b.hasRemaining())
	    	System.out.println("在缓冲区中的剩余元素的数量： "+b.remaining());
	   
	}
	@Test
	public void test1(){
		// 创建一个1024字节的字节缓冲区
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		// 缓冲区为写模式
		System.out.println("------put------");
		byte[] b="Hello".getBytes();
		buffer.put(b);
		TestBuffer.toString(buffer);
		System.out.println("-------get-----");
		// 将缓冲区装换为读模式
		buffer.flip();
		TestBuffer.toString(buffer);
		byte[] dist=new byte[buffer.limit()];
		//读取缓冲区中的数据存储到字符数组中
        buffer.get(dist);
		System.out.println(new String(dist,0,dist.length));
		TestBuffer.toString(buffer);
		//设置重复读------position的位置又重新元素的起始位置上
		buffer.rewind();
		TestBuffer.toString(buffer);
	}
    public static void toString(Buffer buffer){
	  System.out.println("capacity: "+buffer.capacity()+" limit: "+buffer.limit()+
				" position: "+buffer.position());
  }
}
