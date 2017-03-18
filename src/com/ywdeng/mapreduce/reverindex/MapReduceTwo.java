package com.ywdeng.mapreduce.reverindex;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

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
 * @Title: MapReduceTwo.java
 * @Description:  实现对索引文本进行排序
 * Hello----->a.txt  3
 * Hello----->b.txt  5
 * Hello----->c.txt  2
 * ---------------------
 * 将上面结果转换为：
 * Hello  b.txt----> 5   a.txt----> 3  c.txt---->2 
 */
public class MapReduceTwo {
	static class TwoMap  extends Mapper<LongWritable, Text, Text, WordBean>{
	    WordBean bean=new WordBean();
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			String line=value.toString();
			String[] keyValue=line.split("\t");
			String[] keys=keyValue[0].split("--->");
			bean.set(keys.length==2?keys[1]:keys[0],Integer.parseInt(keyValue[1]));
		    context.write(new Text(keys.length==2?keys[0]:" "),
		    		bean);
		}
		static class  TwoReduce extends Reducer<Text, WordBean, Text, Text>{
			@Override
			protected void reduce(Text key, Iterable<WordBean> value,
					Context context)
					throws IOException, InterruptedException {
			    TreeMap<Integer, String> treeMap=new TreeMap<>();
			    for(WordBean bean:value){
			    	//将值加入到treeMap中 ,根据出现的次序进行排序
			    	treeMap.put(bean.getAmount(), bean.getFileName());
			}
			    // 取出排好序值构造成字符串
			    StringBuilder builder=new StringBuilder();
			    for(Entry<Integer,String> entry:treeMap.entrySet()){
			        builder.append(entry.getValue()+"--->"+entry.getKey()+"\t");
			    }
			context.write(key, new Text(builder.toString()));
		}
			
			public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
				 Configuration conf = new Configuration();
			 		//设置输出字段之间的分割符
			 		conf.set("mapred.textoutputformat.separator", "\t");
			 		
			 		Job job = Job.getInstance(conf);

			 		// 指定本程序的jar包所在的本地路径
			 		// job.setJarByClass(RJoin.class);
//			 		job.setJar("c:/join.jar");
			 		job.setJarByClass(MapReduceTwo.class);
			 		// 指定本业务job要使用的mapper/Reducer业务类
			 		job.setMapperClass(TwoMap.class);
			 		job.setReducerClass(TwoReduce.class);

			 		// 指定mapper输出数据的kv类型
			 		job.setMapOutputKeyClass(Text.class);
			 		job.setMapOutputValueClass(WordBean.class);

			 		// 指定最终输出的数据的kv类型
			 		job.setOutputKeyClass(Text.class);
			 		job.setOutputValueClass(Text.class);
			 		//设置combiner 实现对数据进行压缩
			 		
			 		
			 		FileInputFormat.setInputPaths(job, new Path("C:/Users/JimG/Desktop/mapOut/part-r-00000"));
			 		// 指定job的输出结果所在目录
			 		FileOutputFormat.setOutputPath(job, new Path("C:/Users/JimG/Desktop/mapOutSort"));

			 		// 将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
			 		/* job.submit(); */
			 		boolean res = job.waitForCompletion(true);
			 		System.exit(res ? 0 : 1);
			}
		}
	}

}
