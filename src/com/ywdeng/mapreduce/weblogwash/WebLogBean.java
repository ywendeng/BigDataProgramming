package com.ywdeng.mapreduce.weblogwash;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * @author ywdeng
 * @date 2017年3月19日
 * @Title: WebLogBean.java
 * @Description: 
 */
public class WebLogBean implements Writable {
   private String  remote_ip; // 客户端的IP地址
   private String  remote_name;// 用户名
   private String  time_local; //记录访问时间与时区
   private String  request_url;// 用户发送的url 请求
   private String  status; // 请求的返回的状态码
   private String  body_bytes_sent;// 请求返回给客户端的大小
   private String  http_refer;  // 该请求是从那个页面跳转过来的
   private String  http_user_agent ;//记录客户端浏览器的相关信息
   //判断用户请求是否有效给该条记录添加一个标志位
   private int valid;
	public String getRemote_ip() {
		return remote_ip;
	}
	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}
	public String getRemote_name() {
		return remote_name;
	}
	public void setRemote_name(String remote_name) {
		this.remote_name = remote_name;
	}
	public String getTime_local() {
		return time_local;
	}
	public void setTime_local(String time_local) {
		this.time_local = time_local;
	}
	public String getRequest_url() {
		return request_url;
	}
	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBody_bytes_sent() {
		return body_bytes_sent;
	}
	public void setBody_bytes_sent(String body_bytes_sent) {
		this.body_bytes_sent = body_bytes_sent;
	}
	public String getHttp_refer() {
		return http_refer;
	}
	public void setHttp_refer(String http_refer) {
		this.http_refer = http_refer;
	}
	public String getHttp_user_agent() {
		return http_user_agent;
	}
	public void setHttp_user_agent(String http_user_agent) {
		this.http_user_agent = http_user_agent;
	}
	
    public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public WebLogBean(){
		
	}
	public WebLogBean(String remote_ip,String remote_name,String  time_local,String  request_url,
    		String  status,String  body_bytes_sent,String  http_refer,
    		String  http_user_agent,int valid){
    	this.remote_ip=remote_ip;
    	this.remote_name=remote_name;
    	this.time_local=time_local;
    	this.request_url=request_url;
    	this.status=status;
    	this.body_bytes_sent=body_bytes_sent;
    	this.http_refer=http_refer;
    	this.http_user_agent=http_user_agent;
    	this.valid=valid;
    }
	@Override
	public void readFields(DataInput in) throws IOException {
		remote_ip =in.readUTF();
		remote_name=in.readUTF();
	    time_local=in.readUTF();
	    request_url=in.readUTF();
	    status=in.readUTF();
	    body_bytes_sent=in.readUTF();
	    http_refer=in.readUTF();
	    http_user_agent=in.readUTF();
	    valid=in.readInt();
		
	}
	@Override
	public void write(DataOutput out) throws IOException {
		 out.writeUTF(remote_ip);
		 out.writeUTF(remote_name);
		 out.writeUTF(time_local);
		 out.writeUTF(request_url);
		 out.writeUTF(status);
		 out.writeUTF(body_bytes_sent);
		 out.writeUTF(http_refer);
		 out.writeUTF(http_user_agent);
		 out.writeInt(valid);
		
	}
	@Override
		public String toString() {
			return remote_ip+"\t"+remote_name+"\t"+time_local+"\t"+request_url+"\t"
					+status+"\t"+body_bytes_sent+"\t"+http_refer+
					"\t"+http_user_agent+"\t"+valid;
		}
   
   
}
