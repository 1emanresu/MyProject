package com.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.vo.Employees;

public class AdLoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		try {
			Employees emplog = ai.getController().getSessionAttr("adminsession");
			if(emplog!=null){
	        	//=========================sql过滤start=======================================
				boolean sqlvali = SqlFilterTool.sqlFilter(ai);
				//有sql关键字，跳转到error.html
		        if (sqlvali) {
		        	ai.getController().renderJson("{\"statusCode\":\"301\", \"message\":\"您发送请求中的参数中含有非法字符！\"}");
		        } else {
					ai.invoke();
		        }
			}else{
				ai.getController().renderJsp("/WEB-INF/admin/login.jsp");
			}
		} catch (Exception e) {
			ai.getController().renderJsp("/WEB-INF/admin/login.jsp");
		}
	}
}
