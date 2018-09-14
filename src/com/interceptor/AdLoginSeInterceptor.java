package com.interceptor;

import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.vo.Employees;

public class AdLoginSeInterceptor implements Interceptor {

	public void intercept(Invocation ai) {
		try {
			Employees emplog = ai.getController().getSessionAttr("adminsession");
			String uname="";
			if(emplog!=null){
	        	//=========================sql过滤start=======================================
				boolean sqlvali = SqlFilterTool.sqlFilter(ai);
				//有sql关键字，跳转到error.html
		        if (sqlvali) {
		        	ai.getController().renderJson("{\"statusCode\":\"301\", \"message\":\"您发送请求中的参数中含有非法字符！\"}");
		        } else {
		        	int employeeid = emplog.getInt("employeeid");
		        	//这里写验证权限的代码
		        	Map<String,String> map = PermissionInterceptor.getPermission(ai,employeeid);
		        	if(!map.get("result").equals("1")){
						ai.getController().renderJson("{\"statusCode\":\"301\", \"message\":\"对不起，您没有访问该内容的权限！\"}");
					}else{
		        		UserRequestLogInterceptor.saveUserRequestLog(ai, employeeid,map.get("uname"));
						ai.invoke();
					}
		        }
		        
			}else{
				ai.getController().renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
			}
		} catch (Exception e) {
			ai.getController().renderJson("{\"statusCode\":\"301\", \"message\":\"会话超时\"}");
		}
	}

}
