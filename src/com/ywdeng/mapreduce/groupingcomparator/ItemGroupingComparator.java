package com.ywdeng.mapreduce.groupingcomparator;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author ywdeng
 * @date 2017年3月19日
 * @Title: ItemGroupingComparator.java
 * @Description: 利用reduce端的GroupingComparatorkey比较机制,自定义group的key,
 * 使得不同的key被视为相同的key
 */
public class ItemGroupingComparator extends WritableComparator{
    //需要使用构造函数将比较对象传入生成对象示例
     public ItemGroupingComparator() {
		super(OrderBean.class,true);
	}
	@Override
    public int compare(WritableComparable a, WritableComparable b) {
    	OrderBean  order1=(OrderBean)a;
    	OrderBean  order2=(OrderBean) b;
        //在groupingComparator进行比较时,只需要比较第一个字段即可
    	return order1.getOid().compareTo(order2.getOid());
    }
}
   