package com.admin.userrequestlog.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdUserRequestLogDao {
	
	public Page<Record> pageLogs(int pageNumber, int pageSize,
			String netstarttime, String netendtime,  int netemployeeid,int netid,String uname,String name){
		
		
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		
		selsql.append("SELECT l.id,l.url,l.employee_id,l.date,l.uname,l.para ,e.name ");
		frosql.append(" FROM user_request_log l JOIN employee e  ON l.`employee_id`=e.`employeeid` WHERE 1=1  ");

		if (netid!=0  ) {
			frosql.append(" and l.id= '" + netid + "'");
		}	
	
		if (netemployeeid!=0  ) {
			frosql.append(" and l.employee_id= '" + netemployeeid + "'");
		}	
	
		if (!netstarttime.equals("")  ) {
			frosql.append(" and l.date >= '" + netstarttime + "'");
		}
		if(!netendtime.equals("")){
			frosql.append(" and l.date < '" + netendtime + "'");
		}
		
		if(!uname.equals("")){
			frosql.append(" and l.uname = '" + uname + "'");
		}
		if(!name.equals("")){
			frosql.append(" and e.name = '" + name + "'");
		}
		
		frosql.append(" ORDER BY l.date DESC");
		//System.out.println(selsql.toString()+frosql.toString());
		Page<Record> page=null;
		try {	
			page = Db.paginate(pageNumber, pageSize,selsql.toString(), frosql.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  page;
	}

}
