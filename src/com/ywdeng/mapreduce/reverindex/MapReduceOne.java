package com.ywdeng.mapreduce.reverindex;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




/**
 * @author ywdeng
 * @date 2017年3月17日
 * @Title: MapReduceOne.java
 * @Description: 主要将文本中的单词切分为如下k-v格式：
 *  Hello--->a.txt  2
 *  World---->a.txt  4
 *  My---->b.txt  3
 */
public class MapReduceOne {
     static class OneMap extends Mapper<LongWritable, Text, Text, IntWritable>{
    	Text k=new Text();
    	IntWritable v=new IntWritable();
    	 @Override
    	protected void map(LongWritable key, Text value,
    			Context context)
    			throws IOException, InterruptedException {
    		 //获取该条数据记录所在的文件名
    		 FileSplit split= (FileSplit) context.getInputSplit();
    		 String fileNam= split.getPath().getName();
    		 String[] words=value.toString().split(" ");
    		 for (String word:words){
    			 k.set(word+"--->"+fileNam);
    			 v.set(1);
    			 context.write(k,v);
    		 }
    	}
     }
    static  class OneReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
    	
         IntWritable v=new IntWritable();
    	 @Override
    	protected void reduce(Text key, Iterable<IntWritable> value,
    			Context context)
    			throws IOException, InterruptedException {
    		 int count=0;
    		 for(IntWritable t:value){
    			 count+=t.get();
    		 }
    		 context.write(key,new IntWritable(count));	
    	}
     }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    	 Configuration conf = new Configuration();
 		//设置输出字段之间的分割符
 		conf.set("mapred.textoutputformat.separator", "\t");
 		
 		Job job = Job.getInstance(conf);

 		// 指定本程序的jar包所在的本地路径
 		// job.setJarByClass(RJoin.class);
// 		job.setJar("c:/join.jar");
 		job.setJarByClass(MapReduceOne.class);
 		// 指定本业务job要使用的mapper/Reducer业务类
 		job.setMapperClass(OneMap.class);
 		job.setReducerClass(OneReduce.class);

 		// 指定mapper输出数据的kv类型
 		job.setMapOutputKeyClass(Text.class);
 		job.setMapOutputValueClass(IntWritable.class);

 		// 指定最终输出的数据的kv类型
 		job.setOutputKeyClass(Text.class);
 		job.setOutputValueClass(IntWritable.class);
 		//设置combiner 实现对数据进行压缩
 		job.setCombinerClass(OneReduce.class);
 		// 指定job的输入原始文件所在目录
 		FileInputFormat.setInputPaths(job, new Path("C:/Users/JimG/Desktop/map/"));
 		// 指定job的输出结果所在目录
 		FileOutputFormat.setOutputPath(job, new Path("C:/Users/JimG/Desktop/mapOut"));

 		// 将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
 		/* job.submit(); */
 		boolean res = job.waitForCompletion(true);
 		System.exit(res ? 0 : 1);
 }
}
