package com.ywdeng.mapreduce.flowcount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author ywdeng
 * @date 2017年3月12日
 * @Title: FlowMap.java
 * @Description: 
 */
public class FlowMap extends Mapper<LongWritable, Text, Text, FlowBean> {
  //覆盖父类中的Map方法
	Text k=new Text();
	FlowBean flowBean=new FlowBean();
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
	    String line = value.toString();
	    String[] vlaues=line.split("\t");
	    String phoneNumber=vlaues[1];
	    flowBean.set(Long.parseLong(vlaues[7]),Long.parseLong(vlaues[8]));
	    k.set(phoneNumber);
	    //context会调用partitioner对数据进行分区，把相同hashCode的值分配到一个分区中
	    context.write(k, flowBean);
	}
}
