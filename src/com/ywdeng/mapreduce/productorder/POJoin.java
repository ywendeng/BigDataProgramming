package com.ywdeng.mapreduce.productorder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



/**
 * @author ywdeng
 * @date 2017年3月15日
 * @Title: POMapReduce.java
 * @Description: 主要MapReduce框架中map端会把相同的key的value转换为Iterable<v1,v2,v3...>
 * 等形式传递给reduce端来处理，从而起到join的目的。但是这种实现方式会出现数据倾斜的情况
 */
public class POJoin {
	static class POMap extends Mapper<LongWritable, Text, Text, POInfoBean>{
		POInfoBean bean=new POInfoBean();
		Text k=new Text();
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			//根据切片所在快的上下文获取文件名
			FileSplit split =(FileSplit) context.getInputSplit();
			String fileName=split.getPath().getName();
			
			String line=value.toString();
			if(fileName.startsWith("order")){
				String[] orders=line.split(",");
				int order_id=Integer.parseInt(orders[0]);
				String date=orders[1];
				String pid=orders[2];
				int amout=Integer.parseInt(orders[3]);
				bean.setOrder(order_id, date,amout);
				k.set(pid);
			}else {
				String[] product=line.split(",");
				String pid=product[0];
				String pName=product[1];
				int category_id=Integer.parseInt(product[2]);
				float price=Float.parseFloat(product[3]);
				bean.setProduct(pName, category_id,price);
				k.set(pid);
			}
			context.write(k, bean);
		}
	}
   static class POReduce extends Reducer<Text, POInfoBean, POInfoBean, NullWritable>{
	   Text k =new Text();
	   POInfoBean bean=new POInfoBean();
	   //将map端关联好的数据信息在reduce端进行合并
	   @Override
	protected void reduce(Text key, Iterable<POInfoBean> values,
			Context context)
			throws IOException, InterruptedException {
		   //根据数据记录的标志位将商品信息记录和订单信息记录分开
		   POInfoBean  product=new POInfoBean();
		   ArrayList<POInfoBean>ordersList=new ArrayList<POInfoBean>();
		   for(POInfoBean po:values){
			   if (po.getFlag()==1){
				   try {
					BeanUtils.copyProperties(product, po);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			   }else {
				POInfoBean orderBean= new POInfoBean();
				try {
					BeanUtils.copyProperties(orderBean, po);
					ordersList.add(orderBean);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			   
		   }
		   //遍历订单list, 对商品订单信息实现拼接
		   for(POInfoBean poBean:ordersList){
			   bean.set(poBean.getOrder_id(), poBean.getDateString(),
					   key.toString(), poBean.getAmount(), product.getPname(), 
					   product.getCategory_id(), product.getPrice(),product.getFlag());
			   context.write(bean,NullWritable
					   .get());
		   }
		  
		   
		   
		   
		
	}
   }
   public static void main(String[] args) throws Exception {
	   Configuration conf = new Configuration();
		//设置输出字段之间的分割符
		conf.set("mapred.textoutputformat.separator", "\t");
		
		Job job = Job.getInstance(conf);

		// 指定本程序的jar包所在的本地路径
		// job.setJarByClass(RJoin.class);
//		job.setJar("c:/join.jar");

		job.setJarByClass(POJoin.class);
		// 指定本业务job要使用的mapper/Reducer业务类
		job.setMapperClass(POMap.class);
		job.setReducerClass(POReduce.class);

		// 指定mapper输出数据的kv类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(POInfoBean.class);

		// 指定最终输出的数据的kv类型
		job.setOutputKeyClass(POInfoBean.class);
		job.setOutputValueClass(NullWritable.class);

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
