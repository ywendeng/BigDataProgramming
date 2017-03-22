package com.ywdeng.mapreduce.weblogwash;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * @author ywdeng
 * @date 2017年3月21日
 * @Title: WebLogOutputFormat.java
 * @Description: 自定义输出
 */
public class WebLogOutputFormat extends TextOutputFormat<Text,NullWritable>{
   
	@Override
public RecordWriter<Text, NullWritable> getRecordWriter(
		TaskAttemptContext context) throws IOException, InterruptedException {
		FileSystem fs=FileSystem.get(context.getConfiguration());
		// 使用字节输出流
		  FSDataOutputStream normal=fs.create(new Path("C:\\Users\\JimG\\Desktop\\logNomal"));
		  
		  FSDataOutputStream  notNormal=fs.create(new Path("C:\\Users\\JimG\\Desktop\\logNotNomal"));
	   // 需要返回RecordWriter对象
	   return new LogRecordWriter(normal, notNormal);
}  
  //自定义recordWriter写出方式
  @SuppressWarnings("unused")
private class LogRecordWriter extends RecordWriter<Text, NullWritable>{
    // 使用字节输出流
	  FSDataOutputStream normal=null;
	  FSDataOutputStream  notNormal=null;
	  public LogRecordWriter( FSDataOutputStream normal,FSDataOutputStream  notNormal) {
		super();
		this.normal=normal;
		this.notNormal=notNormal;
	}
	@Override
	public void close(TaskAttemptContext arg0) throws IOException,
			InterruptedException {
		if(normal!=null)
		     normal.close();
		if (notNormal!=null)
		    notNormal.close();
	}
    // 此处是将key-value 写入文件
	@Override
	public void write(Text key, NullWritable value) throws IOException,
			InterruptedException {
		String[] line=key.toString().split("\t");
		if(line[line.length-1].equals("1"))
			normal.write(key.toString().getBytes());
		else {
			notNormal.write(key.toString().getBytes());
		}
		
		
	}
	  
  }
}
