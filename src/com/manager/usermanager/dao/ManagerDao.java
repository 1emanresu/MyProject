package com.manager.usermanager.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ManagerDao {

	public Page<Record> getUserPage(int pageNumber, int pageSize, String account) {
		StringBuffer selsql = new StringBuffer();
		StringBuffer frosql = new StringBuffer();
		selsql.append(" select a.* ,b.role_id,group_concat(c.role_name) as role_name ");
		frosql.append(" from employee as a ");
		frosql.append(" left JOIN role_user as b on a.employeeid=b.employeeid ");
		frosql.append(" left JOIN role as c on b.role_id=c.role_id ");
		if (!account.equals("")) {
			frosql.append(" where a.account like '%" + account + "%'");
		}
		frosql.append(" GROUP BY a.employeeid ");
		frosql.append(" ORDER BY a.creationtime DESC");
		Page<Record> orderPage = Db.paginate(pageNumber, pageSize, selsql.toString(), frosql.toString());
		return orderPage;
	}

	public List<Record> getRolename(int employeeid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* ,b.role_id,c.role_name ");
		sql.append(" from employee as a ");
		sql.append(" left JOIN role_user as b on a.employeeid=b.employeeid ");
		sql.append(" left JOIN role as c on b.role_id=c.role_id where a.employeeid=" + employeeid);
		return Db.find(sql.toString());
	}

	public List<Record> gethaveRolename(int employeeid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.role_name ");
		sql.append(" from role r ");
		sql.append(" where r.role_id  in (select u.role_id  from role_user u where u.employeeid=" + employeeid + ")");
		return Db.find(sql.toString());
	}

	public List<Record> getnullRolename(int employeeid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ");
		sql.append(" from role r ");
		sql.append(" where r.role_id not in (select u.role_id from role_user u where u.employeeid=" + employeeid + ")");
		return Db.find(sql.toString());
	}

	public List<Record> getUserPerm(int employeeid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select e.*, p.navid as checked");
		sql.append(" from navigation e");
		sql.append(" LEFT JOIN user_perm p");
		sql.append(" on e.navid = p.navid and p.employee_id=" + employeeid);
		return Db.find(sql.toString());
	}
}
