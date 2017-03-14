package com.ywdeng.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author ywdeng
 * @date 2017年3月9日
 * @Title: WordCountMap.java
 * @Description: mr分布式编程框架, 只要重写重写父类中的map方法
 * 并在方法中实现相应的业务逻辑即可-----实现统计文本中每个单词出现的次数
 */
public class WordCountMap extends Mapper<LongWritable, Text, Text,IntWritable>{
   @Override
   //maptask 每输入一行便调用一次map方法
protected void map(LongWritable key, Text value,
		Context context)
		throws IOException, InterruptedException {
	  String line= value.toString();
	  //对输入的行进行切分为数组
	  String[]words= line.split(",");
	  for(String word:words)
		  context.write(new Text(word), new IntWritable(1));
}
}
