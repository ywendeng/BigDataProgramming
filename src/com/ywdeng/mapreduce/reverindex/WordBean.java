package com.ywdeng.mapreduce.reverindex;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * @author ywdeng
 * @date 2017年3月18日
 * @Title: WordBean.java
 * @Description: 构造一个value 存储对象
 */
public class WordBean implements Writable{
    private String fileName=null;
    private int  amount=0;
    

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void set(String fileName,int amount ){
		this.fileName =fileName;
		this.amount=amount;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		fileName=in.readUTF();
		amount=in.readInt();
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(fileName);
        out.writeInt(amount);		
	}
	@Override
	public String toString() {
		return fileName+"--->"+amount;
	}

}
