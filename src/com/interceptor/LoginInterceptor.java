package com.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.vo.Person;

public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		try {
			Person person = ai.getController().getSessionAttr("session");
			if(person!=null){
				ai.invoke();
			}else{
				ai.getController().redirect("/");
			}
		} catch (Exception e) {
			ai.getController().redirect("/");
			e.printStackTrace();
		}
	}
}
