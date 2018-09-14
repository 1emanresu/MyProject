package com.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class UpdateInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		// TODO Auto-generated method stub
		try {
			String key = ai.getController().getSessionAttr("sms");
			if(!key.equals("")&&!key.equals(null)){
				ai.invoke();
			}else{
				ai.getController().redirect("/user");
			}
		} catch (Exception e) {
			ai.getController().redirect("/user");
		}
	}
}
