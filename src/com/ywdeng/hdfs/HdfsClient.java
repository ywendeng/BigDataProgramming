package com.ywdeng.hdfs;


import java.io.IOException;
import java.net.URI;




import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ywdeng
 * @date 2017年3月5日
 * @Title: HdfsClient.java
 * @Description: 主要介绍了获取文件系统操作实例对象(本地的文件系统,和Hdfs文件系统),以及
 * 指定相应的操作用户名
 */
public class HdfsClient {
	// FileSystem 是一个抽象的文件系统类，在获取文件对象操作实例时，需要根据相应的参数来指定   
    private FileSystem fs=null;
	@Before
	public void init() throws Exception{
		Configuration conf=new Configuration();
		
		fs=FileSystem.get(new URI("hdfs://192.168.80.3:9000"),conf,"hadoop");
	}
	//上传文件到HDFS
	@Test
	public void  putFile() throws Exception{
		fs.copyFromLocalFile(new Path("C:\\Users\\JimG\\Desktop\\season_240075"), new Path("/season.bak"));
	    fs.close();
	}
	//从HDFS下载文件到本地
	@Test 
	public void getFile() throws Exception{
		fs.copyToLocalFile(new Path("/season.bak"),new Path("C:\\Users\\JimG\\Desktop\\"));
		fs.close();
	}
	//新建目录
	@Test
	public void mkdirs() throws IOException{
	  fs.mkdirs(new Path("/ywdeng/hdfs"));
	  fs.close();
	}
	@Test
	public void deletDir() throws  IOException{
		fs.deleteOnExit(new Path("/ywdeng/hdfs"));
		fs.close();
	}
	//递归的遍历指定文件夹以及子文件夹下的所有文件
	@Test 
	public void recursionListFile() throws  IOException{
	   RemoteIterator<LocatedFileStatus> files=fs.listFiles(new Path("/erp"), true);
	   while(files.hasNext()){
		   LocatedFileStatus file=files.next();
		   System.out.println(file.isFile()?file.getPath().getName():file.getPath());
		   //取出文件存放在hdfs 上的文件块信息
		   BlockLocation[] fileBlock= file.getBlockLocations();
		   //遍历每个块的信息
		   for (BlockLocation block : fileBlock) {
			System.out.println("文件块的长度   "+block.getLength());
           	System.out.println("起始块的偏于量   "+block.getOffset())	;
           	//遍历每个块所在的dataNode的位置
           String[] location=block.getHosts();
           for(String dataNode:location){
        	   System.out.println("dataNode location  "+dataNode);
           }
		}  
	   }  
		fs.close();
	   }
	   //遍历指定目录下的一层文件
	   @Test
	   public void recursionDirAndFile() throws IOException{
		 FileStatus[] dirFile= fs.listStatus(new Path("/erp"));
		 for(FileStatus file:dirFile){
			 System.out.println("name: " + file.getPath().getName());
			 System.out.println(file.isFile()?file:"is directory");
		 }
		 fs.close();
	  
	}
	
}
