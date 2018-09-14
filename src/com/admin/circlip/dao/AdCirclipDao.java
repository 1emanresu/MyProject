package com.admin.circlip.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AdCirclipDao {

	public Page<Record> getCircPage(int pageNumber, int pageSize, String circstarttime, String circendtime, String circparticipate, String circstate, int employeeid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT c.*, p.name, ca.payable");
		frosql.append(" from circlip c");
		frosql.append(" LEFT JOIN person p");
		frosql.append(" ON p.id = c.id");
		frosql.append(" LEFT JOIN cardcode ca");
		frosql.append(" ON c.cardcode = ca.participate where 1=1");
//		if(employeeid==1){
//			frosql.append(" where 1=1");
//		}else{
//			frosql.append(" WHERE p.id in (SELECT ep.id from employee_person ep WHERE ep.employeeid="+employeeid+")");
//		}
		if(!circparticipate.equals("")){
			frosql.append(" and ca.codeid="+circparticipate);
		}
		if(!circstate.equals("")){
			if(circstate.equals("3")){
				frosql.append(" and c.result!=2000 and c.result!=2001");
			}else{
				frosql.append(" and c.result="+circstate);
			}
		}
		if(!circstarttime.equals("")&&!circendtime.equals("")){
			frosql.append(" and c.datetime >= '"+circstarttime+"' and c.datetime < '"+circendtime+"'");
		}
		frosql.append(" ORDER BY c.datetime DESC");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
}
