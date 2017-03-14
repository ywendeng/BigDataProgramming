package com.ywdeng.mapreduce.flowcount;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author ywdeng
 * @date 2017年3月12日
 * @Title: FlowReduce.java
 * @Description:  主要负责对map端的输出相同key的汇总
 */
public class FlowReduce extends Reducer<Text, FlowBean, Text, FlowBean>{
   @Override
protected void reduce(Text key, Iterable<FlowBean> values,
		Context context)
		throws IOException, InterruptedException {
	  long sumDownFlow=0;
	  long sumUpFlow=0;
	  long sumFlow=0;
	  for (FlowBean bean: values){
		  sumDownFlow+=bean.getDownFlow();
		  sumUpFlow+=bean.getUpFlow();
		  sumFlow+=sumDownFlow+sumUpFlow;  
	  }   
	context.write(key,new FlowBean(sumDownFlow,sumUpFlow,sumFlow));
}
}
