package com.ywdeng.basic.netty.sendobject.client;



import java.net.InetSocketAddress;

import com.ywdeng.basic.netty.sendobject.coder.PersonEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ywdeng
 * @date 2017年3月4日
 * @Title: EchoClient.java
 * @Description: 
 */
public class EchoClient {
	private final String host;
	private final int port;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup nioEventLoopGroup = null;
		try {
			// 创建Bootstrap对象用来引导启动客户端
			Bootstrap bootstrap = new Bootstrap();
			// 创建EventLoopGroup对象并设置到Bootstrap中，EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接、接受数据、发送数据
			nioEventLoopGroup = new NioEventLoopGroup();
			// 创建InetSocketAddress并设置到Bootstrap中，InetSocketAddress是指定连接的服务器地址
			bootstrap.group(nioEventLoopGroup)//
					.channel(NioSocketChannel.class)//
					.remoteAddress(new InetSocketAddress(host, port))//
					.handler(new ChannelInitializer<SocketChannel>() {//
								// 添加一个ChannelHandler，客户端成功连接服务器后就会被执行
								@Override
								protected void initChannel(SocketChannel ch)
										throws Exception {
									// 注册编码的handler----先调用EchoClientHandler再调用PersonEncoder
									ch.pipeline().addLast(new PersonEncoder());  //out
									//注册处理消息的handler
									ch.pipeline().addLast(new EchoClientHandler());   //in
								}
							});
			// • 调用Bootstrap.connect()来连接服务器
			ChannelFuture f = bootstrap.connect().sync();
			// • 最后关闭EventLoopGroup来释放资源
			f.channel().closeFuture().sync();
		} finally {
			nioEventLoopGroup.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new EchoClient("localhost", 9898).start();
	}
}
