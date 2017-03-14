package com.ywdeng.basic.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Test;

/**
 * @author ywdeng
 * @date 2017年2月26日
 * @Title: TestNio2.java
 * @Description: 
 */
/**
 * @author ywdeng
 * 在JDK1.7 中对NIO 进行了大量的扩展，称之为NIO.2
 * 1.path、paths 
 * java.nio.file.Path 接口代表了一个与平台无关的平台路径，描述了目录结构中文件的位置
 * Paths 提供了get()方法，用来获取Path对象
 * 2.Files 
 * 3.自动资源管理
 * try(需要关闭的资源){
 *   可能会出现异常的语句
 * }catch(Exception e){
 * ......
 * }
 */
public class TestNio2 {
	//自动资源管理：自动关闭实现 AutoCloseable 接口的资源
		@Test
		public void test6(){
			try(FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
					FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)){
				
				ByteBuffer buf = ByteBuffer.allocate(1024);
				inChannel.read(buf);
				
			}catch(IOException e){
				
			}
		}
	/*
	Files常用方法：用于操作内容
		SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。---newByteChannel是获取通道的方式之一
		DirectoryStream newDirectoryStream(Path path) : 打开 path 指定的目录
		InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
		OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
   */
	@Test 
	public void test5() throws IOException{
	SeekableByteChannel inByteChannel= Files.newByteChannel(Paths.get("C:\\Users\\JimG\\Desktop","files.txt"), StandardOpenOption.READ);
	SeekableByteChannel outByteChannel= Files.newByteChannel(Paths.get("C:\\Users\\JimG\\Desktop","file_new.txt"), 
			StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
	ByteBuffer buf=ByteBuffer.allocate(1024);
	inByteChannel.read(buf);
	buf.flip();
	outByteChannel.write(buf);
	//关闭IO设备之间的连接通道
	inByteChannel.close();
	outByteChannel.close();
	
	}
	 /*
		Files常用方法：用于判断
			boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
			boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
			boolean isExecutable(Path path) : 判断是否是可执行文件
			boolean isHidden(Path path) : 判断是否是隐藏文件
			boolean isReadable(Path path) : 判断文件是否可读
			boolean isWritable(Path path) : 判断文件是否可写
			boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
			public static <A extends BasicFileAttributes> A readAttributes(Path path,Class<A> type,LinkOption... options) : 获取与 path 指定的文件相关联的属性。
	 */
	@Test
	public void test4() throws IOException{
		Path path =Paths.get("C:\\Users\\JimG\\Desktop","files.txt");
		//判断文件是否可写
		System.out.println(Files.isWritable(path));
		//获取文件相关联的属性
		BasicFileAttributes fileAttributes=Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
	    //读取文件的属性
		System.out.println("文件的创建时间是："+fileAttributes.creationTime());
		System.out.println("文件的最后修改时间是："+fileAttributes.lastModifiedTime());	
	}
	/**
		Files常用方法：
			Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
			Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
			Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
			void delete(Path path) : 删除一个文件
			Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
			long size(Path path) : 返回 path 指定文件的大小
	 * @throws IOException 
	 */
	@Test 
	public void test2() throws IOException{
		Path src=Paths.get("C:\\Users\\JimG\\Desktop","files.txt");
		Path dist=Paths.get("C:\\Users\\JimG\\Desktop","GitHubLog.txt");
		//计算文件的大小
		System.out.println("当前输入文件的大小是："+Files.size(src));
		//将一个文件移动到另一个文件下
		//Files.move(src, dist, StandardCopyOption.ATOMIC_MOVE);
		//复制文件
		Files.copy(src, dist);
	}
	/**
	 * 文件或文件夹的创建、删除
	 * @throws IOException 
	 */
    @Test
    public void  test3() throws IOException{
    	Path path=Paths.get("C:\\Users\\JimG\\Desktop\\Files");
        Path filePath=Paths.get("hello.txt");
        System.out.println(filePath.toAbsolutePath());
        Files.createDirectory(path);
        //Files.createFile(filePath);
        //删除文件
        Files.delete(filePath);
        
    }
	/**
	 * Path 常用方法：
			boolean endsWith(String path) : 判断是否以 path 路径结束
			boolean startsWith(String path) : 判断是否以 path 路径开始
			boolean isAbsolute() : 判断是否是绝对路径
			Path getFileName() : 返回与调用 Path 对象关联的文件名
			Path getName(int idx) : 返回的指定索引位置 idx 的路径名称
			int getNameCount() : 返回Path 根目录后面元素的数量
			Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
			Path getRoot() ：返回调用 Path 对象的根路径
			Path resolve(Path p) :将相对路径解析为绝对路径
			Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
			String toString() ： 返回调用 Path 对象的字符串表示形式
	 */
   @Test
   public void test1(){
	   Path path=Paths.get("C:\\Users\\JimG\\Desktop","GitHubLog.txt");
	   System.out.println(path.isAbsolute());
	   System.out.println(path.getRoot());
	   //path对象对应的表示路径
	   System.out.println(path.toString());
	   System.out.println(path.getFileName());
	   System.out.println(path.startsWith("C:/"));
	   //只能够匹配到文件名
	   System.out.println(path.endsWith("GitHubLog.txt"));
	   //使用getNameCount() 返回根目录后的元素数量
	   for(int i= 0;i<path.getNameCount();i++)
		   // 返回指定索引位置的i的路径名称
		   System.out.println(path.getName(i));
   }
}
