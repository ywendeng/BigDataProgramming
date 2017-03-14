package com.ywdeng.mapreduce.flowcount;

import java.util.HashMap;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author ywdeng
 * @date 2017年3月12日
 * @Title: MapPartitioner.java
 * @Description: Partitioner中key和value类型需要和map中的输出key_value
 * 类型相同
 */
public class MapPartitioner extends Partitioner<Text, FlowBean> {
    // 设置分区的编码------使用一个静态代码块,在加载类的时候就将该编码加入类中
	static HashMap<String, Integer> hash=new HashMap<>();
	static{
	     hash.put("136", 0);
	     hash.put("137", 1);
	     hash.put("138", 2);
	     hash.put("139", 3);
	}
	
	@Override
	public int getPartition(Text key, FlowBean arg1, int arg2) {
		 Integer patition= hash.get(key.toString().substring(0,3));
		 
		return patition==null?4:patition;
	}

}
