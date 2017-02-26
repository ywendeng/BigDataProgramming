package com.ywdeng.basic.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


import org.junit.Test;
/**
 * @author ywdeng
 * @date 2017年2月25日
 * @Title: TestChannel.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 在NIO中的通道的使用
 * 1.Channel 接口常见的通道实现类
 * FileChannel ：用于读取、写入、 映射和操作文件的通道
 * DatagramChannel: 通过UDP读取网络中的数据通道
 * SocketChannel:通过TCP 读取网络中的数据
 * ServerSocketChanel:可以监听新进来的TCP连接，对每一个新进来的连接都会建立一个SocketChannel
 * 2.获取通道的方式
 * a.支持通道的对象，调用getChannel()
 *   本地IO:
 *      |---FileInputStream/FileOutputStream 
 *      |---RandomAccessFile
 *   网络IO:
 *      |---Socket
 *      |---ServerSocket
 *      |----DatagramSocket
 * b.使用files类的静态方法newByteChannel()获取字节通道
 * c.通过通道的静态方法open()打开并返回指定通道
 * 3.通道之间传递数据
 * a.transferFrom 
 * b.transferTo
 * 4.分散(scatter)和聚集（gather）
 * a.分散---将管道中数据写入到多个缓冲区中
 * b.聚集---将多个缓冲区中的数据写入到管道中
 * 5.编码和解码
 * a.编码---将字符串编码成字节数组(使用charSet的反射机制来创建类对象)
 * b.解码---将字节数据解码成字符串
 * 
 */
public class TestChannel {
	@Test
	public void test7() throws IOException{
		
	}
	//编码和解码  
	@Test 
	public void test5() throws IOException{
         Charset cs=Charset.forName("GBK");
		 //通过字符集对象获取编码器和解码器
		 CharsetEncoder encoder=cs.newEncoder();
		 CharsetDecoder decoder=cs.newDecoder();
		 //声明一个缓冲区
		 CharBuffer cbuf=CharBuffer.allocate(1024);
		 cbuf.put("大数据基础");
		 //编码
		 cbuf.flip();
		 ByteBuffer buf=encoder.encode(cbuf);
		 buf.flip();
		 System.out.println(buf.limit());
		 //对缓存器解码
		 CharBuffer cbuf2=decoder.decode(buf);
		 System.out.println(cbuf2.toString());
	}
	
	//分散和聚集
	@Test
	public void test4() throws IOException{
		RandomAccessFile infile=new RandomAccessFile("C:/Users/JimG/Desktop/GitHubLog.txt","r");
		RandomAccessFile outfile=new RandomAccessFile("C:/Users/JimG/Desktop/Log.txt","rw");
		FileChannel  inChannel=infile.getChannel();
		FileChannel  outChannel=outfile.getChannel();
		//分散---将管道中的内容写入多个缓冲区
		ByteBuffer buf1=ByteBuffer.allocate(1024);
		ByteBuffer  buf2=ByteBuffer.allocate((int) (inChannel.size()-1024));
	    ByteBuffer[] bufs ={buf1,buf2};
        //将管道中的数据写入多个缓冲区中 ‘
	    inChannel.read(bufs);
	    //将缓冲区中的数据聚集写入通道中
	    for(ByteBuffer b:bufs)
	    	b.flip();
	    outChannel.write(bufs);
	    inChannel.close();
	    outChannel.close();	
	}
	//通道之间直接传输数据---不用从通道中读取数据到数组中，再把数据写入通道
	@Test 
	public void test3() throws IOException{
		//通过FileChannel的静态方法直接打开文件，并返回通道
		FileChannel inChannel=FileChannel.open(Paths.get("C:/Users/JimG/Desktop/GitHubLog.txt"),StandardOpenOption.READ);
		FileChannel outChannel=FileChannel.open(Paths.get("C:/Users/JimG/Desktop/Git.txt"),
				StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
	     
	    //使用transferTo和transferFrom 来实现管道之间数据的传输
		//inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		inChannel.close();
		outChannel.close();
	}
	 
	/**
	 * 使用直接缓冲区完成文件的复制---主要使用FileChannel的map方法将文件的区域映射到内存中来创建,
	 * 该方法返回的是MapByteBuffer 
	 */
	@Test
	public void test2()throws Exception{
		//通过通道的静态方法open()打开并放回指定通道
		FileChannel inChannel=FileChannel.open(Paths.get("C:/Users/JimG/Desktop/GitHubLog.txt"), StandardOpenOption.READ);
	    FileChannel outChannel=FileChannel.open(Paths.get("C:/Users/JimG/Desktop/2.txt"), 
	    		StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
	    // 使用map方法将文件的区域直接映射到内存中来创建
	    MappedByteBuffer inBuffer=inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
	    MappedByteBuffer outBuffer=outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
	    //直接对缓冲区中的数据进行读写
	    byte[] b=new byte[inBuffer.limit()];
	    //读出缓冲区中的数据
	    /**
	     * 在此处不能再次将物理内存转换为读模式,因为在映射文件映射到内存时就已经定义为
	     * 只读模式，如果此处，继续使用inBuffer.flip()将会报错
	     */
	    inBuffer.get(b);
	    outBuffer.put(b);
	    //关闭通道
	    inChannel.close();
	    outChannel.close();
	}
	
	
   //通过管道实现本地文件的复制
   @Test
   public  void test1(){
	   FileInputStream in=null;
	   FileOutputStream out=null;
	   FileChannel inc=null;
	   FileChannel outc=null;
	   try {
		   //获取文件操作对象
		   in=new FileInputStream("C:/Users/JimG/Desktop/01107.mp4");
		   out=new FileOutputStream("C:/Users/JimG/Desktop/to001.mp4");
		   //获取支持管道的对象的管道
		   inc=in.getChannel();
		   outc=out.getChannel();
		   //将读取管道中的数据读入到缓冲区中
//		   ByteBuffer buf=ByteBuffer.allocate(1024);
//		   //反复将管道中的数据写入缓冲区中，之后将缓冲区中的数据写入管道中
//		   while(inc.read(buf)!=-1){
//			   //设置缓冲区为读模式
//			   buf.flip();
//			   //将缓冲区中的数写入输出管道中
//			   outc.write(buf); 
//			   //将缓冲区清空，为了下次继续读取
//			   buf.clear();
//		   }
		   //直接使用通道之间的数据传输
		   inc.transferTo(0, inc.size(), outc);
		
	} catch (Exception e) {
		// TODO: handle exception
	}finally {
	   try{
		   inc.close();
		   outc.close();
		   in.close();
		   out.close();
	   }catch(Exception e){
		   
	   }
	}
   }
}
