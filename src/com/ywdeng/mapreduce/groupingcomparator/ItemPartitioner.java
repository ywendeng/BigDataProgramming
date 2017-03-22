package com.ywdeng.mapreduce.groupingcomparator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author ywdeng
 * @date 2017年3月19日
 * @Title: ItemPartitioner.java
 * @Description:自定义分区,将相同order_id的订单记录分配同一个reduce端
 */
public class ItemPartitioner extends Partitioner<OrderBean, Text> {

	@Override
	public int getPartition(OrderBean order, Text text, int numPatitions) {
		return order.getOid().toString().hashCode()&Integer.MAX_VALUE%numPatitions;
	}

}
