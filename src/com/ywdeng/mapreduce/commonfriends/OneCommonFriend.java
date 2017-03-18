package com.ywdeng.mapreduce.commonfriends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
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
 * @date 2017年3月18日
 * @Title: OneCommonFriends.java
 * @Description: 从文本中解析出两个人之间的共同好友
 */
public class OneCommonFriend {
	static class SplitRelationMap extends Mapper<LongWritable, Text,Text,Text>{
		Text k=new Text();
		Text v=new Text();
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
		 String[] relation=value.toString().split("#");
		 k.set(relation[0]);
		 String[] friends=relation[1].split(",");
		 for(String friend: friends){
			 v.set(friend);
			context.write(v, k);
		 }
		}
	}
    static class RelationReduce extends Reducer<Text, Text, Text, Text>{
    	@Override
    	protected void reduce(Text key, Iterable<Text> value,
    			Reducer<Text, Text, Text, Text>.Context context)
    			throws IOException, InterruptedException {
    	ArrayList<String> array=new ArrayList<>();
    	//将所有的元素存放到list中,为遍历没有元素做准备
    	for(Text val:value)
               array.add(val.toString());
    	
    	for(int i=0 ;i<array.size();i++){
    		for(int j=i+1;j<array.size();j++){
    		 context.write(new Text(array.get(i)+"-"+array.get(j)), key);
    		}	
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
	 		job.setJarByClass(OneCommonFriend.class);
	 		// 指定本业务job要使用的mapper/Reducer业务类
	 		job.setMapperClass(SplitRelationMap.class);
	 		job.setReducerClass(RelationReduce.class);

	 		// 指定mapper输出数据的kv类型
	 		job.setMapOutputKeyClass(Text.class);
	 		job.setMapOutputValueClass(Text.class);

	 		// 指定最终输出的数据的kv类型
	 		job.setOutputKeyClass(Text.class);
	 		job.setOutputValueClass(Text.class);
	 		//设置combiner 实现对数据进行压缩
	 		
	 		
	 		FileInputFormat.setInputPaths(job, new Path("C:\\Users\\JimG\\Desktop\\friend.txt"));
	 		// 指定job的输出结果所在目录
	 		FileOutputFormat.setOutputPath(job, new Path("C:/Users/JimG/Desktop/onecommon"));

	 		// 将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
	 		/* job.submit(); */
	 		boolean res = job.waitForCompletion(true);
	 		System.exit(res ? 0 : 1);
	}


}
