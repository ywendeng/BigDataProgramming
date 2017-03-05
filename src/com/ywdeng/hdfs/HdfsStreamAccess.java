package com.ywdeng.hdfs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ywdeng
 * @date 2017年3月5日
 * @Title: HdfsStreamAccess.java
 * @Description: 通过流的方式来读取和上传文件，可以指定操作字节数
 */
public class HdfsStreamAccess {
	FileSystem fs = null;
	Configuration conf = null;
	
	@Before
	public void init() throws Exception{
		
		conf = new Configuration();
		//拿到一个文件系统操作的客户端实例对象
//		fs = FileSystem.get(conf);
		//可以直接传入 uri和用户身份
		fs = FileSystem.get(new URI("hdfs://192.168.80.3:9000"),conf,"hadoop");
	}
	

	/**
	 * 通过流的方式上传文件到hdfs
	 * @throws Exception
	 */
	@Test
	public void testUpload() throws Exception {
		
		FSDataOutputStream outputStream = fs.create(new Path("/angelababy.love"), true);
		FileInputStream inputStream = new FileInputStream("c:/angelababy.love");
		
		IOUtils.copy(inputStream, outputStream);
		
	}
	
	
	/**
	 * 通过流的方式获取hdfs上数据
	 * @throws Exception
	 */
	@Test
	public void testDownLoad() throws Exception {
		
		FSDataInputStream inputStream = fs.open(new Path("/angelababy.love"));		
		
		FileOutputStream outputStream = new FileOutputStream("d:/angelababy.love");
		
		IOUtils.copy(inputStream, outputStream);
		
	}
	
	
	@Test
	public void testRandomAccess() throws Exception{
		
		FSDataInputStream inputStream = fs.open(new Path("/angelababy.love"));
	
		inputStream.seek(12);
		
		FileOutputStream outputStream = new FileOutputStream("d:/angelababy.love.part2");
		
		IOUtils.copy(inputStream, outputStream);
		
		
	}
	
	
	
	/**
	 * 显示hdfs上文件的内容
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testCat() throws IllegalArgumentException, IOException{
		
		FSDataInputStream in = fs.open(new Path("/angelababy.love"));
		
		IOUtils.copy(in, System.out);
		
//		IOUtils.copyBytes(in, System.out, 1024);
	}
}
