package com.ywdeng.basic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;
/**
 * @author ywdeng
 * @date 2017年2月26日
 * @Title: TestBlockingSocketNIO.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 使用NIO实现阻塞式的Socket编程
 *  1、通道(channel)
 *  2、缓冲区(buffer)
 *  ------------------------------
 *  实现功能：
 *  1.将本地文件上传到服务端，并在服务端保存
 *  2.服务端成功接收之后放回给客户端确认消息
 *  
 */
public class BlockingSocketNIO {
   //客户端实现
   @Test
   public void client() throws IOException{
	  //打开指定的远程端Ip地址，并返回通道
	  SocketChannel schannel=SocketChannel.open(new InetSocketAddress("localhost",8899));
	  //使用fileChannel通道从本地获取获取文件地址
	  FileChannel inChannel=FileChannel.open(Paths.get("C:\\Users\\JimG\\Desktop","Git.txt"), StandardOpenOption.READ);
	 
	  //声明一个缓冲区用于存储数据
	  ByteBuffer buf=ByteBuffer.allocate(1024);
	  while(inChannel.read(buf)!=-1){
		  buf.flip();
		  schannel.write(buf);
		  buf.clear();
	  }
	  //发送完成之后使用需要shutdownInput表示发送完成
	 schannel.shutdownInput();
	 //从通道中读取数据到缓冲区中
	 int len=0;
	 //通道的read()方法返回读取到字符的长度
	 while((len=schannel.read(buf))!=-1){
		 buf.flip();
		 //输出读取的内容
		 System.out.println(new String(buf.array(),0,len));
		 buf.clear();
	 }
	  //关闭通道
	 inChannel.close();
	 schannel.close();
	  
   }
   //服务端实现
   @Test 
   public void server() throws IOException{
	   ServerSocketChannel serverChannel=ServerSocketChannel.open();
	   //服务端绑定对应的端口
	   serverChannel.bind(new InetSocketAddress(8899));
	   //声明一个文件输出通道
	   FileChannel outChannel=FileChannel.open(Paths.get("C:\\Users\\JimG\\Desktop","remote.txt"),
	    		StandardOpenOption.WRITE,StandardOpenOption.CREATE);
	    
	   ByteBuffer buf=ByteBuffer.allocate(1024);
	    while(true){
	    	//获取客户端请求
	    	SocketChannel sChannel=serverChannel.accept();
	        //在此处进行业务处理
	    	while(sChannel.read(buf)!=-1){
	    		buf.flip();
	    		//将缓冲区中的数据写入为文件通道中
	    		outChannel.write(buf);
	    		//将缓冲区清空
	    		buf.clear();
	    	}
	    	//在接受完成客户端请求之后需要返回确认消息
	    	buf.put("文件接收成功！".getBytes());
	    	//将缓冲区中数据
	    	buf.flip();
	    	sChannel.write(buf);
	    	outChannel.close();
	    	sChannel.close();
	    	
	    }
	   
   }
}
