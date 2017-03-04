package com.ywdeng.basic.netty.sendobject.client;

import com.ywdeng.basic.netty.sendobject.bean.Person;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: EchoClientHandler.java
 * @Description: 
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
   /**
    * 用于向服务端发送请求
    */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	   System.out.println("调用Handler处理器,本来应该先调用handler,再调用Encoder");
		Person person=new Person();
		person.setAge(18);
		person.setName("小米科技");
		person.setSex("男");
		ctx.writeAndFlush(person);
	}
	
	/**
	 * 主要用于接收客户端来的数据
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
			throws Exception {
		System.out.println("client 读取server数据..");
		// 服务端返回消息后
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("服务端数据为 :" + body);
		
	}
	  // • 发生异常时被调用
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			System.out.println("client exceptionCaught..");
			// 释放资源
			ctx.close();
		}

}
