package com.ywdeng.mapreduce.flowcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author ywdeng
 * @date 2017年3月15日
 * @Title: FlowCountSort.java
 * @Description:  根据计算的总流量 从高到底的排序,主要设计思想是将
 * 自己封装的对象设置为key，利用mapreduce编程框架会对key将进行排序
 * 来实现对整体的一个排序功能
 */
public class FlowCountSort {
	static class FlowSortMap extends Mapper<LongWritable, Text, FlowBean, Text>{
		FlowBean flow=new FlowBean();
		Text  v =new Text();
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			String line=value.toString();
			String[]vlaues =line.split("\t");
			String phoneNumber=vlaues[0];
			flow.set(Long.parseLong(vlaues[1]),Long.parseLong(vlaues[2]));
			v.set(phoneNumber);
			context.write(flow, v);
		}
	}
    static class FlowSortReduce extends Reducer<FlowBean, Text, Text, FlowBean>{
    	@Override
    	protected void reduce(FlowBean key, Iterable<Text> value,
    			Context context)
    			throws IOException, InterruptedException {
    		//在此默认是已经有job将 数据记录统计好了
    		context.write(
    	    		value.iterator().next(), key);
    	}
    }
    public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		/*conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resoucemanager.hostname", "mini1");*/
		Job job = Job.getInstance(conf);
		
		/*job.setJar("/home/hadoop/wc.jar");*/
		//指定本程序的jar包所在的本地路径
		job.setJarByClass(FlowCountSort.class);
		
		//指定本业务job要使用的mapper/Reducer业务类
		job.setMapperClass(FlowSortMap.class);
		job.setReducerClass(FlowSortReduce.class);
		
		//指定mapper输出数据的kv类型
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(Text.class);
		
		//指定最终输出的数据的kv类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		//指定job的输入原始文件所在目录
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		//指定job的输出结果所在目录
		
		Path outPath = new Path(args[1]);
		
		FileOutputFormat.setOutputPath(job, outPath);
		
		//将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
		/*job.submit();*/
		boolean res = job.waitForCompletion(true);
		System.exit(res?0:1);

	}
}
