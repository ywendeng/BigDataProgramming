package com.ywdeng.mapreduce.productorder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

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
 * @date 2017年3月16日
 * @Title: POMapJoin.java
 * @Description: 将关联表中的小表的数据加入内存 ，实现在map端 Join操作，而不是在Reduce端才进行关联
 * 从而避免了数据倾斜的情况
 */
public class POMapJoin {
   static class MapJoin extends Mapper<LongWritable, Text, POInfoBean, NullWritable>{
	    /**
	     * 在调用map处理数据时会先调用一次,以便 实现数据的准备 
	     */
	   HashMap<String, POInfoBean> hashMap=null;
	   POInfoBean pobean=new POInfoBean();
	   @Override
	    protected void setup(
	    		Context context)
	    		throws IOException, InterruptedException {
	    	    //使用流的方式从工作目录下取出文件
		   BufferedReader reader=new BufferedReader(new InputStreamReader(
				   new FileInputStream("product.txt")));
		   //将读取的内容存储在HashMap中
		   hashMap=new HashMap<String, POInfoBean>();
		   String line=null;
		   while((line=reader.readLine())!=null){ 
			 String[] values=line.split(",");
			 POInfoBean bean= new POInfoBean();
			 String pId=values[0];
			 bean.setProduct(values[1],Integer.parseInt(values[2]) , Float.parseFloat(values[3]));
			 hashMap.put(pId,bean);
		   }
		   
	    } 
	   @Override
	    protected void map(LongWritable key, Text value,
	    		Context context)
	    		throws IOException, InterruptedException {
	     	String line=value.toString();
	     	String[] values=line.split(",");
	     	// 取出关联主键在此实现join 操作
	     	String pid=values[2];
	        POInfoBean product=hashMap.get(pid);
	        if (product!=null) {
	        	
				pobean.set(Integer.parseInt(values[0]), values[1],pid, 
						Integer.parseInt(values[3]), product.getPname(), 
						product.getCategory_id(),product.getPrice(),
						product.getFlag());
				context.write(pobean, NullWritable.get());
			}
	    }
   }
   
   public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
	   Configuration conf = new Configuration();
			//设置输出字段之间的分割符
			conf.set("mapred.textoutputformat.separator", "\t");
			
			Job job = Job.getInstance(conf);

			job.setJarByClass(POMapJoin.class);
			// 指定本业务job要使用的mapper/Reducer业务类
			job.setMapperClass(MapJoin.class);
			job.setNumReduceTasks(0);

			// 指定mapper输出数据的kv类型
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(POInfoBean.class);
            //将文件缓存到maptask工作的内存中
			job.addCacheFile(new URI("/user/hadoop/product.txt"));
			

			// 指定job的输入原始文件所在目录
			FileInputFormat.setInputPaths(job, new Path(args[0]));
			// 指定job的输出结果所在目录
		    
			FileOutputFormat.setOutputPath(job, new Path(args[1]));

			// 将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
			/* job.submit(); */
			boolean res = job.waitForCompletion(true);
			System.exit(res ? 0 : 1);
}
   
}
