package com.ywdeng.basic.netty.sendobject.server;



import java.net.InetSocketAddress;

import com.ywdeng.basic.netty.sendobject.coder.PersonDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: EchoServer.java
 * @Description: 
 */
public class EchoServer {
	private final int port;
	public EchoServer(int port){
		this.port=port;
	}
	public void start(){
		ServerBootstrap  bootstrap=new ServerBootstrap(); 
		EventLoopGroup envent=null;
		try {
			envent=new NioEventLoopGroup();
			bootstrap.group(envent)
			  .channel(NioServerSocketChannel.class)
			  .localAddress(new InetSocketAddress(port))
			  .childHandler(new ChannelInitializer<Channel>(){

				@Override
				protected void initChannel(Channel ch) throws Exception {
				    //实现发系列化   
					ch.pipeline().addLast(new PersonDecoder());
					ch.pipeline().addLast(new EchoServerHandler());
				}
				  
			  });
			  
	     ChannelFuture future=bootstrap.bind().sync();
	     System.out.println("服务器端启动....."+"端口号: "+port);
	     future.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				envent.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new EchoServer(9898).start();
	}
}
