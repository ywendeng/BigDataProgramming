package com.ywdeng.mapreduce.weblogwash;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author ywdeng
 * @date 2017年3月19日
 * @Title: WebLog.java
 * @Description: 清洗日志数据记录,将符合要求的数据放在一个文件,不符合要求的数据
 * 存放在另一个文件
 */
public class WebLog {
	static class LogCleanMap extends Mapper<LongWritable, Text, Text, NullWritable>{
		@Override
		protected void map(
				LongWritable key,
				Text value,
				Mapper<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			Text  k= new Text(PaserUtils.paser(value.toString()));
		    String[] fields=k.toString().split("\t");
		    // 根据条件将数据写入两个文件中
		    if(fields[fields.length-1].equals("1"))
			     context.write(k,NullWritable.get());
		    else 
		    	context.write(k,NullWritable.get());
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);

		job.setJarByClass(WebLog.class);

		job.setMapperClass(LogCleanMap.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
        job.setNumReduceTasks(0);
        job.setOutputFormatClass(WebLogOutputFormat.class);
		FileInputFormat.setInputPaths(job, new Path("C:\\Users\\JimG\\Desktop\\access.log.fensi"));
		FileOutputFormat.setOutputPath(job, new Path("C:\\Users\\JimG\\Desktop\\accss"));

		job.waitForCompletion(true);
	}

}
