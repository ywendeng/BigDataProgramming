package com.ywdeng.mapreduce.weblogwash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;



/**
 * @author ywdeng
 * @date 2017年3月20日
 * @Title: PaserUtils.java
 * @Description: 封装一个解析日志的方法
 */
public class PaserUtils {
    private static HashMap<String, String> urlHashMap=DBLoder.getUrlFromMySQL();

	//解析字段日志字段
	public static String paser(String log){
		String[]  fields = log.split(" ");

		String remote_ip=fields[0];
		String remote_name=fields[1];
		 // 时间格式转换 
		String time_local=timeFormat(fields[3].substring(1));
		if(fields.length>=11){
		String requste_url=fields[6];			
		int valid = urlValidate(requste_url);
		String status=fields[8];
		String body_bytes_sent=fields[9];
		String http_refer=fields[10].substring(1, fields[10].length()-1);
		StringBuilder http_user_agent= new StringBuilder();
		// 对后面被切分的字段拼接为一个字段
		int i=11;
		while(i<fields.length){
			 if(i== 11)
				  http_user_agent.append(fields[i].substring(1)+" ");
			 else if(i==fields.length-1)
				 http_user_agent.append(fields[i].substring(0,fields[i].length()-1));
			 else 
				 http_user_agent.append(fields[i]+" ");
		     ++i;	 
		}
		//判断请求url是否有效
		return new WebLogBean(remote_ip,remote_name,time_local,
				requste_url,status,body_bytes_sent,http_refer,http_user_agent.toString(),valid).toString();
		
		}else {
			//表示无效的资源请求
			return  log+"\t"+0;
		}
		
	}
    public static String timeFormat(String time){
    	SimpleDateFormat sd1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);

    	SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String timeString = "";
		try {
			Date parse = sd1.parse(time);
			timeString = sd2.format(parse);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return timeString;
    }
    // 对url校验改为使用从数据库中读取数据来实现
    public static int urlValidate(String request_url){
       if (urlHashMap.get(request_url)!=null){
    	   return 1;
       }else {
    	  
		  return 0;
	}
    	
    }
}
