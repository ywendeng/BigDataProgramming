package com.ywdeng.basic.netty.sendobject.utils;

import io.netty.buffer.ByteBuf;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: ByteBufToBytes.java
 * @Description: 实现将ByteBuf转换为byte[]
 */
public class ByteBufToBytes {
    public byte[] read(ByteBuf datas){
    	byte[] dst=new byte[datas.readableBytes()];
    	datas.readBytes(dst);
		return dst;	
    }
}
