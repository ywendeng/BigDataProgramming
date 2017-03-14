package com.ywdeng.basic.netty.sendstring.client;

import java.net.InetSocketAddress;







import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ywdeng
 * @date 2017年3月1日
 * @Title: EchoClient.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 1.连接服务器
 * 2.写数据到服务器端
 * 3.接受来自服务器端返回
 * 4.关闭连接
 */
public class EchoClient {
    private  final int port;
    private  final String address;
    public EchoClient(int port,String address){
    	this.port=port;
    	this.address=address;
    }
    public void start() throws InterruptedException{
    	//声明一个引导类
    	Bootstrap bootstrap=new Bootstrap();
    	//EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接、接受数据、发送数据
    	EventLoopGroup  nioChannelGroup=null;
    	try {
			nioChannelGroup=new NioEventLoopGroup();
			//将事件回环加入到引导类中
			bootstrap.group(nioChannelGroup)
			         .channel(NioSocketChannel.class)
			         .remoteAddress(new InetSocketAddress(address,port))
			         .handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							//将业务处理类注册到管道中
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});
			//服务器连接,并使用ChannelFuture 实例对象监控管道的生命周期
	     ChannelFuture	channelFuture=bootstrap.connect().sync();
	     //管道的关闭需要等待管道中处理完成之后 
		 channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
		  nioChannelGroup.shutdownGracefully().sync();
		}
    	
    }
    public static void main(String[] args) throws InterruptedException {
		new EchoClient(8999, "localhost").start();
	}
}
