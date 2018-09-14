package com.interceptor;

import java.util.Date;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class SmsRegisterInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		// TODO Auto-generated method stub
		Date sms = ai.getController().getSessionAttr("smsregtime");
		if(sms==null){
			ai.getController().setSessionAttr("smsregtime", new Date());
			ai.invoke();
		}else{
			 long time=new Date().getTime()-sms.getTime();
			 long ti = time/1000;
			 if(ti>300){
				 ai.getController().removeSessionAttr("smsregtime");
				 ai.invoke();
			 }else{
				 ai.getController().renderText("获取太频繁");
			 }
		}
	}
}
