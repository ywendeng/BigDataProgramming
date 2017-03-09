package com.ywdeng.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
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
    //设置reduce端的输出系列化方式
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    //设置输入输出数据文件目录
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1])); 
    //提交作业并返回作业的运行状态
    Boolean b=job.waitForCompletion(true);
    System.out.println(b?1:0);
}
}
