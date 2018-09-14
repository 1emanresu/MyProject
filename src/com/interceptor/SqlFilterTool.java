package com.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Invocation;

public class SqlFilterTool {
	
	public static boolean sqlFilter(Invocation ai){
		//=========================sql过滤start=======================================
		HttpServletRequest req = ai.getController().getRequest();
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取所有请求参数
		Enumeration params = req.getParameterNames();
		String sql = "";
		while (params.hasMoreElements()) {
			//得到参数名
			String name = params.nextElement().toString();
			//System.out.println("name===========================" + name + "--");
			//得到参数对应值
			String[] value = req.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				sql = sql + value[i];
			}
		}
		//System.out.println("============================SQL"+sql);
		return sqlValidate(sql);
	}
	
	//效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|;|--|+|,|like|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
            	//System.out.println(badStrs[i]);
                return true;
            }
        }
        return false;
    }
}
