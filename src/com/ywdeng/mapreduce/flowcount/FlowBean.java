package com.ywdeng.mapreduce.flowcount;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.io.Writable;

/**
 * @author ywdeng
 * @date 2017年3月12日
 * @Title: FlowBean.java
 * @Description:主要用于封装流量的两个属性
 */
public class FlowBean implements Writable {
     private long downFlow;
     private long upFlow;
     private long  sumFlow;
     
    public  FlowBean(){
    	
    }
    public FlowBean(long downFlow,long upFlow,long sumFlow){
    	this.downFlow=downFlow;
    	this.upFlow=upFlow;
    	this.sumFlow=sumFlow;
    }
	public long getSumFlow() {
		return sumFlow;
	}
	public void setSumFlow(long sumFlow) {
		this.sumFlow = sumFlow;
	}
	public long getDownFlow() {
		return downFlow;
	}
	public void setDownFlow(long downFlow) {
		this.downFlow = downFlow;
	}
	public long getUpFlow() {
		return upFlow;
	}
	public void setUpFlow(long upFlow) {
		this.upFlow = upFlow;
	}
    public void set(long downFlow,long upFlow){
    	this.downFlow=downFlow;
    	this.upFlow=upFlow;
    	this.sumFlow=downFlow+upFlow;
    }
    //系列化
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(downFlow);
		out.writeLong(upFlow);	
		out.writeLong(sumFlow);
	}
	//反系列化
	@Override
	public void readFields(DataInput in) throws IOException {
		downFlow=in.readLong();
		upFlow=in.readLong();
		sumFlow=in.readLong();
	}
	//在输出到文件中的时候会调用toString 来格式话调用
    @Override
    public String toString() {
    	return downFlow+"\t"+upFlow+"\t"+sumFlow;
    }
}
