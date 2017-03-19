package com.ywdeng.mapreduce.groupingcomparator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * @author ywdeng
 * @date 2017年3月19日
 * @Title: OrderBean.java
 * @Description: 封装order 属性,并自定义比较方法
 */
public class OrderBean implements WritableComparable<OrderBean>{
    private Text oid;
    private DoubleWritable price;
    
    
	public Text getOid() {
		return oid;
	}

	public void setOid(Text oid) {
		this.oid = oid;
	}

	public DoubleWritable getPrice() {
		return price;
	}

	public void setPrice(DoubleWritable price) {
		this.price = price;
	}
    public void set(Text oid,DoubleWritable price){
    	this.oid=oid;
    	this.price=price;
    }
	@Override
	public void readFields(DataInput in) throws IOException {
	     oid=new Text(in.readUTF());
	     price =new DoubleWritable(in.readDouble());
	     
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(oid.toString());
		out.writeDouble(price.get());	
	}

	@Override
	public int compareTo(OrderBean object) {
		 int tmp=this.oid.compareTo(object.getOid());
		 if(tmp==0)
			 //如果是第一个字段相等,则按照第二个字段进行比较
			 return -this.price.compareTo(object.getPrice());
		 else 
			 return tmp;
	}
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return oid.toString()+"\t"+price;
    }
}
