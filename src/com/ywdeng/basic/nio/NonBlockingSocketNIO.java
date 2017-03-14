package com.ywdeng.basic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

/**
 * @author ywdeng
 * @date 2017年2月26日
 * @Title: NonBlockingSocketNIO.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 使用NIO完成网络通信的三个关键
 * １.通道（channel）:java.nio.channels.Channel 接口,其子类主要如下
 *        |---SelectableChannel子接口
 *            |---SocketChannel
 *            |---ServerSocketChannel
 *            |---DatagramChannel 
 *            
 *            |---Pipe.SourceChannel 
 *            |---Pipe.SinkChannel
 *2.缓冲区(buffer):主要负责对数据的存取
 *3. 选择器（selector）:SelectableChannel 多路复用器，用于监控selectableChannel的IO状况
 * ---------------------------------------------------------------------------
 *  实现功能：公共聊天室的模拟实现         
 * 
 */
public class NonBlockingSocketNIO {
  //客户端
  @Test
  public void client() throws IOException{
	SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("localhost",8999)); 
    //设置为非阻塞模式
	socketChannel.configureBlocking(false);
    ByteBuffer buf=ByteBuffer.allocate(1024);
    //资源读取器 
    Scanner  scan=new Scanner(System.in);
    //如果用户输入内容,则将其写入缓冲区中,并写入通道中
    while(scan.hasNext()){
    	String  str=scan.next();
    	buf.put(str.getBytes());
        buf.flip();
        socketChannel.write(buf);
        buf.clear();
    }
    //关闭通道
    socketChannel.close();
  }
  //服务端
  @Test
  public void  server() throws IOException{
	  /**
	   * 实现非阻塞式通讯的核心是selectableChannel
	   * 1.创建selector 
	   * 2.将通道注册到选择器上，通过选择器来监管通道的状态
	   */
	  //获取通道
	  ServerSocketChannel sChannel=ServerSocketChannel.open();
	  sChannel.bind(new InetSocketAddress(8999));
	  //将通道切换为非阻塞式
	  sChannel.configureBlocking(false);
	  //设置缓冲区的大小
	  ByteBuffer buf=ByteBuffer.allocate(1024);
	  //获取选择器
	  Selector selector=Selector.open();
	  //将服务器端通道注册到选择器上并监听接收状态
	  sChannel.register(selector, SelectionKey.OP_ACCEPT);
	  //轮询获取选择器上已经"准备就绪的"事件
	  while(selector.select()>0){
		  //获取准备就绪的事件(获取选择器中所有选择键)
		  Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
		  while(iterator.hasNext()){
			  SelectionKey sk=iterator.next();
			  //如果该事件是接受事件
			  if(sk.isAcceptable()){
				  //接收来自客户端socketChannel通道
				  SocketChannel socketChannel=sChannel.accept();
				  //将socketChannel转换为非阻塞模式
				  socketChannel.configureBlocking(false);
				  //将该通道注册到选择器上
				  socketChannel.register(selector, SelectionKey.OP_READ);
			  }else if(sk.isReadable()){
				SocketChannel socketChannel=(SocketChannel) sk.channel();
				//将通道中的数据取到写入到缓冲区
				int len=0;
				while((len=socketChannel.read(buf))>0){
					buf.flip();
					System.out.println(new String(buf.array(),0,len));
					buf.clear();
				}
				
			  }
			 
		  }
		  //在就绪事件执行完成之后需要移除
		  iterator.remove();
	  }  
  }
}
