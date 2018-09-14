package com.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Invocation;
import com.pay.yeepay.server.Configuration;
import com.tool.StringUtil;
import com.vo.UserRequestLog;

public class UserRequestLogInterceptor {
	/**
	 * @description 保存用户请求的log日志<br/>
	 * @methodName saveUserRequestLog<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月18日下午12:04:10<br/>
	 * @param ai
	 * @param employeeid
	 */
	public static void saveUserRequestLog(Invocation ai,int employeeid,String uname){
		HttpServletRequest request = ai.getController().getRequest();
		
		
		String url = request.getRequestURI();
		Map<String, String[]> paramsMap = request.getParameterMap();
		StringBuffer params = new StringBuffer();
		String adminp = Configuration.getInstance().getValue("adminurl");
		if(StringUtil.isNotEmpty(url) && url.contains(adminp)){
			url = url.substring(url.indexOf(adminp)+adminp.length());
			params.append(url);
		}
		try {
			if(paramsMap != null && paramsMap.size()>0){
				params.append("?");
				int i=0;
				for (Map.Entry<String,String[]> element : paramsMap.entrySet()) {
					String key = element.getKey();
					String[] value = element.getValue();
					if(i == paramsMap.size()-1)break;
					if(i==0){
						params.append(key).append("=").append(value[0]);
					}else{
						params.append("&").append(key).append("=").append(value[0]);
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		url = params.toString();
		UserRequestLog log = new UserRequestLog();
		if(url.indexOf('?')!=-1){
			String para=url.substring(url.indexOf("?")+1);
			if(para.length()>500){
				para=para.substring(0,500)+"...";
			}
			log.set("para", url.substring(url.indexOf("?")+1));
			
			url=url.substring(0,url.indexOf("?"));
			
		}else if(url.indexOf("?")==(url.length()-1)){
			
			url= url.substring(0,url.length()-1);
		}
		
		log.set("url", url);
		
		//String uname=Db.queryStr("SELECT name FROM navigation WHERE url='"+url+"' LIMIT 1 ");
		if(StringUtil.isNotEmpty(uname)){
			log.set("uname", uname);
		}
		log.set("employee_id", employeeid);
		
		boolean result = log.save();
		//System.out.println(result==true?"日志保存成功":"日志保存失败");
	}
}
