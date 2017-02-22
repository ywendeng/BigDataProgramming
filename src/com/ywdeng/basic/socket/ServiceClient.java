package com.ywdeng.basic.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author ywdeng
 * @date 2017年2月22日
 * @Title: ServiceClient.java
 * @Description: 
 */
public class ServiceClient {
/**
 * socket的客户端
 * @throws IOException 
 * @throws UnknownHostException 
 */
public static void main(String[] args) throws IOException {
	// 向服务器发起连接请求
	Socket socket=new Socket("localhost", 8897);
	//建立输入流输出流连接
   OutputStream out=null;
   InputStream in=null;
   try {
	  out =socket.getOutputStream();
	  in =socket.getInputStream();
	  //将请求发写入输入流中，并发送到服务端
	  PrintWriter  pw=new PrintWriter(out);
	  pw.println("Hello is James");
	  pw.flush();
	  //向输入流中获取服务端的返回值
	  BufferedReader reader=new BufferedReader(new InputStreamReader(in));
	  String result = reader.readLine();
	  System.out.println(result);
} catch (Exception e) {
	// TODO: handle exception
}finally {
  try{
	  out.close();
	  in.close();
	  socket.close();
  }catch(IOException e){
	  
  }
}
  
}
}
