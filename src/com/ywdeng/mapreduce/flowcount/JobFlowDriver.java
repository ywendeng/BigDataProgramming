package com.ywdeng.mapreduce.flowcount;

import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * @author ywdeng
 * @date 2017年3月12日
 * @Title: JobFlowDriver.java
 * @Description:在此设置yarn客户端的job信息
 */
public class JobFlowDriver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	   Configuration conf=new Configuration();
	   Job job=Job.getInstance(conf);
	   job.setJarByClass(JobFlowDriver.class);
	   job.setPartitionerClass(MapPartitioner.class);
	   //-------------------------设置reduce的数目
	   job.setNumReduceTasks(5);
	   //设置自定义逻辑的map和reduce类
	   job.setMapperClass(FlowMap.class);
	   job.setReducerClass(FlowReduce.class);
	   //设置自定义的输入输出类型
	   job.setMapOutputKeyClass(Text.class);
	   job.setMapOutputValueClass(FlowBean.class);
	   job.setOutputKeyClass(Text.class);
	   job.setOutputValueClass(FlowBean.class);
	   //设置输入输出文件的路径
	   FileInputFormat.setInputPaths(job, new Path(args[0]));
	   FileOutputFormat.setOutputPath(job, new Path(args[1]));
	   //设置作业的提交
	   Boolean b=job.waitForCompletion(true);
	   System.out.println(b?1:0);
		
	}

}
