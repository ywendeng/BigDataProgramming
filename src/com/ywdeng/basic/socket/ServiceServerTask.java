package com.ywdeng.basic.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;



/**
 * @author ywdeng
 * @date 2017年2月22日
 * @Title: ServiceServerTask.java
 * @Description: 
 */
/**
 * @author ywdeng
 *  线程贡献任务
 */
public class ServiceServerTask implements Runnable{
	Socket  socket;
	//创建一个构造方法
	InputStream in=null;
	OutputStream out=null;
	
	public ServiceServerTask(Socket socket) {
		this.socket=socket;
	}
	@Override
	public void run() {
		 try {
			 in =socket.getInputStream();
			 out=socket.getOutputStream();
			//将输入流转换为BufferReader
			BufferedReader  reader= new BufferedReader(new InputStreamReader(in));
		    String str=reader.readLine();
		    GetDataServiceImpl serviceImpl=new GetDataServiceImpl();
		    String result=serviceImpl.getData(str);
		    // 将处理好的结果写入的输出流中
		    PrintWriter  pw=new PrintWriter(out);
		    pw.println(result);
		    pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//将输入流输出流关闭
			try{
				in.close();
				out.close();
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
