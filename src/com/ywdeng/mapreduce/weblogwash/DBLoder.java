package com.ywdeng.mapreduce.weblogwash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author ywdeng
 * @date 2017年3月22日
 * @Title: DBLoder.java
 * @Description: 
 */
public class DBLoder {
	
	public static HashMap<String, String> getUrlFromMySQL(){
		
		HashMap<String, String> urlHashMap=new HashMap<>();
		Connection con=null;
		Statement st=null;
		ResultSet resultSet=null;
		try {
	   		Class.forName("com.mysql.jdbc.Driver");
	   		//和Mysql建立连接
	   		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata", "root", "root");
            st=con.createStatement();
            resultSet=st.executeQuery("select * from url_rule");
            while(resultSet.next()){
            	urlHashMap.put(resultSet.getString(1), resultSet.getString(2));
            }
	   		
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if(resultSet!=null){
					resultSet.close();
				}
				if(st!=null){
					st.close();
				}
				if(con!=null){
					con.close();
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return urlHashMap;
		
	}
	
}
