package com.admin.dynamic.dao;

import com.jfinal.plugin.activerecord.Page;
import com.vo.Dynamic;

public class AdDynamicDao {

	public Page<Dynamic> getDynamicPage(int pageNumber, int pageSize, String starttime, String endtime, String title, int employeeid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select d.title, d.content, d.createtime, d.dynamicid, e.`name` as employeeid");
		frosql.append(" from dynamic d, employee e");
		frosql.append(" where d.employeeid = e.employeeid");
//		if(employeeid!=1){
//			frosql.append(" and e.employeeid = "+employeeid);
//		}
		if(!title.equals("")){
			frosql.append(" and d.title LIKE '%"+title+"%'");
		}
		if(!starttime.equals("")&&!endtime.equals("")){
			frosql.append(" and d.createtime >= '"+starttime+"' and d.createtime < '"+endtime+"'");
		}
		frosql.append(" ORDER BY d.createtime DESC");
		Page<Dynamic> orderPage = Dynamic.dao.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
}
