package com.ywdeng.basic.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ywdeng
 * @date 2017年2月22日
 * @Title: ServiceServer.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 使用socket编程,socket服务端用于接收客户端的请求
 */
public class ServiceServer {
   public static void main(String[] args) throws IOException {
	   //创建一个socket对象
	   ServerSocket socket=new ServerSocket();
	   //绑定一个服务地址和端口号
       socket.bind(new InetSocketAddress("localhost",8897));
       //使用accept方法接收来自客户端的请求,该方法是一个阻塞的方法,会一直等待,直到有客户端请求连接才返回
       while(true){
         Socket s=socket.accept();
         //如果把义务请求在此处处理,则不能继续接收其它请求----所以将请求交给线程来处理
         new Thread(new ServiceServerTask(s)).start();
         
       }
       
   }
}
