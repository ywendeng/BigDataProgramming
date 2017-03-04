package com.ywdeng.basic.netty.sendstring.server;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ywdeng
 * @date 2017年3月1日
 * @Title: EchoServerHandler.java
 * @Description: 
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException{
		System.out.println("服务器端接收数据.......");
        //申请一个缓冲区,并将数据读入到缓冲区中
		ByteBuf  dst=(ByteBuf)msg;
		//从缓冲区中读取数据
		byte[] str=new byte[dst.readableBytes()];
		//从buffer 中读出数据到byte数组中
		dst.readBytes(str);
		System.out.println("从客户端接收到数据： "+new String(str, 0, str.length, "UTF-8"));
		
		//向客户端写数据
		ByteBuf byteBuf=Unpooled.copiedBuffer("ok,服务器端接收数据成功....".getBytes());
	
		ctx.write(byteBuf);
	}
	  @Override
	 public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		  System.out.println("接收客户端的数据完毕");
		  //数据刷新之后才能传送到客户端
	      ctx.flush();
	 }
	  @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	       throws Exception {
	       cause.printStackTrace();
	       ctx.close();
	}

}
