package com.ywdeng.basic.netty.sendstring.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ywdeng
 * @date 2017年3月1日
 * @Title: EchoServer.java
 * @Description: 
 */
/**
 * @author ywdeng
 *  服务器端主要用于接收客户端请求，并返回响应值
 *
 */
public class EchoServer {
    private final int port;
    public EchoServer(int port){
    	this.port=port;	
    }
    public void start() throws InterruptedException{
    	//服务端引导类
    	ServerBootstrap bootstrap=null;
    	EventLoopGroup nioLoopGroup=null;
    	try {
			bootstrap=new ServerBootstrap();
			nioLoopGroup=new NioEventLoopGroup();
			//将nioLoopCroup 加入到服务端引导类中
			bootstrap.group(nioLoopGroup)
			         .channel(NioServerSocketChannel.class)
			         .localAddress(new InetSocketAddress(port))
			         .childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
						   //从管道接收到数据之后，将业务交给EchoServerHandler来处理
							ch.pipeline().addLast(new EchoServerHandler());	
						}
			
					});
			//绑定服务器，调用sync()阻塞直到绑定完成
			ChannelFuture future=bootstrap.bind().sync();
			System.out.println("服务器启动端口地址为: "+future.channel().localAddress());
			future.channel().closeFuture().sync();
		} catch (Exception e) {
		
		}finally {
			 nioLoopGroup.shutdownGracefully().sync();
		}
    }
    public static void main(String[] args) throws InterruptedException {
		new EchoServer(8999).start();
	}
}
