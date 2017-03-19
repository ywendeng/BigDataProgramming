package com.ywdeng.mapreduce.groupingcomputer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.yarn.logaggregation.AggregatedLogFormat.LogWriter;

/**
 * @author ywdeng
 * @date 2017年3月19日
 * @Title: MaxRecordByOrderId.java
 * @Description: 找出每个订单中交易金额最大的记录
 */
public class SecondarySort {
	static class OrderMap extends Mapper<LongWritable, Text, OrderBean, Text>{
		 OrderBean bean=new OrderBean();
		 Text v=new Text();
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, OrderBean, Text>.Context context)
				throws IOException, InterruptedException {
			 String[] values=value.toString().split(",");
			 bean.set(new Text(values[0]), new DoubleWritable(Double.parseDouble(values[2])));
			 v.set(new Text(values[1]));
			 context.write(bean,v);
			 
		}
	}
	static class OrderReduce extends Reducer<OrderBean, Text, OrderBean, Text>{
		@Override
		protected void reduce(OrderBean key, Iterable<Text> values,
				Reducer<OrderBean, Text, OrderBean, Text>.Context context)
				throws IOException, InterruptedException {
			Text v=new Text(values.iterator().next());
			context.write(key, v);
			
		}
	}
	
public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(SecondarySort.class);
		
		job.setMapperClass(OrderMap.class);
		job.setReducerClass(OrderReduce.class);
		
		
		job.setOutputKeyClass(OrderBean.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path("C:\\Users\\JimG\\Desktop\\orders.txt"));
		FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\JimG\\Desktop\\orderMax"));
		
		//在此设置自定义的Groupingcomparator类 
		job.setGroupingComparatorClass(ItemGroupingComparator.class);
		//在此设置自定义的partitioner类
		job.setPartitionerClass(ItemPartitioner.class);
		
		job.setNumReduceTasks(2);
		
		job.waitForCompletion(true);
		
	}

}
