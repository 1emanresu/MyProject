package com.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.yeepay.server.Configuration;
import com.tool.StringUtil;
import com.vo.Employees;

public class PermissionInterceptor {
	
	/**
	 * @description 验证当前用户是否有访问的权限<br/>
	 * @methodName getPermission<br/>
	 * @author jack<br/>
	 * @createTime 2017年10月13日下午1:14:05<br/>
	 * @param ai
	 * @param employeeid
	 * @return Map result代表权限，uname 指访问的路径名称
	 */
	public static Map<String,String> getPermission(Invocation ai,int employeeid){
		String result = "0";
		String uname="";
		HttpServletRequest request = ai.getController().getRequest();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = request.getRequestURI();
		String adminp = Configuration.getInstance().getValue("adminurl");
		if(url.contains(adminp)){
			url = url.substring(url.indexOf(adminp)+adminp.length());
		}
		if(url.contains("/gateway/upView")){
			url = "/gateway/upView";
		}
		Employees employees = Employees.dao.findById(employeeid);
		int powerId = employees.getInt("powerid");
		StringBuffer sql = new StringBuffer();
		sql.append(" select n.url ,n.name ");
		sql.append(" from navigation n ");
		sql.append(" left join powerid_navid p on p.navid=n.navid where p.powerid = "+powerId+" ");
		//System.out.println(sql.toString());
		List<Record> records = Db.find(sql.toString());
		if(records != null && records.size() > 0){
			for (Record record : records) {
				String rurl = record.getStr("url");
				if(StringUtil.isNotEmpty(rurl) && rurl.contains(url)){
					uname=record.get("name");
					result = "1";
					break;
				}
			}
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("result", result);
		map.put("uname", uname);
		return map;
	}
}
