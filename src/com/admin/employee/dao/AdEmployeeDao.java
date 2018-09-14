package com.admin.employee.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.vo.Employees;
import com.vo.Power;

public class AdEmployeeDao {
	
	public Page<Record> getRechargePage(int pageNumber, int pageSize, String empstarttime, String empendtime, String empstate, String employeeid, String powerid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("select e.*, p.name as powername");
		frosql.append(" from employee e");
		frosql.append(" LEFT JOIN power p");
		frosql.append(" on p.powerid = e.powerid");
		frosql.append(" where e.employeeid!="+1);
		if(!employeeid.equals("")){
			frosql.append(" and e.employeeid="+employeeid);
		}
		if(!powerid.equals("")){
			frosql.append(" and e.powerid="+powerid);
		}
		if(!empstate.equals("")){
			frosql.append(" and e.iflock="+empstate);
		}
		if(!empstarttime.equals("")&&!empendtime.equals("")){
			frosql.append(" and e.creationtime >= '"+empstarttime+"' and e.creationtime < '"+empendtime+"'");
		}
		frosql.append(" ORDER BY e.employeeid");
		//System.out.println("sql========"+selsql.toString()+frosql.toString());
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}
	
	public boolean ifAccount(String account){
		boolean boo = false;
		List<Employees> employees = Employees.dao.find("select p.account from employee p where p.account = '"+account+"'");
		if(employees.size()>0){
			boo = true;
		}
		return boo;
	}
	
	public List<Power> getPowerlist(){
		return Power.dao.find("select * from power where powerid!=1");
	}
	
	public Page<Record> getPerPage(int pageNum,int numPerPage,String pername,int empid){
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append("SELECT p.*");
		frosql.append(" from person p");
		frosql.append(" LEFT JOIN employee_person e");
		frosql.append(" ON p.id = e.id");
		frosql.append(" WHERE e.employeeid="+empid);
		if(!pername.equals("")){
			frosql.append(" and p.name LIKE '%"+pername+"%'");
		}
		frosql.append(" ORDER BY p.time desc");
		Page<Record> orderPage = Db.paginate(pageNum, numPerPage, selsql.toString(), frosql.toString());
		return orderPage;
	}
}
