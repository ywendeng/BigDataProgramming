package com.ywdeng.basic.netty.sendstring.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ywdeng
 * @date 2017年3月1日
 * @Title: EchoClientHandler.java
 * @Description: 
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
   
	//在客户端和服务端连接建立好之后调用
	@Override
	 public void channelActive(ChannelHandlerContext ctx) throws Exception {
	 System.out.println("已经和服务器端建立连接，传输数据准备就绪.......");
	 byte[] msg="大数据技术与应用".getBytes();
	 //申请一个缓冲区
	 ByteBuf buffer=Unpooled.buffer(msg.length);
	 //将消息写入缓冲区中
	  buffer.writeBytes(msg);
	 //刷新缓冲区
	 ctx.writeAndFlush(buffer);
	};
	//从服务端读取数据时候调用
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ByteBuf msg)
			throws Exception {
		System.out.println("服务器端返回数据,接收数据开始.......");
		ByteBuf buf = msg;
		//声明一个缓冲区用于接收数据
		byte[] dst =new byte[buf.readableBytes()];
	    buf.readBytes(dst);
	    String result=new String(dst,"UTF-8");
	    System.out.println("服务端的响应消息为："+ result);
	}
	  //出现异常时被调用
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			System.out.println("client exceptionCaught..");
			// 释放资源
			ctx.close();
		}
	}

