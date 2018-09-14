package com.admin.index.dao;
import java.util.List;

import com.vo.Navigation;
public class AdIndexDao {

	public List<Navigation> getUserNavid(int powerId){
		StringBuffer sql = new StringBuffer();
		sql.append(" select n.navid,n.node,n.ownership,n.url,n.`name` ");
		sql.append(" from navigation n");
		sql.append(" where n.navid in (");
		sql.append(" SELECT pn.navid from powerid_navid pn");
		sql.append(" where pn.powerid="+powerId+")");
		sql.append(" order by navid asc ");
		List<Navigation> list = Navigation.dao.find(sql.toString());
		return list;
	}
	
	public List<Navigation> findParentNode(int navid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select navid,node,ownership,url,`name` ");
		sql.append(" from navigation where navid = "+navid+" ");
		return Navigation.dao.find(sql.toString());
	}
	
	public List<Navigation> getNavi(int employeeid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from navigation where navid in( ");
		sql.append(" select u.navid ");
		sql.append(" from user_perm u");
		sql.append(" where u.employee_id=" + employeeid + ") order by navid");
		//System.out.println("sql1====="+sql.toString());
		List<Navigation> list = Navigation.dao.find(sql.toString());
		return list;
	}

	public List<Navigation> getNavis(int employeeid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from navigation where navid in( ");
		sql.append(" select a.navid ");
		sql.append(" from role_perm as a ");
		sql.append(" left JOIN  role_user as b on a.role_id=b.role_id");
		sql.append(" where b. employeeid= " + employeeid+ ") order by navid");
		//System.out.println("sql2====="+sql.toString());
		List<Navigation> list = Navigation.dao.find(sql.toString());
		return list;
	}
}
