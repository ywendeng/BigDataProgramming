package com.ywdeng.mapreduce.commonfriends;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.ywdeng.mapreduce.commonfriends.AllCommonFriends.CommonFriedsMap.CommonFriedsReduce;


/**
 * @author ywdeng
 * @date 2017年3月18日
 * @Title: AllCommonFriends.java
 * @Description: 根据第一个mapReduce的输出，找出好友之间的所有共同好友
 */
public class AllCommonFriends {
	  
    static class CommonFriedsMap extends Mapper<LongWritable, Text, Text, Text>{
    	@Override
    	protected void map(LongWritable key, Text value,
    			Mapper<LongWritable, Text, Text, Text>.Context context)
    			throws IOException, InterruptedException {
    		String[] relation=value.toString().split("\t");
    		context.write(new Text(relation[0]), new Text(relation[1]));
    	}
      static class CommonFriedsReduce extends Reducer<Text, Text, Text, Text>{
    	  @Override
    	protected void reduce(Text key, Iterable<Text> value,
    			Reducer<Text, Text, Text, Text>.Context context)
    			throws IOException, InterruptedException {
    		  StringBuilder builder =new StringBuilder();
    		  Iterator<Text> iterator=value.iterator();
    		  //重新定义输出格式
    		   while(iterator.hasNext()){
    			   Text val=iterator.next();
    			   if(iterator.hasNext())
    				   builder.append(val+",");
    			   else 
    				   builder.append(val);
    			   
    		   }
    	 context.write(key, new Text(builder.toString()));
    	}
    	  
      }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		 Configuration conf = new Configuration();
	 		//设置输出字段之间的分割符
	 		conf.set("mapred.textoutputformat.separator", "\t");
	 		
	 		Job job = Job.getInstance(conf);

	 		// 指定本程序的jar包所在的本地路径
	 		// job.setJarByClass(RJoin.class);
           //job.setJar("c:/join.jar");
	 		job.setJarByClass(AllCommonFriends.class);
	 		// 指定本业务job要使用的mapper/Reducer业务类
	 		job.setMapperClass(CommonFriedsMap.class);
	 		job.setReducerClass(CommonFriedsReduce.class);

	 		// 指定mapper输出数据的kv类型
	 		job.setMapOutputKeyClass(Text.class);
	 		job.setMapOutputValueClass(Text.class);

	 		// 指定最终输出的数据的kv类型
	 		job.setOutputKeyClass(Text.class);
	 		job.setOutputValueClass(Text.class);
	 		//设置combiner 实现对数据进行压缩
	 		
	 		
	 		FileInputFormat.setInputPaths(job, new Path("C:/Users/JimG/Desktop/onecommon/part-r-00000"));
	 		// 指定job的输出结果所在目录
	 		FileOutputFormat.setOutputPath(job, new Path("C:/Users/JimG/Desktop/allcommon"));

	 		// 将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
	 		/* job.submit(); */
	 		boolean res = job.waitForCompletion(true);
	 		System.exit(res ? 0 : 1);
	}

}
