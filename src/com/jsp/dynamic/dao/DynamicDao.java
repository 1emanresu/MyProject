package com.jsp.dynamic.dao;

import com.jfinal.plugin.activerecord.Page;
import com.vo.Dynamic;

public class DynamicDao {
	public Page<Dynamic> getDynamicPage(int pageNumber, int pageSize){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select d.*");
		frosql.append(" from dynamic d");
		frosql.append(" ORDER BY d.createtime DESC");
		Page<Dynamic> orderPage = Dynamic.dao.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	public Dynamic getDynamic(int dynamicid){
		return Dynamic.dao.findById(dynamicid);
	}
}
