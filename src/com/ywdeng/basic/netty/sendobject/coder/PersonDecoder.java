package com.ywdeng.basic.netty.sendobject.coder;

import java.util.List;

import com.ywdeng.basic.netty.sendobject.utils.ByteObjConverter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: PersonDecoder.java
 * @Description: 
 */
public class PersonDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		byte[] data =new byte[in.readableBytes()];
		in.readBytes(data);
		Object object=ByteObjConverter.byteToObject(data);
		//将解码出来的对象加入到列表中
		out.add(object);
	}

	
}
