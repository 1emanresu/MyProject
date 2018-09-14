package com.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.vo.Asynchronous;

public class AsynchronousTimer extends TimerTask{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("自动补单之计时器运行中"+new Date()+"START");
		StringBuffer sql = new StringBuffer();
		sql.append("select b.*");
		sql.append(" from asynchronous b");
		sql.append(" where b.status='N'");
		List<Asynchronous> asyn = Asynchronous.dao.find(sql.toString());		
		for(Asynchronous ba:asyn){
			String orderid =ba.get("orderid");
			String url = ba.getStr("url");
			//链接客户回调地址--开始
			HttpClient hClient = new HttpClient();
			//hClient.getParams().setContentCharset( "GBK "); 
			HttpConnectionManagerParams managerParams = hClient.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(1110000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(3011000);
			PostMethod post = null;
			post = new PostMethod(url);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String returnStr = "";			
			try {
				hClient.executeMethod(post);
				returnStr = post.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(returnStr.indexOf("SUCCESS")==-1 && returnStr.indexOf("success")==-1 && returnStr.indexOf("充值成功")==-1){
				System.out.println("链接客户服务器失败或者未收到客户的返回成功状态");
			}else{
				Asynchronous asynchronous = Asynchronous.dao.findById(orderid);
				asynchronous.set("status", "Y");
				asynchronous.update();  
			}
		}
		System.out.println("自动补单之计时器运行中"+new Date()+"END");
	}

}
