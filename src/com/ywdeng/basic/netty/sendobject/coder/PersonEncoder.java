package com.ywdeng.basic.netty.sendobject.coder;

import com.ywdeng.basic.netty.sendobject.bean.Person;
import com.ywdeng.basic.netty.sendobject.utils.ByteObjConverter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: PersonEncoder.java
 * @Description:将对象转换为byte数组
 */
public class PersonEncoder extends MessageToByteEncoder<Person>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out)
			throws Exception {
        System.out.println("使用Encoder进行编码......");
        byte[] data=ByteObjConverter.objectToByte(msg);
        //将字节数组写入缓冲区
        out.writeBytes(data);
        ctx.flush();
		
	}
	

}
