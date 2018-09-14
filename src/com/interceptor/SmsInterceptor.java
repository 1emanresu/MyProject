package com.interceptor;

import java.util.Date;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class SmsInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		Date sms = ai.getController().getSessionAttr("smstime");
		if(sms==null){
			ai.getController().setSessionAttr("smstime", new Date());
			ai.invoke();
		}else{
			 long time=new Date().getTime()-sms.getTime();
			 long ti = time/1000;
			 if(ti>60){
				 ai.getController().removeSessionAttr("smstime");
				 ai.invoke();
			 }else{
				 ai.getController().renderText("获取太频繁");
			 }
		}
	}
}
