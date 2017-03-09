package com.ywdeng.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author ywdeng
 * @date 2017年3月9日
 * @Title: WordCountReduce.java
 * @Description: Reduce端的输入，为Map端的输出---主要实现单词累加
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
   @Override
 //ReduceTask会对每一个key-value对的处理调用一次reduce程序
protected void reduce(Text key, Iterable<IntWritable> values,
		Context context)
		throws IOException, InterruptedException {
	int count=0;
	for (IntWritable value:values)
		  count+= value.get();
	context.write(key, new IntWritable(count));
}
}
