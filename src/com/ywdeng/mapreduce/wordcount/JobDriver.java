package com.ywdeng.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author ywdeng
 * @date 2017年3月9日
 * @Title: JobDriver.java
 * @Description: 
 */
public class JobDriver {
   public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	Configuration conf=new Configuration();
	//获取一个Yarn客户端操作对象实例
	Job job =Job.getInstance(conf);
	// 设置jar所在的位置
	job.setJarByClass(JobDriver.class);
    //需要告诉Yarn的自己业务实现类是那个类
    job.setMapperClass(WordCountMap.class);
    job.setReducerClass(WordCountReduce.class);
    //设置对应的Map端输出自定义或者是默认的系列化方式
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    //设置combiner实现子的map端实现合并
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    /**
     *在map端使用combiner进行合并---减少网络数据的传输
     */
    job.setCombinerClass(WordCountReduce.class);
    /**
     *每一个文件会启动一个maptask 来处理，如果小文件过分，则需要启动过多的maptask 
     *造成资源的浪费。通常考虑到两种处理方案：1.将小文件合并之后才写入HDFS 2.如果文件已经存在
     *HDFS上则可以改变文件的输入格式----将默认的TextInputFormat输入格式转换为
     *CombineTextInputFormat(根据设置的文件大小,对小文件进行合并)
     */
      //默认的文件输入格式是 textInputFormat 
     job.setInputFormatClass(CombineTextInputFormat.class); 
     CombineTextInputFormat.setMaxInputSplitSize(job,4194304);
     CombineTextInputFormat.setMinInputSplitSize(job, 2097152);
    //设置reduce端的输出系列化方式
    //设置输入输出数据文件目录
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1])); 
    //提交作业并返回作业的运行状态
    Boolean b=job.waitForCompletion(true);
    System.out.println(b?1:0);
}
}
