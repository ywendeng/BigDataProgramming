package com.ywdeng.basic.netty.sendobject.server;

import java.nio.Buffer;

import com.ywdeng.basic.netty.sendobject.bean.Person;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: EchoServerHandler.java
 * @Description: 
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	
	@Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Person person=(Person)msg;
		System.out.println(person.toString());
		byte[] returnMessage= "服务端收到对象成功......".getBytes();
		ByteBuf buf=Unpooled.copiedBuffer(returnMessage);
		ctx.write(buf);
	};
   
   @Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
   
   
}
